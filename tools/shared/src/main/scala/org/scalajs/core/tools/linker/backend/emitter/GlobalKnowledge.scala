/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js tools             **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2013-2016, LAMP/EPFL   **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \    http://scala-js.org/       **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */


package org.scalajs.core.tools.linker.backend.emitter

import org.scalajs.core.ir.Trees.{FieldDef, JSNativeLoadSpec}
import org.scalajs.core.ir.Types.Type

private[emitter] trait GlobalKnowledge {
  /** Tests whether the parent class data is accessed in the linking unit. */
  def isParentDataAccessed: Boolean

  /** Tests whether the specified class name refers to an `Interface`. */
  def isInterface(className: String): Boolean

  /** All the `FieldDef`s, included inherited ones, of a Scala class.
   *
   *  It is invalid to call this method with anything but a `Class` or
   *  `ModuleClass`.
   */
  def getAllScalaClassFieldDefs(className: String): List[FieldDef]

  /** Tests whether the specified class uses an inlineable init.
   *
   *  When it does, its (only) `init___` method is inlined inside the JS
   *  constructor. This means that instantiation should look like
   *  {{{
   *  new \$c_Foo(args)
   *  }}}
   *  instead of
   *  {{{
   *  new \$c_Foo().init___XY(args)
   *  }}}
   */
  def hasInlineableInit(className: String): Boolean

  /** Tests whether the specified class locally stores its super class. */
  def hasStoredSuperClass(className: String): Boolean

  /** Gets the types of the `jsClassCaptures` of the given class. */
  def getJSClassCaptureTypes(className: String): Option[List[Type]]

  /** `None` for non-native JS classes/objects; `Some(spec)` for native JS
   *  classes/objects.
   *
   *  It is invalid to call this method with a class that is not a JS class
   *  or object (native or not), or one that has JS class captures.
   */
  def getJSNativeLoadSpec(className: String): Option[JSNativeLoadSpec]

  /** The `encodedName` of the superclass of a (non-native) JS class.
   *
   *  It is invalid to call this method with a class that is not a non-native
   *  JS class.
   */
  def getSuperClassOfJSClass(className: String): String

  /** The `FieldDef`s of a non-native JS class.
   *
   *  It is invalid to call this method with a class that is not a non-native
   *  JS class.
   */
  def getJSClassFieldDefs(className: String): List[FieldDef]
}
