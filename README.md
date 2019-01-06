# Kiss

## Background

The [GraalVM](http://www.graalvm.org/) project is a "universal virtual machine" that extends the existing
JVM and adds support for various other languages like Python and Ruby. It also includes
[Truffle](https://github.com/oracle/graal/blob/master/truffle/README.md) which is a language implementation
framework, designed to make it easier to run other languages on the JVM. It is
[getting close to release 1.0](https://github.com/oracle/graal/releases), currently at RC10 although a Windows
version is not yet available (just Mac and Linux).

To learn about Truffle they suggest:

- subclassing [TruffleLanguage](https://github.com/graalvm/simplelanguage) and build from there
- or forking [SimpleLanguage](https://github.com/graalvm/simplelanguage) which is a working implementation 

I chose the first option because I wanted to:

- start with something even simpler (although of course less complete and more stupid!) 
- use gradle (SimpleLanguage uses maven)
- use kotlin (SimpleLanguage uses java)
- incorporate antlr4 parser into the build (SimpleLanguage also uses antlr4)

The Kiss implementation is very simple, and supports only:

- basic integer arithmetic
- function calls with one argument
- limited recursion (function code can call itself)

## Build and Run

Install GraalVM. My testing is on Mac using RC10. It should be possible to run on Linux
or perhaps even use the [docker image](https://hub.docker.com/r/oracle/graalvm-ce/) 
```
$ java -version
openjdk version "1.8.0_192"
OpenJDK Runtime Environment (build 1.8.0_192-20181024123616.buildslave.jdk8u-src-tar--b12)
GraalVM 1.0.0-rc10 (build 25.192-b12-jvmci-0.53, mixed mode)
```

Clone this repository, then build and run unit tests using the gradle wrapper:
```
./gradlew clean build
```

To build a command line executable, run:
```
./gradlew installDist
```

Test by running some one-line scripts
(see [ParserTest.kt](src/test/kotlin/com/github/sgdan/kiss/ParserTest.kt)
for more examples):
```
kiss$ ./build/install/kiss/bin/kiss
> 3 + 68
71

> inc {$+1} inc 7
8

> fact { $ ? $*fact($-1) : 1 } fact 5
120

```