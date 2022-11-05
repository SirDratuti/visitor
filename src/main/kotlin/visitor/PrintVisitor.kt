package visitor

import token.Token
import java.io.OutputStream
import java.io.PrintStream

class PrintVisitor(out: OutputStream) : TokenVisitor {

    private val printStream = PrintStream(out)

    override fun visit(number: Token.Number) = printStream.write(number)

    override fun visit(operation: Token.Operation) = printStream.write(operation)

    override fun visit(brace: Token.Brace) = error("No braces in output")

    fun printTokens(tokens: List<Token>) {
        tokens.forEach { it.accept(this) }
        printStream.println()
    }

    private fun PrintStream.write(token: Token) = print("$token ")
}
