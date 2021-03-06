package scala.scalajs.runtime

import scala.scalajs.js

import StackTrace.JSStackTraceElem

/** Information about the JavaScript environment Scala.js runs in.
 *
 *  Holds configuration for the Scala.js internals and should not be used
 *  directly (could be retrieved via [[EnvironmentInfo.envInfo]]).
 *
 *  This facade type serves as a documentation on what aspects of the Scala.js
 *  standard library can be influenced through environment options.
 *
 *  If there is a variable named `__ScalaJSEnv` in Scala.js' scope (and it
 *  references an object), it is used as the value of
 *  [[EnvironmentInfo.envInfo]]. Otherwise, the latter is `undefined`.
 *
 *  @groupname envInfo Scala.js environment configuration
 *  @groupprio envInfo 1
 */
sealed trait EnvironmentInfo extends js.Object {

  // Can't link to java.lang.Runtime.exit - #1969
  /** The function that is called by `java.lang.Runtime.exit`
   *
   *  @group envInfo
   */
  def exitFunction: js.UndefOr[js.Function1[Int, Nothing]]

  /** Method used to source map JavaScript stack traces
   *
   *  @group envInfo
   */
  def sourceMapper: js.UndefOr[js.Function1[ // scalastyle:ignore
    js.Array[JSStackTraceElem], js.Array[JSStackTraceElem]]]

  /** Dictionary of system properties to add to java.lang.System.getProperties()
   *
   *  @group envInfo
   */
  def javaSystemProperties: js.UndefOr[js.Dictionary[String]]
}

object EnvironmentInfo {
  /** The value of the `__ScalaJSEnv` variable, if it exists. */
  val envInfo: js.UndefOr[EnvironmentInfo] = {
    import js.Dynamic.{global => g}

    // We've got to use selectDynamic explicitly not to crash Scala 2.10
    if (js.typeOf(g.selectDynamic("__ScalaJSEnv")) == "object" &&
        g.selectDynamic("__ScalaJSEnv") != null) {
      g.selectDynamic("__ScalaJSEnv").asInstanceOf[EnvironmentInfo]
    } else {
      js.undefined
    }
  }
}
