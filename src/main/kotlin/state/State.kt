package state

import token.Token
import token.Tokenizer

sealed interface State {

    fun next(tokenizer: Tokenizer)

    fun createToken(tokenizer: Tokenizer): Token

    object Number : State {
        override fun next(tokenizer: Tokenizer) {
            tokenizer.state = when {
                tokenizer.isOperation() || tokenizer.isBrace() -> Start
                tokenizer.isEOF -> End
                else -> Error
            }
        }

        override fun createToken(tokenizer: Tokenizer) = Token.Number(tokenizer.parseNumber())
    }

    object Start : State {
        override fun next(tokenizer: Tokenizer) {
            tokenizer.state = when {
                tokenizer.isOperation() || tokenizer.isBrace() -> Start
                tokenizer.isEOF -> End
                tokenizer.isDigit -> Number
                else -> Error
            }
        }

        override fun createToken(tokenizer: Tokenizer) = when (val char = tokenizer.currentChar) {
            '+' -> Token.Operation.Plus
            '-' -> Token.Operation.Minus
            '*' -> Token.Operation.Multiplication
            '/' -> Token.Operation.Division
            '(' -> Token.Brace.OpenBrace
            ')' -> Token.Brace.CloseBrace
            else -> error("Invalid char $char")
        }.also { tokenizer.nextChar() }
    }

    object End : State {
        override fun next(tokenizer: Tokenizer) = error("Can't get next token from end state")

        override fun createToken(tokenizer: Tokenizer) = error("Can't create token from end state")
    }

    object Error : State {
        override fun next(tokenizer: Tokenizer) = error("Can't get next token from error state")

        override fun createToken(tokenizer: Tokenizer) = error("Can't create token from error state")
    }
}