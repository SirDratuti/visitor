package visitor

import token.Token

interface TokenVisitor {

    fun visit(number: Token.Number)

    fun visit(brace: Token.Brace)

    fun visit(operation: Token.Operation)
}
