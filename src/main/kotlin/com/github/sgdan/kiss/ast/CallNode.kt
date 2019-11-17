package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.oracle.truffle.api.Truffle
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import com.oracle.truffle.api.nodes.RootNode
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class CallNode(kl: KissLanguage) : ExecutableNode(kl) {
    lateinit var function: RootNode

    @Child
    lateinit var arg: ExecutableNode

    override fun execute(frame: VirtualFrame): String {
        val argValue = arg.execute(frame)
        val rt = Truffle.getRuntime()
        return rt.createDirectCallNode(rt.createCallTarget(function))
                .call(argValue)
                .toString()
    }
}
