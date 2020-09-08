package com.github.edineipiovesan.chain.base

import java.util.LinkedList
import java.util.Queue

open class ChainBase<CMD : Command<CTX>, CTX : Context> {
    private val commandStack: Queue<CMD> = LinkedList()

    fun addCommand(command: CMD) = commandStack.add(command)

    fun execute(context: CTX) {
        var currentContext = context
        while (commandStack.isNotEmpty()) {
            val command = commandStack.poll()
            currentContext = command.executeBefore(currentContext)
            currentContext = command.execute(currentContext)
            currentContext = command.executeAfter(currentContext)
        }
    }
}
