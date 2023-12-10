package day10

import readInput

fun main() {
    val solver = Day10()
    val input = readInput("src/day10/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input, 'L')}")
}

class Day10 {

    data class Cell(
        var char: Char,
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

        map.print()

        return map.maxOf { it.maxOf { it.distanceFromStart } }
    }

    fun List<List<Cell>>.print() {
        forEach {
            it.forEach {
                if (it.distanceFromStart == -1) {
                    print("[    ]")
                } else {
                    print("[${it.distanceFromStart.toString().padStart(4, ' ')}]")
                }
            }
            println()
        }
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
                        if (target.distanceFromStart == -1 && listOf(
                                'S',
                                '|',
                                'J',
                                'L'
                            ).contains(current.char) && listOf('|', '7', 'F').contains(target.char)
                        ) {
                            target.distanceFromStart = distanceFromStart + 1
                            somethingFound = true
                        }
                    }

                    // Down
                    if (row < rows - 1) {
                        val target = this[row + 1][col]
                        if (target.distanceFromStart == -1 && listOf(
                                'S',
                                '|',
                                'F',
                                '7'
                            ).contains(current.char) && listOf('|', 'L', 'J').contains(target.char)
                        ) {
                            target.distanceFromStart = distanceFromStart + 1
                            somethingFound = true
                        }
                    }

                    // Left
                    if (col > 0) {
                        val target = this[row][col - 1]
                        if (target.distanceFromStart == -1 && listOf(
                                'S',
                                '-',
                                'J',
                                '7'
                            ).contains(current.char) && listOf('-', 'L', 'F').contains(target.char)
                        ) {
                            target.distanceFromStart = distanceFromStart + 1
                            somethingFound = true
                        }
                    }

                    // Right
                    if (col < cols - 1) {
                        val target = this[row][col + 1]
                        if (target.distanceFromStart == -1 && listOf(
                                'S',
                                '-',
                                'F',
                                'L'
                            ).contains(current.char) && listOf('-', 'J', '7').contains(target.char)
                        ) {
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

    fun part2(input: List<String>, s: Char): Int {
        val map = input.map { it.toCharArray().toList().map { Cell(char = it) } }

        for (row in map.indices) {
            for (col in map[row].indices) {
                if (map[row][col].char == 'S') {
                    map[row][col].distanceFromStart = 0
                }
            }
        }

        map.mapDistances(0)

        map.print()

        val dots = mutableListOf<Pair<Int, Int>>()
        map.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, col ->
                if (col.distanceFromStart == -1) {
                    col.char = '.'
                }
                if (col.char == '.') {
                    dots.add(Pair(rowIndex, colIndex))
                }
                if (col.char == 'S') {
                    col.char = s
                }
            }
        }

        println(dots.size)

        val rows = map.size
        val cols = map[0].size
        val dotsIn = dots.filter { dot ->
            println("dot: $dot")
            val row = map[dot.first].joinToString("") {
                if (it.distanceFromStart > 0) {
                    it.char.toString()
                } else {
                    "."
                }
            }

            val left = row.substring(0, dot.second).mapToStuffWeCareAbout()
            val right = if (dot.second < cols - 1) {
                row.substring(dot.second + 1, cols).mapToStuffWeCareAbout()
            } else {
                ""
            }
            val toTheLeft = left.count()
            val toTheRight = right.count()

            println(row)
            println("left: $left, right: $right")

            toTheLeft % 2 == 1 && toTheRight % 2 == 1
        }

        map.print(dotsIn)
        return dotsIn.size
    }

    fun String.mapToStuffWeCareAbout(): String {
        return this
            .replace("-", "")
            .replace(".", "")
            .replace("L7", "|")
            .replace("FJ", "|")
            .replace("LJ", "||")
            .replace("F7", "||")
            .replace("7", "")
            .replace("L", "")
            .replace("F", "")
            .replace("J", "")

    }

    fun List<List<Cell>>.print(dotsIn: List<Pair<Int, Int>>) {
        forEachIndexed { row, it ->
            it.forEachIndexed { col, it ->
                if (it.distanceFromStart == -1) {
                    if (dotsIn.contains(Pair(row, col))) {
                        print("[ I  ]")
                    } else {
                        print("[ O  ]")
                    }
                } else {
                    print("[${it.distanceFromStart.toString().padStart(4, ' ')}]")
                }
            }
            println()
        }
    }

}