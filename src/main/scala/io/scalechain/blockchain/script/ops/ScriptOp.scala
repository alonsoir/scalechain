package io.scalechain.blockchain.script.ops

import java.math.BigInteger

import io.scalechain.blockchain.script.{ScriptValue, ScriptEnvironment}
import io.scalechain.blockchain.{ErrorCode, ScriptEvalException}

trait ScriptOp {
 /** Execute the script operation, return the number of bytes it proceeded.
   * Ex> If the script operation read 10 bytes from the script to push the data on the stack, it returns 10.
   * This is used to calcuate the next script operation to evaluate in the script byte array chunk.
   *
   * @param env The script execution environment.
   * @return The number of bytes the script operation read from the script chunk.
   */
  def execute(env : ScriptEnvironment) : Int

  def unaryOperation(env : ScriptEnvironment, mutate : (ScriptValue) => (ScriptValue) ): Unit = {
    val value1 =  env.stack.pop()

    val result = mutate( value1 )

    env.stack.push( result )
  }

  def binaryOperation(env : ScriptEnvironment, mutate : (ScriptValue, ScriptValue) => (ScriptValue) ): Unit = {
    val value2 = env.stack.pop()
    val value1 =  env.stack.pop()

    val result = mutate( value1, value2 )

    env.stack.push( result )
  }

  def ternaryOperation(env : ScriptEnvironment, mutate : (ScriptValue, ScriptValue, ScriptValue) => (ScriptValue) ): Unit = {
    val value3 = env.stack.pop()
    val value2 = env.stack.pop()
    val value1 =  env.stack.pop()

    val result = mutate( value1, value2, value3 )

    env.stack.push( result )
  }

}

trait DisabledScriptOp {
  def execute(env : ScriptEnvironment) : Int = {
    throw new ScriptEvalException(ErrorCode.DisabledScriptOperation)
  }
}

/** The script operations are only for internal script execution engine.
 *
 */
trait InternalScriptOp {
  def execute(env : ScriptEnvironment) : Int = {
    throw new ScriptEvalException(ErrorCode.DisabledScriptOperation)
  }
}

trait AlwaysInvalidScriptOp {
  /** Because we check if there is any *always* invalid script operation before executing the script,
   * the execute method should never run. So we implement this method to hit an assertion.
   *
   * @param env
   */
  def execute(env : ScriptEnvironment) : Int = {
    assert(false)
    0
  }
}

trait InvalidScriptOpIfExecuted {
  def execute(env : ScriptEnvironment) : Int = {
    throw new ScriptEvalException(ErrorCode.InvalidTransaction)
  }
}



















