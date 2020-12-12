package com.pingpong

import akka.actor.{Actor, Status}

/**
 * @Author sherlock
 *
 * sender() 方法返回了 发送者的 ActorRef
 */
class PongActor extends Actor {
	
	override def receive: Receive = {
		case msg: String if msg.toLowerCase == "ping" => sender() ! "Pong"
		case _ => sender() ! Status.Failure(new Exception("unknown message..."))
	}
}
