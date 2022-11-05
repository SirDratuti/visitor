package visitor

import token.Token
import java.util.*

class ParserVisitor : TokenVisitor {

    private val tokens = mutableListOf<Token>()
    private val stack: Deque<Token> = ArrayDeque()

    override fun visit(number: Token.Number) {
        tokens.add(number)
    }

    override fun visit(operation: Token.Operation) {
        val currentPriority = when (val peek = stack.peek()) {
            is Token.Operation -> peek.priority
            else -> 0
        }
        while (stack.isNotEmpty() && operation.priority <= currentPriority) {
            tokens.add(stack.pop())
        }
        stack.push(operation)
    }

    override fun visit(brace: Token.Brace) {
        when (brace) {
            is Token.Brace.OpenBrace -> stack.push(brace)
            is Token.Brace.CloseBrace -> {
                while (stack.isNotEmpty() && stack.peek() !is Token.Brace.OpenBrace) {
                    tokens.add(stack.pop())
                }
                stack.pop()
            }
        }
    }

    fun parseTokens(tokens: List<Token>): List<Token> {
        tokens.forEach { it.accept(this) }
        while (stack.isNotEmpty()) {
            this.tokens.add(stack.pop())
        }
        return this.tokens
    }
}