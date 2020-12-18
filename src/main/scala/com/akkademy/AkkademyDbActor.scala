package com.akkademy

import akka.actor.{Actor, Stash, Status}
import akka.event.Logging
import com.akkademy.messages._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
class AkkademyDbActor extends Actor
		with Stash {
	
	private val log = Logging(context.system, this)
	private val db = AkkademyDb.getInstance
	
	
	override def preStart(): Unit = {
		context.system.scheduler.scheduleOnce(
			5 seconds,
			self,
			CheckConnected,
		)
	}
	
	override def receive: Receive = {
		case r: Request =>
			log.info("======> received Request - {} - 当前 AkkademyDb 状态为: [DisConnected]", r)
			stash()
			
		case Connected =>
			log.info("======> received Connected - 当前 AkkademyDb 状态为: [Connected]")
			context.become(online)
			unstashAll()
		
		case CheckConnected =>
			throw ConnectTimeoutException("")
		
		case DisConnected =>
			// do nothing
			
	}
		
	
	
	def online: Receive = {
		
		case SetRequest(k, v) =>
			log.info("======> received SetRequest - key: {} value: {}", k, v)
			db.saveIfNotExists(k, v) match {
				case Success(value) => sender() ! Status.Success(value)
				case Failure(exception) => sender() ! Status.Failure(exception)
			}
		
		case GetRequest(k) =>
			log.info("======> received GetRequest - key: {}", k)
			db.get(k) match {
				case Success(result) =>
					result match {
						case Some(value) => sender() ! Status.Success(value)
						case None => sender() ! Status.Failure(NullValueException())
					}
				case Failure(exception) => sender() ! Status.Failure(exception)
			}
		
		case UpdateRequest(k, v) =>
			log.info("======> received UpdateRequest - key: {} value:{}", k, v)
			db.update(k, v) match {
				case Success(value) => sender() ! Status.Success(value)
				case Failure(exception) => sender() ! Status.Failure(exception)
			}
		
		case DeleteRequest(k) =>
			log.info("======> received DeleteRequest - key: {}", k)
			db.delete(k) match {
				case Success(value) => Status.Success(value)
				case Failure(exception) => Status.Failure(exception)
			}
		
		case DisConnected => context.unbecome()
		
		case CheckConnected | Connected => Status.Success("======> already connected")
	}
	
}
