package com.github.sgdan.kiss.syntax

import com.github.sgdan.kiss.KissLanguage
import com.github.sgdan.kiss.ast.OperationNode

class Operation(
        private val lhs: Syntax,
        private val rhs: Syntax,
        private val op: Operator
) : Syntax() {
    lateinit var node: OperationNode

    override fun createNode(kl: KissLanguage) {
        node = OperationNode(kl, op)

        lhs.createNode(kl)
        rhs.createNode(kl)
    }

    override fun resolve(functions: Map<String, Function>) {
        node.lhs = lhs.node()
        node.rhs = rhs.node()

        lhs.resolve(functions)
        rhs.resolve(functions)
    }

}
