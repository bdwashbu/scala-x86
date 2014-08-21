package com.scalaAsm.x86
package Instructions
package Standard

import com.scalaAsm.x86.Operands.One

trait SHL extends x86Instruction {
  val mnemonic = "SHL"
}

trait SHL_2[OpEn, -O1, -O2] extends TwoOperandInstruction[OpEn, O1,O2] with SHL

object SHL {
  
  implicit object shl1 extends SHL_2[M1, rm8, One] {
      val opcode = OneOpcode(0xD0) /+ 4
  }
}