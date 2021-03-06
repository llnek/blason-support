/*??
 * COPYRIGHT (C) 2012-2013 CHERIMOIA LLC. ALL RIGHTS RESERVED.
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

package com.zotoh.blason.core

import scala.collection.JavaConversions._
import scala.collection.mutable
import org.apache.commons.io.{FileUtils=>FUT}
import java.net.URLClassLoader
import java.io.File
import java.net.URL

import Constants._

/**
 * @author kenl
 */
class AppClassLoader(par:RootClassLoader) extends AbstractClassLoader(par) {

  def configure(appDir:String) {
    val c= new File(appDir, POD_CLASSES)
    val p= new File(appDir, POD_PATCH)
    val b= new File(appDir, POD_LIB)
    if (!_loaded) {
      findUrls(p)
      addUrl(c)
      findUrls(b)
      if ( new File(appDir, WEB_INF).exists() ) {
        addUrl( new File(appDir, WEB_CLASSES))
        findUrls(new File(appDir, WEB_LIB))
      }
    }
    _loaded=true
  }

}
