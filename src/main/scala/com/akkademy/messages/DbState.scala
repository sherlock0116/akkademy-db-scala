package com.akkademy.messages

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
sealed trait DbState

case object Connected
case object DisConnected
case object CheckConnected
