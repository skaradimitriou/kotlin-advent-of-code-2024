package day02.first

import readInput


private const val TEST_DATA = "day02/day02_test_data"
private const val ACTUAL_DATA = "day02/day02_actual_data"


enum class Mode {
    INIT,
    EQUAL,
    INCREASING,
    DECREASING
}

fun main() {
    val testData = readInput(TEST_DATA)
    val testResult = solve(testData)
    check(testResult == 2)

    println("-------")

    val actualData = readInput(ACTUAL_DATA)
    val actualResult = solve(actualData)
    check(actualResult == 252)
}

private fun solve(input: List<String>): Int {
    val original = input.map { text: String -> text.split(" ") }

    val totalSafeReports = original.map {
        val isSafe = it.checkIfSafeSequence()

        if (isSafe) {
            println("$it : Safe")
        } else {
            println("$it : Unsafe")
        }

        isSafe
    }.count { it }

    println("$totalSafeReports reports are safe.")

    return totalSafeReports
}

private fun List<String>.checkIfSafeSequence(): Boolean {
    var mode = Mode.INIT
    for (i in 0 until size - 1) {
        val difference = this[i + 1].toInt() - this[i].toInt()

        val newMode = when {
            difference > 0 -> Mode.INCREASING
            difference < 0 -> Mode.DECREASING
            else -> Mode.EQUAL
        }

        if (mode != Mode.INIT && mode != newMode) {
            return false
        }

        when {
            difference == 0 -> return false
            difference > 3 -> return false
            difference < -3 -> return false
        }

        mode = newMode
    }

    return true
}