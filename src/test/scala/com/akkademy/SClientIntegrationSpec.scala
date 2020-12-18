package com.akkademy

import com.UnitSpec
import com.akkademy.messages.{KeyAlreadyExistsException, KeyNotExistsException}
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
		
		it("should fail because key exists") {
			intercept[KeyAlreadyExistsException](
				Await.result(client.setIfNotExists("123", "wood-man"), 2 seconds)
			)
		}
		
		it("should place key/value into map") {
			client.setIfNotExists("name", "tester")
			Await.result(client.get("name"), 2 seconds) shouldBe("tester")
		}
		
		it("shuold delete the key which exists") {
			client.delete("name")
			intercept[KeyNotExistsException](
				Await.result(client.get("name").mapTo[String], 2 seconds)
			)
		}
	}
	
}
