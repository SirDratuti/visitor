package token

import visitor.TokenVisitor

typealias IntOperation = (Int, Int) -> Int

sealed interface Token {
    fun accept(visitor: TokenVisitor)

    class Number(val value: Int) : Token {
        override fun accept(visitor: TokenVisitor) = visitor.visit(this)

        override fun toString() = "Number($value)"
        override fun equals(other: Any?) = (other as? Number)?.value == value
        override fun hashCode() = value.hashCode()
    }

    sealed interface Brace : Token {
        override fun accept(visitor: TokenVisitor) = visitor.visit(this)

        object OpenBrace : Brace {
            override fun toString() = "("
        }

        object CloseBrace : Brace {
            override fun toString() = ")"
        }
    }

    sealed interface Operation : Token {
        val priority: Int
        val operation: IntOperation

        fun apply(left: Number, right: Number) = Number(operation(right.value, left.value))
        override fun accept(visitor: TokenVisitor) = visitor.visit(this)

        object Plus : Operation {
            override val priority = LOW_PRIORITY
            override val operation: IntOperation = Int::plus

            override fun toString() = "PLUS"
        }

        object Minus : Operation {
            override val priority = LOW_PRIORITY
            override val operation: IntOperation = Int::minus

            override fun toString() = "MINUS"
        }

        object Multiplication : Operation {
            override val priority = HIGH_PRIORITY
            override val operation: IntOperation = Int::times

            override fun toString() = "MULTIPLICATION"
        }

        object Division : Operation {
            override val priority = HIGH_PRIORITY
            override val operation: IntOperation = Int::div

            override fun toString() = "DIVISION"
        }

        companion object {
            const val LOW_PRIORITY = 1
            const val HIGH_PRIORITY = 2
        }
    }
}
