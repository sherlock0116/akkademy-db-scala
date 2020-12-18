package com.akkademy.messages

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
sealed abstract class DbException(msg: String) extends Exception(msg)

case class KeyNotExistsException(msg: String) extends DbException(msg)
case class KeyAlreadyExistsException(msg: String) extends DbException(msg)
case class ConnectTimeoutException(msg: String) extends DbException(msg)
case class NullValueException(msg: String = "") extends DbException(msg)