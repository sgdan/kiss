package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import java.math.BigInteger

class ConditionalNode(kl: KissLanguage) : ExecutableNode(kl) {
    @Child
    lateinit var test: ExecutableNode

    @Child
    lateinit var positive: ExecutableNode

    @Child
    lateinit var otherwise: ExecutableNode

    override fun execute(frame: VirtualFrame): String {
        val testValue = test.execute(frame).toString()
        return if (BigInteger(testValue).signum() > 0) {
            positive.execute(frame)
        } else {
            otherwise.execute(frame)
        }.toString()
    }
}
