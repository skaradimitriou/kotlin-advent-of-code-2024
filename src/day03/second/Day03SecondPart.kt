package day03.second

import readInput

private const val ACTUAL_DATA = "day03/second/day03_actual_second_part"
private const val ACTUAL_SOLUTION_VALUE = 88802350

private val BEFORE_LABELS_REGEX: Regex = Regex("^(.*?)(?=(do\\(\\)|don't\\(\\)))")
private val DO_LABELS_REGEX: Regex = Regex("(do\\(\\)[^d]*)")
private val VALID_MUL_REGEX: Regex = Regex("mul\\(\\d+,\\d+\\)")

fun main() {
    println("DAY 03 Challenge (Second Part)...")

    val actualSolution = solve(readInput(ACTUAL_DATA).toString())
    check(actualSolution == ACTUAL_SOLUTION_VALUE)
}

private fun solve(input: String): Int {
    println("Original Data => $input")
    println("-------")

    val sumOfPairsMultiplied = listOf(
        input.ofPattern(BEFORE_LABELS_REGEX).extractValidMulOccurrences().extractIntPairs(),
        input.ofPattern(DO_LABELS_REGEX).extractValidMulOccurrences().extractIntPairs()
    ).flatten()
        .also { println("Final pairs => $it") }
        .sumOf { it.first * it.second }

    println("The sum of pairs after multiplication is: $sumOfPairsMultiplied")
    return sumOfPairsMultiplied
}

private fun String.ofPattern(regex: Regex): String = regex.findAll(this)
    .map { it.value }
    .toList()
    .toString()

private fun String.extractValidMulOccurrences(): List<String> {
    val matches = VALID_MUL_REGEX.findAll(this)
        .map { it.value }
        .toList()

    println("Valid Mul Occurrences => $matches")
    println("-------------")

    return matches
}

private fun List<String>.extractIntPairs(): List<Pair<Int, Int>> {
    val intPairRegex = Regex("mul\\((\\d+),(\\d+)\\)")
    val pairs: List<Pair<Int, Int>> = mapNotNull {
        intPairRegex.find(it)?.destructured
    }.map { (first, second) ->
        first.toInt() to second.toInt()
    }

    println("Mul Pairs => $pairs")
    println("-------------")

    return pairs
}