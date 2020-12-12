package com.akkademy.messages

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
sealed abstract class Request

case class SetRequest(key: String, value: Object) extends Request
case class GetRequest(key: String) extends Request
case class SetIfNotExists(key: String, value: Object) extends Request
case class DeleteRequest(key: String) extends Request