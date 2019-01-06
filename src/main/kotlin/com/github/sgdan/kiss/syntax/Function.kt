package com.github.sgdan.kiss.syntax

import com.github.sgdan.kiss.KissLanguage
import com.github.sgdan.kiss.ast.FunctionNode

class Function(
        val name: String,
        private val expression: Syntax
) : Syntax() {
    lateinit var node: FunctionNode

    override fun createNode(kl: KissLanguage) {
        node = FunctionNode(kl)

        expression.createNode(kl)
    }

    override fun resolve(functions: Map<String, Function>) {
        node.body = expression.node()

        expression.resolve(functions)
    }
}
