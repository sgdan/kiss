package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import mu.KotlinLogging
import java.math.BigInteger

class VariableNode(kl: KissLanguage) : ExecutableNode(kl) {
    override fun execute(frame: VirtualFrame): BigInteger {
        return frame.arguments[0].toString().toBigInteger()
    }
}
