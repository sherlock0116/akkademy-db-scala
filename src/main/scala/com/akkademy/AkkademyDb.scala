package com.akkademy

import akka.actor.Actor
import akka.event.Logging
import com.akkademy.messages.SetRequest

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
		case o =>
			log.info("received unknown message: {}", o)
	}
	
	def getValue(key: String): Option[Object] = map.get(key)
}
