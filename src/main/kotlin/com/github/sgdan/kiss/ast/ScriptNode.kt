package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import com.oracle.truffle.api.nodes.RootNode
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class ScriptNode(val kl: KissLanguage) : RootNode(kl) {
    @Child
    lateinit var body: ExecutableNode

    override fun execute(frame: VirtualFrame): String {
        return try {
            body.execute(frame).toString()
        } catch (e: Exception) {
            logger.error(e) { "Unexpected error" }
            "error"
        }
    }
}
