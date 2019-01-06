package com.github.sgdan.kiss.syntax

import com.github.sgdan.kiss.KissLanguage
import com.github.sgdan.kiss.ast.VariableNode

class Variable : Syntax() {
    lateinit var node: VariableNode

    override fun createNode(kl: KissLanguage) {
        node = VariableNode(kl)
    }
}
