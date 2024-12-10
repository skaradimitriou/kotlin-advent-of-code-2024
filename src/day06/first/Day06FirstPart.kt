package day06.first

import readInput

private const val TEST_DATA = "day06/day06_test_data"
private const val ACTUAL_DATA = "day06/day06_actual_data"

private const val TEST_SOLUTION = 41
private const val ACTUAL_SOLUTION = 4776

private class StartingPosition(
    var startX: Int = -1,
    var startY: Int = -1
)

private const val GUARD_STARTING_POSITION = '^'
private const val OBSTACLE = '#'
private const val VISITED = 'X'

private val DIRECTIONS = listOf(
    Pair(-1, 0), //Up
    Pair(0, 1),  //Right
    Pair(1, 0),  //Down
    Pair(0, -1)  // Left
)

fun main() {
    println("Day 06 Challenge -- First Part")

    solve(data = readInput(TEST_DATA), solution = TEST_SOLUTION)

    repeat(2) {
        print("-----")
    }

    solve(data = readInput(ACTUAL_DATA), solution = ACTUAL_SOLUTION)
}

private fun solve(
    data: List<String>,
    solution: Int
) {
    println("------")
    println("Original Map")

    val lab = data.onEach { line -> println(line) }
    val grid = lab.map { it.toCharArray() }.toTypedArray()

    val startingPosition = grid.findStartingPosition()

    grid.simulateGuardRoute(
        startX = startingPosition.startX,
        startY = startingPosition.startY
    ).onEach { (visitedX: Int, visitedY: Int) ->
        // Mark visited positions with 'X'
        grid[visitedX][visitedY] = VISITED
    }

    val total = grid.sumOf { it.count { char -> char == VISITED } }
    println("-------")
    println("Solution: The guard will visit $total distinct positions on the given map.")
    check(solution == total)
}

private fun Array<CharArray>.findStartingPosition(): StartingPosition {
    val startingPosition = StartingPosition()

    for (i in this.indices) {
        for (j in this[i].indices) {
            if (this[i][j] == GUARD_STARTING_POSITION) {
                startingPosition.startX = i
                startingPosition.startY = j
                break
            }
        }
    }

    repeat(2) {
        println("------")
    }

    println("The guard's starting position indicated as '^' is: X: ${startingPosition.startX}, Y: ${startingPosition.startY}")

    return startingPosition
}

private fun Array<CharArray>.simulateGuardRoute(startX: Int, startY: Int): Set<Pair<Int, Int>> {
    // Starts facing up
    var currentDirection = 0

    var x = startX
    var y = startY
    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(Pair(x, y))

    var inProgress = true
    while (inProgress) {
        try {
            val (dx, dy) = DIRECTIONS[currentDirection]
            val newX = x + dx
            val newY = y + dy

            if (this[newX][newY] == OBSTACLE) {
                // Turn right
                currentDirection = (currentDirection + 1) % 4
                println("Turns right.")
            } else {
                println("Moving forward to X: $newX, Y: $newY.")
                // Move forward
                x = newX
                y = newY
                visited.add(Pair(x, y))
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            inProgress = false
        }
    }

    return visited
}