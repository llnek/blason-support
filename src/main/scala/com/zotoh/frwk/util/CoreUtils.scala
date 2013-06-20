/*??
 * COPYRIGHT (C) 2013 CHERIMOIA LLC. ALL RIGHTS RESERVED.
 *
 * THIS IS FREE SOFTWARE; YOU CAN REDISTRIBUTE IT AND/OR
 * MODIFY IT UNDER THE TERMS OF THE APACHE LICENSE,
 * VERSION 2.0 (THE "LICENSE").
 *
 * THIS LIBRARY IS DISTRIBUTED IN THE HOPE THAT IT WILL BE USEFUL,
 * BUT WITHOUT ANY WARRANTY; WITHOUT EVEN THE IMPLIED WARRANTY OF
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.
 *
 * SEE THE LICENSE FOR THE SPECIFIC LANGUAGE GOVERNING PERMISSIONS
 * AND LIMITATIONS UNDER THE LICENSE.
 *
 * You should have received a copy of the Apache License
 * along with this distribution; if not, you may obtain a copy of the
 * License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 ??*/

package com.zotoh.frwk.util

import scala.collection.JavaConversions._
import org.slf4j._

object CoreUtils {
  
  private val _log= LoggerFactory.getLogger(classOf[CoreUtils])
  
  def main(args:Array[String]) {
    println(shuffle(""))
  }

  def using[A <: {def close(): Unit}, B](param: A)(f: A => B): B = {
    try {
      f(param)
    } catch {
      case e:Throwable => _log.warn("", e); throw e
    } finally {
      tryc { () => param.close }
    }
  }

  def tryc ( f:  ()  => Unit ) {
    try {
      f()
    } catch { case e:Throwable =>  }
  }
  
  def blockAndWait(lock:AnyRef, waitMillis:Long) {
    lock.synchronized {
      tryc { () =>
        if (waitMillis > 0L) { lock.wait(waitMillis) } else { lock.wait() }
      }
    }
  }
    
  def unblock(lock:AnyRef) {
    lock.synchronized {
      tryc { () => lock.notifyAll }
    }
  }
  
  def asJObj(a:Any) : Object = a match {
    case x:Object => x
    case _ => null
  }
  
  def nsb(x:Any) = if (x==null) "" else x.toString()
  
  def shuffle(s:String) = {
    val lst= new java.util.ArrayList(s.toCharArray.toSeq)
    java.util.Collections.shuffle(lst)
    new String(lst.toList.toArray[Char])    
  }
    
}

sealed class CoreUtils {}