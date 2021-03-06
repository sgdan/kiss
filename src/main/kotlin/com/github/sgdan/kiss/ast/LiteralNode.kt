package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import mu.KotlinLogging
import java.math.BigInteger

class LiteralNode(
        kl: KissLanguage,
        private val value: BigInteger
) : ExecutableNode(kl) {
    override fun execute(frame: VirtualFrame): String {
        return value.toString()
    }
}
