package com.akkademy

import java.util.concurrent.locks.ReentrantLock

import com.akkademy.messages.{KeyAlreadyExistsException, KeyNotExistsException}

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

/**
 * @Description TODO
 * @Author sherlock
 * @Date
 */
class AkkademyDb private {
	
	private val setLock = new ReentrantLock()
	private val deleteLock = new ReentrantLock()
	private val map: mutable.Map[String, Object] = new mutable.HashMap[String, Object]
	
	def saveIfNotExists(key: String, value: Object): Try[String] = {
		setLock.lock()
		try {
			if (map contains key) {
				Failure(KeyAlreadyExistsException(key))
			} else {
				map += (key -> value)
				Success(key)
			}
		} catch {
			case e: Exception => throw e
		} finally {
			setLock.unlock()
		}
	}
	
	def update(key: String, value: Object): Try[String] = {
		setLock.lock()
		try {
			if (map contains key) {
				map(key) = value
				Success(key)
			} else {
				Failure(KeyNotExistsException(key))
			}
		} catch {
			case e: Exception => throw e;
		} finally {
			setLock.unlock()
		}
	}
	
	def delete(key: String): Try[String] = {
		if (map contains key) {
			map -= key
			Success(key)
		} else {
			Failure(KeyNotExistsException(key))
		}
	}
	
	def get(key: String): Try[Option[Object]] = {
		if (map contains key) {
			val option: Option[Object] = map.get(key)
			Success(option)
		} else {
			Failure(KeyNotExistsException(key))
		}
	}
}

object AkkademyDb {
	
	private lazy val db = new AkkademyDb
	def getInstance: AkkademyDb = db
	
	def getValue(k: String): Option[Object] = getInstance.map.get(k)
}
