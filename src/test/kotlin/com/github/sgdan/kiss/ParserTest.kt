package com.github.sgdan.kiss

import io.kotlintest.data.forall
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row
import mu.KotlinLogging
import org.graalvm.polyglot.Context

private val logger = KotlinLogging.logger {}

/**
 * Each example Kiss file in src/test/resources contains some
 * code and a comment with the expected result. Parse and execute
 * each one.
 */
class ParserTest : StringSpec({
    val context = Context.create()

    "can load language" {
        context.engine.languages.keys shouldContain "kiss"
    }

    "run test scripts" {
        // kiss script -> expected result
        forall(
                // literal evaluates to itself
                row("5", "5"),

                // variable defaults to error in script
                row("$", "error"),

                // some arithmetic
                row("5 + 12", "17"),
                row("2 + (5 * 20) - (6 / 3)", "100"),

                // function calls
                row("increment { $ + 1 } increment 1", "2"),
                row("inc { $ + 1 } inc (inc 1)", "3"),
                row("a { $ + $ } b { $ / 3 } a b 6", "4"),

                // conditionals zero or negative is false, positive is true
                row("0 ? 3 : 4", "4"),
                row("(0 - 4) ? 3 : 4", "4"),
                row("1 ? 3 : 4", "3"),

                // nested function
                row("inc { $ + 1 } again { inc $ + 1 } again 6", "8"),

                // recursion
                row("sum { $ ? sum 0 : 0 } sum 1", "0"),

                // factorial (no tail recursion so subject to stack overflow)
                row("fact { $ ? $ * fact ($ - 1) : 1 } fact 5", "120")
        ) { script, result ->
            context.eval("kiss", script).asString() shouldBe result
        }
    }
})
