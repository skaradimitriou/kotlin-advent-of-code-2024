package day01.second

import readInput

private const val TEST_DATA = "day01/day01_test_data"
private const val ACTUAL_DATA = "day01/day01_actual_data"

private const val TEST_SOLUTION = 31
private const val ACTUAL_SOLUTION = 23177084

fun main() {
    println("DAY 03 Challenge (Second Part)...")
    println("-------")

    val testSimilarityScore = readInput(TEST_DATA).divideIntoColumns().getTotalSimilarityScore()
    check(testSimilarityScore == TEST_SOLUTION)

    val actualSimilarityScore = readInput(ACTUAL_DATA).divideIntoColumns().getTotalSimilarityScore()
    check(actualSimilarityScore == ACTUAL_SOLUTION)
}

fun List<String>.divideIntoColumns(): List<List<String>> = map {
    it.trim().split("\\s+".toRegex())
}

fun List<List<String>>.getTotalSimilarityScore(): Int {
    val firstColumn = map { it[0] }
    val secondColumn = map { it[1] }

    println("Original Input => $this")
    println("-------")
    println("First col : $firstColumn")
    println("Second col : $secondColumn")
    println("-------")

    val totalSimilarityScore = List(secondColumn.size) { index ->
        try {
            val valueFromFirstColumn = firstColumn[index].toInt()
            val occurrences = secondColumn.map { it.toInt() }
                .count { it == valueFromFirstColumn }

            val similarityScore = valueFromFirstColumn.times(occurrences)

            println("Number: $valueFromFirstColumn appears $occurrences times. Similarity Score : $similarityScore")

            similarityScore
        } catch (e: Exception) {
            error("Lists don't have the same size.")
        }
    }.sum()

    println("-------")
    println("The total similarity score is $totalSimilarityScore.")
    return totalSimilarityScore
}