import application.Application
import org.junit.jupiter.api.Test
import token.Token
import kotlin.test.assertEquals

class ParserVisitorTest {

    @Test
    fun `should correctly add two numbers`() {
        assertCorrectlyCalculated(
            SIMPLE_ADDITION,
            listOf(
                Token.Number(1),
                Token.Number(2),
                Token.Operation.Plus,
            )
        )
    }

    @Test
    fun `should correctly apply operations priorities right`() {
        assertCorrectlyCalculated(
            SIMPLE_PRIORITIES_RIGHT,
            listOf(
                Token.Number(1),
                Token.Number(2),
                Token.Number(3),
                Token.Operation.Multiplication,
                Token.Operation.Plus,
            )
        )
    }

    @Test
    fun `should correctly apply operations priorities left`() {
        assertCorrectlyCalculated(
            SIMPLE_PRIORITIES_LEFT,
            listOf(
                Token.Number(2),
                Token.Number(3),
                Token.Operation.Multiplication,
                Token.Number(1),
                Token.Operation.Plus,
            )
        )
    }

    @Test
    fun `should correctly parse braces`() {
        assertCorrectlyCalculated(
            SIMPLE_BRACKETS,
            listOf(
                Token.Number(3),
                Token.Number(2),
                Token.Number(3),
                Token.Operation.Minus,
                Token.Operation.Multiplication,
            )
        )
    }

    @Test
    fun `should correctly parse lab's example`() {
        assertCorrectlyCalculated(
            LABS_EXAMPLE,
            listOf(
                Token.Number(30),
                Token.Number(2),
                Token.Operation.Plus,
                Token.Number(8),
                Token.Operation.Division,
            )
        )
    }

    private fun assertCorrectlyCalculated(input: String, expected: List<Token>) {
        Application(input).apply {
            val result = tokenize()
            assertEquals(expected, result)
        }
    }
}