package com.scalaAsm.x86
package Operands

import com.scalaAsm.x86.Operands.Memory.AddressingMode

abstract class Register[S: x86Size](val name: String) extends RegisterOrMemory[S] {
  override def toString = name.toLowerCase()
}

object Register {
  type SegmentRegister = GeneralPurpose[_16]
}

abstract class GeneralPurpose[S: x86Size](name: String) extends Register[S](name) {
  self =>
    
  val ID: Int
  
  abstract class BaseIndex[Y: x86Size](val displacement: Constant[Y]) extends AddressingMode[S] {
    def base: GeneralPurpose[S] = self
    def get: BaseIndex[Y] = this
  }
  
  abstract class Indirect extends AddressingMode[S] {
    def base: GeneralPurpose[S] = self
    def get: Indirect = this
  }
  
  def getIndirect: this.type#Indirect = new Indirect{}
  def getBaseIndex[Y: x86Size](const: Constant[Y]) = new BaseIndex(const) {}
}

trait UniformByteRegister[Size] extends GeneralPurpose[Size]

// "A" family - Accumulator for operands and results
abstract class AccumulatorRegister[Size: x86Size](name: String) extends GeneralPurpose[Size](name)  { val ID = 0 } 
class RAX extends AccumulatorRegister[_64]("rax")
class EAX extends AccumulatorRegister[_32]("eax")
class AX extends AccumulatorRegister[_16]("ax")
class AL extends AccumulatorRegister[_8]("al")
class AH extends AccumulatorRegister[_8]("ah") { override val ID = 4 }

// "B" family - Pointer to data in the DS segment
abstract class DataPointerRegister[Size: x86Size](name: String) extends GeneralPurpose[Size](name) { val ID = 3 }
class RBX extends DataPointerRegister[_64]("rbx")
class EBX extends DataPointerRegister[_32]("ebx")
class BX extends DataPointerRegister[_16]("bx")
class BL extends DataPointerRegister[_8]("bl")
class BH extends DataPointerRegister[_8]("bh") { override val ID = 7 }

// "C" family - Counter for string and loop operations
abstract class CounterRegister[Size: x86Size](name: String) extends GeneralPurpose[Size](name) { val ID = 1 }
class RCX extends CounterRegister[_64]("rcx")
class ECX extends CounterRegister[_32]("ecx")
class CX extends CounterRegister[_16]("cx")
class CL extends CounterRegister[_8]("cl")
class CH extends CounterRegister[_8]("ch") { override val ID = 5 }

// "D" family - I/O pointer
abstract class IORegister[Size: x86Size](name: String) extends GeneralPurpose[Size](name) { val ID = 2 }
class RDX extends  IORegister[_64]("rdx")
class EDX extends IORegister[_32]("edx")
class DX extends IORegister[_16]("dx")
class DL extends IORegister[_8]("dl")
class DH extends IORegister[_8]("dh") { override val ID = 6 }

// Stack pointer (in the SS segment)
abstract class StackPointer[Size: x86Size](name: String) extends GeneralPurpose[Size](name) { val ID = 4 }
class RSP extends StackPointer[_64]("rsp")
class ESP extends StackPointer[_32]("esp")
class SP extends StackPointer[_16]("sp")
class SPL extends StackPointer[_8]("spl") with UniformByteRegister[_8]

// Pointer to data on the stack (in the SS segment)
abstract class BasePointer[Size: x86Size](name: String) extends GeneralPurpose[Size](name) { val ID = 5 }
class RBP extends BasePointer[_64]("rbp")
class EBP extends BasePointer[_32]("ebp")
class BP extends BasePointer[_16]("bp")

// Pointer to data in the segment pointed to by the DS register; source pointer for string operations
abstract class SourceIndex[Size: x86Size](name: String) extends GeneralPurpose[Size](name) { val ID = 6 }
class RSI extends SourceIndex[_64]("rsi")
class ESI extends SourceIndex[_32]("esi")
class SI extends SourceIndex[_16]("si")

// Pointer to data (or destination) in the segment pointed to by the ES register; destination pointer for string operations
abstract class DestinationIndex[Size: x86Size](name: String) extends GeneralPurpose[Size](name) { val ID = 7 }
class RDI extends DestinationIndex[_64]("rdi")
class EDI extends DestinationIndex[_32]("edi")
class DI extends DestinationIndex[_16]("di")

class ES extends Register.SegmentRegister("es") { val ID = 8 }
class CS extends Register.SegmentRegister("cs") { val ID = 8 }
class SS extends Register.SegmentRegister("ss") { val ID = 8 }
class DS extends Register.SegmentRegister("ds") { val ID = 8 }
class FS extends Register.SegmentRegister("fs") { val ID = 8 }
class GS extends Register.SegmentRegister("gs") { val ID = 8 }

// Extra 64-bit registers
// Uses the rex.W field to access
abstract class Extra64Reg(name: String) extends GeneralPurpose[_64](name)
class R8 extends Extra64Reg("r8") { val ID = 0 }
class R9 extends Extra64Reg("r9") { val ID = 1 }
class R10 extends Extra64Reg("r10") { val ID = 2 }
class R11 extends Extra64Reg("r11") { val ID = 3 }
class R12 extends Extra64Reg("r12") { val ID = 4 }
class R13 extends Extra64Reg("r13") { val ID = 5 }
class R14 extends Extra64Reg("r14") { val ID = 6 }
class R15 extends Extra64Reg("r15") { val ID = 7 }
