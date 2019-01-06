package com.github.sgdan.kiss.syntax

import com.github.sgdan.kiss.KissLanguage
import com.oracle.truffle.api.nodes.ExecutableNode

abstract class Syntax {
    fun node(): ExecutableNode {
        return when (this) {
            is Call -> node
            is Conditional -> node
            is Function -> node
            is Literal -> node
            is Operation -> node
            is Script -> node
            is Variable -> node
            else -> throw IllegalStateException("Unknown type: ${javaClass.simpleName}")
        }
    }

    /**
     * @return a Truffle AST node representing this syntax
     *         Note: Node will not be complete until resolve method called
     */
    open fun createNode(kl: KissLanguage) {
        throw NotImplementedError()
    }

    /**
     * Resolve the components and return the node
     */
    open fun resolve(functions: Map<String, Function>) {
        // some nodes don't need to do anything
    }
}
