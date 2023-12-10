package day10

import readInput

fun main() {
    val solver = Day10()
    val input = readInput("src/day10/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day10 {

    data class Cell(
        val char: Char,
        var distanceFromStart: Int = -1
    )

    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray().toList().map { Cell(char = it) } }

        for (row in map.indices) {
            for (col in map[row].indices) {
                if (map[row][col].char == 'S') {
                    map[row][col].distanceFromStart = 0
                }
            }
        }

        map.mapDistances(0)

        map.forEach {
            it.forEach {
                if (it.distanceFromStart == -1) {
                    print("[    ]")
                } else {
                    print("[${it.distanceFromStart.toString().padStart(4, ' ')}]")
                }
            }
            println()
        }

        val max = map.maxOf { it.maxOf { it.distanceFromStart } }

        (0..max).forEach { i ->
            val occurrences = map.sumOf {
                it.count {
                    it.distanceFromStart == i
                }
            }
            println("$i: $occurrences")
        }

        return max
    }

    fun List<List<Cell>>.mapDistances(distanceFromStart: Int) {
        var somethingFound = false
        val rows = this.size
        val cols = this[0].size
        for (row in (0 until rows)) {
            for (col in (0 until cols)) {
                if (this[row][col].distanceFromStart == distanceFromStart) {
                    val current = this[row][col]
                    // Up
                    if (row > 0) {
                        val target = this[row - 1][col]
                        if (target.distanceFromStart == -1 && listOf('S','|', 'J', 'L').contains(current.char) && listOf('|', '7', 'F').contains(target.char)) {
                            target.distanceFromStart = distanceFromStart + 1
                            somethingFound = true
                        }
                    }

                    // Down
                    if (row < rows - 1) {
                        val target = this[row + 1][col]
                        if (target.distanceFromStart == -1 && listOf('S','|', 'F', '7').contains(current.char) && listOf('|', 'L', 'J').contains(target.char)) {
                            target.distanceFromStart = distanceFromStart + 1
                            somethingFound = true
                        }
                    }

                    // Left
                    if (col > 0) {
                        val target = this[row][col - 1]
                        if (target.distanceFromStart == -1 && listOf('S','-', 'J', '7').contains(current.char) && listOf('-', 'L', 'F').contains(target.char)) {
                            target.distanceFromStart = distanceFromStart + 1
                            somethingFound = true
                        }
                    }

                    // Right
                    if (col < cols - 1) {
                        val target = this[row][col + 1]
                        if (target.distanceFromStart == -1 && listOf('S','-', 'F', 'L').contains(current.char) && listOf('-', 'J', '7').contains(target.char)) {
                            target.distanceFromStart = distanceFromStart + 1
                            somethingFound = true
                        }
                    }
                }
            }
        }
        if (somethingFound) {
            mapDistances(distanceFromStart + 1)
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

}