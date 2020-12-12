package com.akkademy

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import com.UnitSpec
import com.akkademy.messages.SetRequest

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
class AkkademyDbSpec extends UnitSpec {
	
	implicit val system: ActorSystem = ActorSystem()
	val keyy: String = "name"
	val valuee: String = "RagDoll"
	
	describe("akkademy-db ") {
		describe("given SetRequest") {
			it("should place key/value into map") {
				// TestKit 是一个同步测试工具
				val actorRef: TestActorRef[AkkademyDb] = TestActorRef(new AkkademyDb)
				actorRef ! SetRequest(keyy, valuee)
				
				val akkademyDb: AkkademyDb = actorRef.underlyingActor
				akkademyDb.getValue(keyy) shouldEqual (Some(valuee))
			}
		}
	}
}
