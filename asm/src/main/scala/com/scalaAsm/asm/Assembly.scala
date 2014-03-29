package com.scalaAsm.asm

import java.nio.ByteBuffer
import java.nio.ByteOrder
import scala.collection.mutable.ListBuffer
import com.scalaAsm.utils.Endian
import com.scalaAsm.asm.Tokens._
import com.scalaAsm.x86.MachineCode

case class Assembled(val code: Seq[Token], val data: Seq[Token])

trait CodeBuilder {
  val codeTokens = ListBuffer[Token]()
  val importantTokens = ListBuffer[Token]()
}

trait SimpleCodeBuilder {
  val codeTokens = ListBuffer[MachineCode]()
}

trait AsmProgram {
  
  val codeSegments = new ListBuffer[CodeSegment]()
  val dataSegments = new ListBuffer[DataSegment]()
  

  def hex2Bytes(hex: String) = {
    def stripChars(s: String, ch: String) = s filterNot (ch contains _)
    stripChars(hex, " -").grouped(2).map(Integer.parseInt(_, 16).toByte).toArray
  }

  def assemble: Assembled = {

    def recurse(code: List[CodeToken]): List[Token] =
       code flatMap {
        case Procedure(name, code) => List(BeginProc(name)) ++ recurse(code)
        case CodeGroup(code) => recurse(code)
        case token => List(token)
      }
    
    val codeTokens: ListBuffer[Token] = codeSegments.flatMap{seg => recurse(seg.builder.toList)}
    
    val dataTokens = dataSegments.flatMap{seg => seg.compile}

    Assembled(codeTokens, dataTokens)
  }

//  def getAssembledPermutations: Iterator[Assembled] = {
//    val procs = execution.toList
//    procs.drop(1).toStream.combinations(4).map { perm =>
//      val head = procs.head
//      val tokens: List[Code] = List(head) ++ perm.map(x => x.codeTokens).reduce(_++_) ++ procs.filter { x => !perm.contains(x) }.map(x => x.codeTokens)
//      // "start" function must be at the start
//      val result = tokens.map(_.codeTokens).reduce(_ ++ _)
//      Assembled(result.toSeq, dataTokens.toSeq)
//    }
//  }
}