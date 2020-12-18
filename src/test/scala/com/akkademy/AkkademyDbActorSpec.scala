package com.akkademy

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import com.akkademy.messages._
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.{BeforeAndAfterAll, FunSpec, Matchers}
/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
class AkkademyDbActorSpec extends TestKit(ActorSystem("AkkademyDbSpec"))
		with AnyFunSpecLike
		with Matchers
		with BeforeAndAfterAll {
	
	val test_k: String = "name"
	val test_v: String = "RagDoll"
	
	override protected def afterAll(): Unit = {
		
		TestKit.shutdownActorSystem(system)
	}
	
	describe("akkademy-db ") {
		describe("given SetRequest") {
			it("should place key/value into map") {
				// TestKit 是一个同步测试工具
				val actorRef: TestActorRef[AkkademyDbActor] = TestActorRef(new AkkademyDbActor)
				actorRef ! SetRequest(test_k, test_v)
				actorRef ! Connected
				AkkademyDb.getValue(test_k) shouldEqual (Some(test_v))
			}
		}
	}
}
