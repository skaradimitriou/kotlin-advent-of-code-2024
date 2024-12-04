package day02.second

import readInput


const val TEST_DATA = "day02/day02_test_data"
const val ACTUAL_DATA = "day02/day02_actual_data"

enum class Mode {
    INIT,
    EQUAL,
    INCREASING,
    DECREASING
}

fun main() {
    val testData = readInput(TEST_DATA)
    val testResult = solve(testData)
    check(testResult == 4)

    val actualData = readInput(ACTUAL_DATA)
    val actualResult = solve(actualData)
    check(actualResult == 324)
}

private fun solve(input: List<String>): Int {
    val original = input.map { text: String -> text.split(" ") }
    val totalSafeReports = original.addSafeLabels().count { it }
    println("$totalSafeReports reports are safe.")

    return totalSafeReports
}

private fun List<List<String>>.addSafeLabels() = map {
    val isSafe = when {
        it.checkIfSafeSequence() -> {
            println("$it: Safe without removing any level")
            true
        }

        it.checkIfItCanBeSafeByRemovingOne() -> {
            println("$it: Safe by removing one level")
            true
        }

        else -> {
            println("$it: Unsafe regardless of which level is removed")
            false
        }
    }

    isSafe
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

        when {
            // mode changed
            mode != Mode.INIT && mode != newMode -> return false
            // is equal some other char
            difference == 0 -> return false
            // out of allowed increase range
            difference > 3 -> return false
            //out of allowed decreasing range
            difference < -3 -> return false
        }

        mode = newMode
    }

    return true
}

fun List<String>.checkIfItCanBeSafeByRemovingOne(): Boolean {
    for (i in indices) {
        val newSequence = filterIndexed { index, _ -> index != i }
        if (newSequence.checkIfSafeSequence()) {
            return true
        }
    }
    return false
}