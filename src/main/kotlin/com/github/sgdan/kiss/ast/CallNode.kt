package com.github.sgdan.kiss.ast

import com.github.sgdan.kiss.KissLanguage
import com.oracle.truffle.api.Truffle
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExecutableNode
import com.oracle.truffle.api.nodes.RootNode
import java.math.BigInteger

class CallNode(kl: KissLanguage) : ExecutableNode(kl) {
    /*
     * Don't mark root node as child, will trigger stack overflow
     * if the code has recursive calls.
     */
    lateinit var function: RootNode

    @Child
    lateinit var arg: ExecutableNode

    override fun execute(frame: VirtualFrame): BigInteger {
        val argValue = arg.execute(frame)
        val rt = Truffle.getRuntime()
        val target = rt.createCallTarget(function)
        return rt.createIndirectCallNode()
                .call(target, arrayOf(argValue))
                .toString().toBigInteger()
        /*
        return rt.createDirectCallNode(target)
                .call(arrayOf(argValue))
                .toString().toBigInteger()
                */
    }
}
