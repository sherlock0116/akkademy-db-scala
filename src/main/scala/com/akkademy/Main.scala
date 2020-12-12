package com.akkademy

import akka.actor.{ActorSystem, Props}

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
object Main {
	
	def main(args: Array[String]): Unit = {
		
		val actorSystem: ActorSystem = ActorSystem("akkademy")
		actorSystem.actorOf(Props(classOf[AkkademyDb]), "akkademy-db")
		
	}
}
