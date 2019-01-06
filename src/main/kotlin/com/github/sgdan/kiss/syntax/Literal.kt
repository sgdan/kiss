package com.github.sgdan.kiss.syntax

import com.github.sgdan.kiss.KissLanguage
import com.github.sgdan.kiss.ast.LiteralNode
import java.math.BigInteger

/**
 * Only integer literals are supported
 */
class Literal(private val value: BigInteger) : Syntax() {
    lateinit var node: LiteralNode

    override fun createNode(kl: KissLanguage) {
        node = LiteralNode(kl, value)
    }
}
