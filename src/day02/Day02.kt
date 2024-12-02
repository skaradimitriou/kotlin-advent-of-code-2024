package day02

import readInput


const val TEST_DATA = "day02/day02_test_data"
const val ACTUAL_DATA = "day02/day02_actual_data"

val ALLOWED_DECREASING_RANGE = -2..-1
val ALLOWED_INCREASING_RANGE = 1..3

private class SolutionMetadata(
    var isValid: Boolean = true,
    var mode: Mode = Mode.UNSPECIFIED,
    var differences: MutableList<Int> = mutableListOf()
)

enum class Mode {
    UNSPECIFIED,
    EQUAL,
    INCREASING,
    DECREASING
}

enum class Label {
    SAFE,
    UNSAFE
}

fun main() {
    val testDataOutput: Int = solve(input = readInput(TEST_DATA))

    println("$testDataOutput reports are safe")
    check(testDataOutput == 2)

    val actualDataOutput: Int = solve(input = readInput(ACTUAL_DATA))
    println("$actualDataOutput reports are safe")
    check(actualDataOutput == 252)
}

private fun solve(input: List<String>): Int {
    return input.map { text: String -> text.split(" ") }
        .map { levels: List<String> -> levels.calculateDifferences() }
        .map { differences: List<SolutionMetadata> -> differences.toSafeOrUnsafeLabels() }
        .count { label -> label == Label.SAFE }
}

private fun List<String>.calculateDifferences(): List<SolutionMetadata> {
    var tempElement: Int? = null
    val metadata = SolutionMetadata()
    val differences: MutableList<Int> = mutableListOf()
    val elements: MutableList<SolutionMetadata> = mutableListOf()

    forEach { element ->
        val intElement: Int = element.toInt()

        tempElement?.let { item ->
            val newMode: Mode = when {
                intElement > item -> Mode.INCREASING
                intElement < item -> Mode.DECREASING
                else -> Mode.EQUAL
            }

            if (metadata.mode != Mode.UNSPECIFIED && metadata.mode != newMode) {
                metadata.isValid = false
            } else {
                metadata.mode = newMode
            }

            val difference: Int = when (metadata.mode) {
                Mode.INCREASING -> intElement - item
                Mode.DECREASING -> item - intElement
                else -> item
            }

            differences.add(difference)

            metadata.differences = differences
            elements.add(metadata)

            tempElement = intElement
        } ?: run {
            tempElement = element.toInt()
        }
    }


    return elements
}

private fun List<SolutionMetadata>.toSafeOrUnsafeLabels(): Label {
    var label = Label.SAFE

    forEach { solutionMetadata ->
        if (!solutionMetadata.isValid) {
            label = Label.UNSAFE
            return label
        }

        for (difference in solutionMetadata.differences) {
            label = difference.isBetweenValidRange().toLabel()

            if (label == Label.UNSAFE) {
                break
            }
        }
    }

    return label
}

private fun Int.isBetweenValidRange(): Boolean {
    return this in ALLOWED_DECREASING_RANGE || this in ALLOWED_INCREASING_RANGE
}

private fun Boolean.toLabel(): Label = if (this) Label.SAFE else Label.UNSAFE