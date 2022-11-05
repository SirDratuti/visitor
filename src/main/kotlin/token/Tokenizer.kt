package token

import state.State
import java.io.InputStream
import java.io.InputStreamReader

class Tokenizer(inputStream: InputStream) {
    var state: State = State.Start

    val isDigit get() = currentChar.isDigit()
    val isEOF get() = currentChar.code == END_OF_FILE_CODE

    var currentChar = Char(END_OF_FILE_CODE)
        private set

    private var index = -1

    private val inputStreamReader = InputStreamReader(inputStream)
    private val isWhitespace get() = currentChar.isWhitespace()

    fun tokenize(): List<Token> {
        val result = mutableListOf<Token>()
        nextChar()
        state.next(this)
        while (state !is State.Error && state !is State.End) {
            result.add(state.createToken(this))
            skipWhitespace()
            state.next(this)
        }
        return (state as? State.Error)?.let {
            error("Error state at end of tokenize")
        } ?: result
    }

    fun isOperation() = currentChar in listOf('+', '-', '*', '/')

    fun isBrace() = currentChar in listOf('(', ')')

    fun parseNumber() = buildString {
        while (isDigit && !isEOF) {
            append(currentChar)
            nextChar()
        }
    }.toInt()

    fun nextChar() {
        index += 1
        val charCode = when (val code = inputStreamReader.read()) {
            -1 -> 255
            else -> code
        }
        currentChar = Char(charCode)
    }

    private fun skipWhitespace() {
        while (!isEOF && isWhitespace) {
            nextChar()
        }
    }

    private companion object {
        // Char code for end of file because there is no EOF file code we can create
        private const val END_OF_FILE_CODE = 255
    }
}
