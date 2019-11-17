package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.github.sgdan.kiss.syntax.Operator
import com.github.sgdan.kiss.syntax.Operator.*
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import mu.KotlinLogging
import java.math.BigInteger

private val logger = KotlinLogging.logger {}

class OperationNode(
        kl: KissLanguage,
        private val op: Operator
) : ExecutableNode(kl) {
    @Child
    lateinit var lhs: ExecutableNode

    @Child
    lateinit var rhs: ExecutableNode

    override fun execute(frame: VirtualFrame): String {
        val l = BigInteger(lhs.execute(frame).toString())
        val r = BigInteger(rhs.execute(frame).toString())
        return when (op) {
            MULTIPLY -> l.multiply(r)
            ADD -> l.add(r)
            SUBTRACT -> l.subtract(r)
            DIVIDE -> l.divide(r)
        }.toString()
    }
}
