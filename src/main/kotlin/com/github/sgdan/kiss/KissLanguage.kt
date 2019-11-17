package com.github.sgdan.kiss

import com.oracle.truffle.api.CallTarget
import com.oracle.truffle.api.Truffle
import com.oracle.truffle.api.TruffleLanguage
import com.oracle.truffle.api.TruffleLanguage.Registration
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@Registration(id = "kiss", name = "A simple, stupid language", version = "0.1")
class KissLanguage : TruffleLanguage<KissContext>() {

    override fun createContext(env: Env) = KissContext()

    override fun isObjectOfLanguage(`object`: Any): Boolean = false

    override fun parse(request: ParsingRequest): CallTarget {
        val script = Visitor.parse(this, request.source.characters.toString())
        return Truffle.getRuntime().createCallTarget(script)!!
    }

}
