package com.scalaAsm.x86
package Instructions

abstract class TEST extends x86Instruction("TEST")

trait TEST_2[-O1, -O2] extends TEST with TwoOperands[O1,O2] with InstructionFormat

object TEST {
  
  implicit object test1 extends TEST_2[r32, rm32] {
      def operands = RM(op1,op2)
      val opcode: OpcodeFormat = 0x85
  }
  
  implicit object test2 extends TEST_2[rm32, imm32] {
      def operands = MI(op1,op2)
      val opcode: OpcodeFormat = 0xF7 /+ 0
  }
}