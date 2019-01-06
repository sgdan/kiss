package com.github.sgdan.kiss.syntax

import com.github.sgdan.kiss.KissLanguage
import com.github.sgdan.kiss.ast.ScriptNode

class Script(
        private val functions: Map<String, Function>,
        private val expression: Syntax
) : Syntax() {
    lateinit var node: ScriptNode

    override fun createNode(kl: KissLanguage) {
        node = ScriptNode(kl)

        functions.values.forEach { it.createNode(kl) }
        expression.createNode(kl)
    }

    override fun resolve(functions: Map<String, Function>) {
        node.body = expression.node()

        expression.resolve(functions)
        functions.values.forEach { it.resolve(functions) }
    }

    fun toAST(kl: KissLanguage): ScriptNode {
        createNode(kl)
        resolve(functions)
        return node
    }
}
