/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js tools             **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2013-2017, LAMP/EPFL   **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \    http://scala-js.org/       **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */

package org.scalajs.core.tools.linker.standard

import org.scalajs.core.tools.linker._

/** Common configuration given to all phases of the linker. */
final class CommonPhaseConfig private (
    /** Core specification. */
    val coreSpec: CoreSpec,
    /** Whether things that can be parallelized should be parallelized.
     *  On the JavaScript platform, this setting is typically ignored.
     */
    val parallel: Boolean,
    /** Whether the linker runs in batch mode.
     *
     *  In batch mode, the linker phase can throw away intermediate state that
     *  is otherwise maintained for incremental runs.
     *
     *  This setting is only a hint. A linker phase may ignore it. This applies
     *  in both directions: a phase not supporting incrementality can ignore
     *  `batchMode = false`, and a contrario, a phase mainly designed for
     *  incremental runs may ignore `batchMode = true`.
     */
    val batchMode: Boolean
) {
  private def this() = {
    this(
        coreSpec = CoreSpec.Defaults,
        parallel = true,
        batchMode = false)
  }

  private[linker] def withCoreSpec(coreSpec: CoreSpec): CommonPhaseConfig =
    copy(coreSpec = coreSpec)

  private[linker] def withParallel(parallel: Boolean): CommonPhaseConfig =
    copy(parallel = parallel)

  private[linker] def withBatchMode(batchMode: Boolean): CommonPhaseConfig =
    copy(batchMode = batchMode)

  private def copy(
      coreSpec: CoreSpec = coreSpec,
      parallel: Boolean = parallel,
      batchMode: Boolean = batchMode): CommonPhaseConfig = {
    new CommonPhaseConfig(
        coreSpec = coreSpec,
        parallel = parallel,
        batchMode = batchMode)
  }
}

private[linker] object CommonPhaseConfig {
  private[linker] def apply(): CommonPhaseConfig = new CommonPhaseConfig()
}
