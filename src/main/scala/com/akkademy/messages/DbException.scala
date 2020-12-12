package com.akkademy.messages

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
sealed abstract class DbException(msg: String) extends Exception(msg)

case class KeyNotFoundException(msg: String) extends DbException(msg)
