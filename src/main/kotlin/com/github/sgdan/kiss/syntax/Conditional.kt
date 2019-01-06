package com.github.sgdan.kiss.syntax

import com.github.sgdan.kiss.KissLanguage
import com.github.sgdan.kiss.ast.ConditionalNode

class Conditional(
        private val test: Syntax,
        private val positive: Syntax,
        private val otherwise: Syntax
) : Syntax() {
    lateinit var node: ConditionalNode

    override fun createNode(kl: KissLanguage) {
        node = ConditionalNode(kl)

        test.createNode(kl)
        positive.createNode(kl)
        otherwise.createNode(kl)
    }

    override fun resolve(functions: Map<String, Function>) {
        node.test = test.node()
        node.positive = positive.node()
        node.otherwise = otherwise.node()

        test.resolve(functions)
        positive.resolve(functions)
        otherwise.resolve(functions)
    }

}
