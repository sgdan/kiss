package com.github.sgdan.kiss.syntax

enum class Operator(val symbol: String) {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    companion object {
        fun fromString(value: String): Operator =
                Operator.values().find { it.symbol == value }
                        ?: throw IllegalArgumentException(
                                "No operator with symbol: $value")
    }
}
