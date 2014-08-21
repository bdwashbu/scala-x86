package com.scalaAsm.x86
package Instructions
package Standard

import com.scalaAsm.x86.Operands._
import scala.annotation.implicitNotFound

trait RETN extends x86Instruction {
  val mnemonic = "RETN"
}

trait RETN_1[OpEn, -O1] extends OneOperandInstruction[OpEn, O1] with RETN

object RETN {
  
  implicit object retn1 extends RETN_1[I, imm16] {
      val opcode: OpcodeFormat = 0xC2
  }
}