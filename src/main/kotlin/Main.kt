import application.Application


fun main() {
    Application(readLine()!!, System.out).apply {
        tokenize()
        print()
        println(calculate())
        clear()
    }
}