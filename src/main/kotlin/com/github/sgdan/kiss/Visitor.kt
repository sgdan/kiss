package com.github.sgdan.kiss

import com.github.sgdan.kiss.KissParser.*
import com.github.sgdan.kiss.ast.ScriptNode
import com.github.sgdan.kiss.syntax.*
import com.github.sgdan.kiss.syntax.Function
import com.github.sgdan.kiss.syntax.Operator.Companion.fromString
import mu.KotlinLogging
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.lang.IllegalStateException

/**
 * First convert the antlr4 parse tree to syntax objects, then a
 * Truffle AST.
 *
 * parse tree -> syntax -> truffle AST
 */
class Visitor(private val kl: KissLanguage) : KissBaseVisitor<Syntax>() {
    companion object {
        fun parse(kl: KissLanguage, source: String): ScriptNode {
            // parse from source into parse tree
            val lexer = KissLexer(CharStreams.fromString(source))
            val parser = KissParser(CommonTokenStream(lexer))
            val tree = parser.script()

            // visit parse tree to create syntax
            val script = Visitor(kl).visitScript(tree)

            // turn syntax into truffle AST
            return script.toAST(kl)
        }
    }

    override fun visitScript(ctx: ScriptContext): Script {
        val functions = ctx.function().map {
            visitFunction(it)
        }.associateBy {
            it.name
        }
        val expression = visitExpression(ctx.expression())
        return Script(functions, expression)
    }

    private fun visitExpression(ctx: ExpressionContext): Syntax = when (ctx) {
        is CallContext -> visitCall(ctx)
        is OperationContext -> visitOperation(ctx)
        is BracesContext -> visitBraces(ctx)
        is ConditionalContext -> visitConditional(ctx)
        is LiteralContext -> visitLiteral(ctx)
        is VariableContext -> visitVariable(ctx)
        else -> throw IllegalStateException("Unknown expression context: $ctx")
    }

    override fun visitFunction(ctx: FunctionContext): Function {
        return Function(
                ctx.NAME().text,
                visitExpression(ctx.expression())
        )
    }

    override fun visitCall(ctx: CallContext): Call {
        return Call(
                ctx.NAME().text,
                visitExpression(ctx.expression())
        )
    }

    override fun visitOperation(ctx: OperationContext): Operation {
        return Operation(
                visitExpression(ctx.expression(0)),
                visitExpression(ctx.expression(1)),
                fromString(ctx.SYMBOL().text)
        )
    }

    override fun visitBraces(ctx: BracesContext) = visitExpression(ctx.expression())

    override fun visitConditional(ctx: ConditionalContext): Conditional {
        return Conditional(
                visitExpression(ctx.expression(0)),
                visitExpression(ctx.expression(1)),
                visitExpression(ctx.expression(2))
        )
    }

    override fun visitLiteral(ctx: LiteralContext): Literal {
        return Literal(ctx.INTEGER().text.toBigInteger())
    }

    override fun visitVariable(ctx: VariableContext): Variable {
        return Variable()
    }

}
