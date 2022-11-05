package visitor

import token.Token
import java.util.Stack

class CalcVisitor : TokenVisitor {
    private val stack: Stack<Token.Number> = Stack()

    override fun visit(operation: Token.Operation) {
        try {
            val left = stack.pop()
            val right = stack.pop()
            stack.push(operation.apply(left, right))
        } catch (e: NoSuchElementException) {
            error("Not enough items for implement operation")
        }
    }

    override fun visit(number: Token.Number) {
        stack.push(number)
    }

    override fun visit(brace: Token.Brace) = error("Can't evaluate brace")

    fun calcTokens(tokens: List<Token>): Int {
        tokens.forEach { it.accept(this) }
        return stack.pop().value
    }
}
