package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.github.sgdan.kiss.syntax.Operator
import com.github.sgdan.kiss.syntax.Operator.*
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import java.math.BigInteger

class OperationNode(
        kl: KissLanguage,
        private val op: Operator
) : ExecutableNode(kl) {
    @Child
    lateinit var lhs: ExecutableNode

    @Child
    lateinit var rhs: ExecutableNode

    override fun execute(frame: VirtualFrame): BigInteger {
        val l = lhs.executeKiss(frame)
        val r = rhs.executeKiss(frame)
        return when (op) {
            MULTIPLY -> l.multiply(r)
            ADD -> l.add(r)
            SUBTRACT -> l.subtract(r)
            DIVIDE -> l.divide(r)
        }
    }
}
