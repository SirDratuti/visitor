import application.Application
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CalcVisitorTest {
    @Test
    fun `should correctly add two numbers`() {
        assertCorrectlyCalculated(SIMPLE_ADDITION, 3)
    }

    @Test
    fun `should correctly apply operations priorities`() {
        assertCorrectlyCalculated(SIMPLE_PRIORITIES_RIGHT, 7)
        assertCorrectlyCalculated(SIMPLE_PRIORITIES_LEFT, 7)
    }

    @Test
    fun `should correctly parse braces`() {
        assertCorrectlyCalculated(SIMPLE_BRACKETS, -3)
    }

    @Test
    fun `should correctly parse lab's example`() {
        assertCorrectlyCalculated(LABS_EXAMPLE, 4)
    }

    private fun assertCorrectlyCalculated(input: String, expected: Int) {
        Application(input, System.out).apply {
            tokenize()
            val result = calculate()
            assertEquals(expected, result)
        }
    }
}