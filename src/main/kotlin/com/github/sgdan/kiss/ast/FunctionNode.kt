package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import com.oracle.truffle.api.nodes.RootNode
import mu.KotlinLogging
import java.math.BigInteger

class FunctionNode(kl: KissLanguage) : RootNode(kl) {
    @Child
    lateinit var body: ExecutableNode

    override fun execute(frame: VirtualFrame): BigInteger {
        return body.executeKiss(frame)
    }
}
