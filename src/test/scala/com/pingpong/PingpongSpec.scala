package com.pingpong

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.UnitSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
class PingpongSpec extends UnitSpec{
	
	val actorSystem: ActorSystem = ActorSystem("PingPongSystem")
	val pongActor: ActorRef = actorSystem.actorOf(Props(classOf[PongActor]), "pong")
	implicit val timeout: Timeout = Timeout(2 seconds)
	
	describe("get PongActorRef path") {
		it("would got the path") {
			// PongActorRef Path: [akka://default/user/$a]
//			val pongActor: ActorRef = actorSystem.actorOf(Props(classOf[PongActor]))
			
			// PongActorRef Path: [akka://PingPongSystem/user/pong]
			println(s"PongActorRef Path: [${pongActor.path}]")
			// PongActorRef SerializationFormat Path: [akka://PingPongSystem/user/pong#1506997969]
			println(s"PongActorRef SerializationFormat Path: [${pongActor.path.toSerializationFormat}]")
			// PongActorRef WithoutAddress Path: [/user/pong]
			println(s"PongActorRef WithoutAddress Path: [${pongActor.path.toStringWithoutAddress}]")
			
		}
	}
	
	describe("Pong Actor") {
		
		it("should response with Pong") {
			// use the implicit timeout
			val future: Future[Any] = pongActor ? "ping"
			val result: String = Await.result(future.mapTo[String], 1 second)
			result.toLowerCase shouldBe("pong")
		}
		
		it("should fail on unknown message") {
			val future: Future[Any] = pongActor ? "unknown"
			intercept[Exception] {
				Await.result(future.mapTo[String], 1 second)
			}
		}
	}
	
	describe("deal with future") {
		
		def askPong(msg: String): Future[String] = (pongActor ? msg).mapTo[String]
		
		it("dealing future with onComplete") {
			
			askPong("ping").onComplete{
				case Success(value) => println(s"replied with: ${value}")
				case Failure(exception) => println(exception)
			}
		}
		
		it("flat two-tier Future") {
			val future: Future[String] = askPong("ping")
					.flatMap(e => askPong("ping"))
					// recover 可以从错误中恢复, 用来返回特定结果, 这样可以保证 future 链式调用能成功返回.
					.recover({ case e: Exception => "404"})
			
			Await.result(future, 1 second).toLowerCase shouldBe("pong")
		}
	}
}
