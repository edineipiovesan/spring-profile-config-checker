package com.github.edineipiovesan.chain.base

interface Command<T : Context> {
    fun executeBefore(context: T): T = context
    fun execute(context: T): T
    fun executeAfter(context: T): T = context
}