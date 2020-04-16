package com.knowledgespike.stackunderflow

fun main(args: Array<String>) {
    val logger = Logger(MapConfiguration(), NioPaths(), NioFiles())

    val filename = logger.createLog()
    println("Created: $filename")
}