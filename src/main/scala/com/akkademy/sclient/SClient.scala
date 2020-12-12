package com.akkademy.sclient

import akka.actor.{ActorSelection, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import com.akkademy.messages.{GetRequest, SetRequest}
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
class SClient(remoteAddress: String) {
	
	private implicit val system: ActorSystem = ActorSystem("LocalSystem", ConfigFactory.load("local"))
	private implicit val timeout: Timeout = Timeout(1 second)
	
	private val remoteDb: ActorSelection = system.actorSelection(
		s"akka.tcp://akkademy@${remoteAddress}/user/akkademy-db")
	
	def set(key: String, value: Object) = remoteDb ? SetRequest(key, value)
	def get(key: String) = remoteDb ? GetRequest(key)
}
