package application

import token.Token
import token.Tokenizer
import visitor.CalcVisitor
import visitor.ParserVisitor
import visitor.PrintVisitor
import java.io.OutputStream
import java.nio.charset.StandardCharsets

class Application(
    input: String,
    outputStream: OutputStream = System.out
) {
    private val tokenizer = Tokenizer(input.byteInputStream(StandardCharsets.UTF_8))

    private val parser = ParserVisitor()
    private val printer = PrintVisitor(outputStream)
    private val calculator = CalcVisitor()

    private val parsedTokens = mutableListOf<Token>()

    fun tokenize(): List<Token> {
        val parsed = parser.parseTokens(
            tokenizer.tokenize()
        )
        parsedTokens.addAll(parsed)
        return parsed
    }

    fun print() {
        printer.printTokens(parsedTokens)
    }

    fun calculate() = calculator.calcTokens(parsedTokens)

    fun clear() = parsedTokens.clear()
}