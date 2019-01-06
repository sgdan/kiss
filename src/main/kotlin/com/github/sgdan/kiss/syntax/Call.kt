package com.github.sgdan.kiss.syntax

import com.github.sgdan.kiss.KissLanguage
import com.github.sgdan.kiss.ast.CallNode
import com.oracle.truffle.api.nodes.RootNode

class Call(
        val name: String,
        private val argument: Syntax
) : Syntax() {
    lateinit var node: CallNode

    override fun createNode(kl: KissLanguage) {
        node = CallNode(kl)

        argument.createNode(kl)
    }

    override fun resolve(functions: Map<String, Function>) {
        val function = functions[name]
                ?: throw IllegalStateException("Unable to resolve function: $name")
        node.function = function.node() as RootNode
        node.arg = argument.node()

        argument.resolve(functions)
    }

}
