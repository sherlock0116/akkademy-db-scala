package com.akkademy

import akka.actor.{Actor, Status}
import akka.event.Logging
import com.akkademy.messages.{GetRequest, KeyNotFoundException, SetRequest}

import scala.collection.mutable

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
class AkkademyDb extends Actor {
	
	private val log = Logging(context.system, this)
	private val map: mutable.Map[String, Object] = new mutable.HashMap[String, Object]
	
	override def receive: Receive = {
		
		case SetRequest(k,v) =>
			log.info("received SetRequest - key: {} value: {}", k, v)
			map.put(k, v)
			sender() ! Status.Success
		
		case GetRequest(k) =>
			log.info("received GetRequest - key: {}", k)
			val optionValue: Option[Object] = map.get(k)
			optionValue match {
				case Some(value) => sender() ! value
				case None => sender() ! Status.Failure(new KeyNotFoundException(k))
			}
		
		case o =>
			log.info("received unknown message: {}", o)
			Status.Failure(new ClassNotFoundException)
	}
	
	def getValue(key: String): Option[Object] = map.get(key)
}
