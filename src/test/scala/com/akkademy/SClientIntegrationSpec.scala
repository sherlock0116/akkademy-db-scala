package com.akkademy

import com.UnitSpec
import com.akkademy.sclient.SClient

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
class SClientIntegrationSpec extends UnitSpec {
	
	val client: SClient = new SClient("127.0.0.1:2552")
	
	describe("akkademyDbClient") {
		
		it("should set a value") {
			client.set("123", new Integer(123))
			val future: Future[Any] = client.get("123")
			Await.result(future, 5 seconds) shouldEqual(123)
		}
	}
	
}
