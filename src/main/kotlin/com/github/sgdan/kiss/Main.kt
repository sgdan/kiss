package com.github.sgdan.kiss

import org.graalvm.polyglot.Context
import org.graalvm.polyglot.PolyglotException
import kotlin.system.exitProcess

val context: Context by lazy { Context.create() }

fun execute(script: String): String {
    return try {
        context.eval("kiss", script)
    } catch (e: PolyglotException) {
        e
    }.toString()
}

/**
 * Main method to run Kiss scripts from command line
 */
fun main(args: Array<String>) {
    while (true) {
        print("> ")
        val line = readLine()
        when (line) {
            null, "", "quit", "exit" -> exitProcess(0)
            else -> println(execute(line))
        }
        println()
    }
}