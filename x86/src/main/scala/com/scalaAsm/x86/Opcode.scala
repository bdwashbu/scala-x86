package com.scalaAsm.x86

import com.scalaAsm.x86.Operands.ModRM

trait OpcodeFormat {
  def size: Int
  val opcodeExtension: Option[Byte]
  def /+ (x: Byte): OpcodeFormat
}

case class OpcodePlusRd(opcode1:Byte) extends OpcodeFormat {
  def get(regID: Int) = Array((opcode1 + regID).toByte)
  val size = 1
  val opcodeExtension: Option[Byte] = None
  def /+ (x: Byte) = new OneOpcode(opcode1) { override val opcodeExtension = Some(x) }
}

case class OneOpcode(operand1:Byte) extends OpcodeFormat {
  def get = Array(operand1)
  val size = 1
  val opcodeExtension: Option[Byte] = None
  def /+ (x: Byte) = new OneOpcode(operand1) { override val opcodeExtension = Some(x) }
}

case class TwoOpcodes(opcode1:Byte, opcode2:Byte) extends OpcodeFormat {
  def get = Array(opcode1, opcode2)
  val size = 2
  val opcodeExtension: Option[Byte] = None
  def /+ (x: Byte) = new TwoOpcodes(opcode1, opcode2) { override val opcodeExtension = Some(x) }
}