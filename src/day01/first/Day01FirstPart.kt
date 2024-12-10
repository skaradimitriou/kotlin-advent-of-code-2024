package day01.first

import readInput

private const val TEST_DATA = "day01/day01_test_data"
private const val ACTUAL_DATA = "day01/day01_actual_data"

fun main() {
    println("DAY 03 Challenge (First Part)...")
    println("-------")

    val input = readInput(ACTUAL_DATA).map {
        it.trim().split("\\s+".toRegex())
    }

    val firstColumn = input.map { it[0] }.sorted()
    val secondColumn = input.map { it[1] }.sorted()

    val totalDistance = firstColumn.zip(secondColumn).sumOf { pair ->
        val first = pair.first.toInt()
        val second = pair.second.toInt()

        when {
            first > second -> first - second
            else -> second - first
        }
    }

    println(input)
    println("First col : $firstColumn")
    println("Second col : $secondColumn")

    println("Distances: $totalDistance")
}
