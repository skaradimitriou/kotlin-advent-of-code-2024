package day03.first

import readInput

private const val TEST_DATA = "day03/first/day03_test"
private const val ACTUAL_DATA = "day03/first/day03_actual"

private const val TEST_SOLUTION_VALUE = 161
private const val ACTUAL_SOLUTION_VALUE = 174336360

fun main() {
    println("DAY 03 Challenge...")
    println("-------")

    val testSolution = solve(readInput(TEST_DATA))
    check(testSolution == TEST_SOLUTION_VALUE)

    println("~~~~~~~~~~~")
    println("~~~~~~~~~~~")

    val actualSolution = solve(readInput(ACTUAL_DATA))
    check(actualSolution == ACTUAL_SOLUTION_VALUE)
}

private fun solve(input: List<String>): Int {
    println("Original Data => $input")
    println("-------")

    val mulOccurrences: List<String> = input.toString().extractValidMulOccurrences()
    val pairs: List<Pair<Int, Int>> = mulOccurrences.extractIntPairs()
    val sumOfPairsMultiplied = pairs.sumOf { it.first * it.second }

    println("The sum of pairs after multiplication is: $sumOfPairsMultiplied")
    return sumOfPairsMultiplied
}


private fun String.extractValidMulOccurrences(): List<String> {
    val regex = Regex("mul\\(\\d+,\\d+\\)")
    val matches = regex.findAll(this)
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