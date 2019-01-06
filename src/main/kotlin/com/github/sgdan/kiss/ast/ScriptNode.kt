package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.oracle.truffle.api.Truffle
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import com.oracle.truffle.api.nodes.RootNode
import mu.KotlinLogging
import java.math.BigInteger

fun ExecutableNode.executeKiss(frame: VirtualFrame): BigInteger {
    return execute(frame) as BigInteger
}

class ScriptNode(val kl: KissLanguage) : RootNode(kl) {
    @Child
    lateinit var body: ExecutableNode

    @Child
    var bodyFunction = FunctionNode(kl)

    override fun execute(frame: VirtualFrame): String {
        bodyFunction.body = body
        val rt = Truffle.getRuntime()
        val target = rt.createCallTarget(bodyFunction)
        return rt.createDirectCallNode(target)
                .call(arrayOf(BigInteger("0")))
                .toString()
    }
}
