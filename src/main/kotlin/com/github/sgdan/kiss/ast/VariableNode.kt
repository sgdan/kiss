package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class VariableNode(kl: KissLanguage) : ExecutableNode(kl) {
    override fun execute(frame: VirtualFrame): String {
        return if (frame.arguments.isEmpty()) "error"
        else frame.arguments[0].toString()
    }
}
