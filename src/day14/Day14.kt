package day14

import readInput

fun main() {
    val solver = Day14()
    val input = readInput("src/day14/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day14 {

    fun part1(input: List<String>): Long {
        val grid = input.map {
            it.toCharArray().toMutableList()
        }.toMutableList()

        grid.print()
        grid.flipNorth()
        grid.print()

        return grid.sum()
    }

    fun MutableList<MutableList<Char>>.print() {
        println("======")
        forEachIndexed { rowIndex, row ->
            println(row.joinToString(""))
        }
        println("======")
    }

    fun MutableList<MutableList<Char>>.sum(): Long {
        var score = 0L
        forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, col ->
                if (col == 'O') {
                    score += this.size - rowIndex
                }
            }
        }
        return score
    }

    fun MutableList<MutableList<Char>>.flipNorth(): MutableList<MutableList<Char>> {
        var found = true
        while (found) {
            found = false
            forEachIndexed { rowIndex, row ->
                if (rowIndex > 0) {
                    row.forEachIndexed { colIndex, col ->
                        val current = this[rowIndex][colIndex]
                        val above = this[rowIndex - 1][colIndex]
                        if (current == 'O' && above == '.') {
                            this[rowIndex][colIndex] = above
                            this[rowIndex - 1][colIndex] = current
                            found = true
                        }
                    }
                }
            }
        }
        return this
    }

    fun MutableList<MutableList<Char>>.flipSouth(): MutableList<MutableList<Char>> {
        var found = true
        while (found) {
            found = false
            forEachIndexed { rowIndex, row ->
                if (rowIndex < this.size - 1) {
                    row.forEachIndexed { colIndex, col ->
                        val current = this[rowIndex][colIndex]
                        val below = this[rowIndex + 1][colIndex]
                        if (current == 'O' && below == '.') {
                            this[rowIndex][colIndex] = below
                            this[rowIndex + 1][colIndex] = current
                            found = true
                        }
                    }
                }
            }
        }
        return this
    }

    fun MutableList<MutableList<Char>>.flipLeft(): MutableList<MutableList<Char>> {
        var found = true
        while (found) {
            found = false
            forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, col ->
                    if (colIndex > 0) {
                        val current = this[rowIndex][colIndex]
                        val left = this[rowIndex][colIndex - 1]
                        if (current == 'O' && left == '.') {
                            this[rowIndex][colIndex] = left
                            this[rowIndex][colIndex - 1] = current
                            found = true
                        }
                    }
                }
            }
        }
        return this
    }

    fun MutableList<MutableList<Char>>.flipRight(): MutableList<MutableList<Char>> {
        var found = true
        while (found) {
            found = false
            forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, col ->
                    if (colIndex < row.size - 1) {
                        val current = this[rowIndex][colIndex]
                        val right = this[rowIndex][colIndex + 1]
                        if (current == 'O' && right == '.') {
                            this[rowIndex][colIndex] = right
                            this[rowIndex][colIndex + 1] = current
                            found = true
                        }
                    }
                }
            }
        }
        return this
    }

    fun part2(input: List<String>): Long {
        var grid = input.map {
            it.toCharArray().toMutableList()
        }.toMutableList()

        grid.print()
        val patterns = mutableListOf<String>()
        var consecutiveRepeats = 0L
        var firstRepeat = -1
        var repeats = mutableSetOf<Int>()
        run breaking@{
            (0..1_000_000_000).forEach {
                grid.flipNorth()
                grid.flipLeft()
                grid.flipSouth()
                grid.flipRight()
                if (patterns.contains(grid.toPattern())) {
                    if (firstRepeat == -1) firstRepeat = it
                    repeats.add(patterns.indexOf(grid.toPattern()))
                    consecutiveRepeats++
                    if (consecutiveRepeats > 1000) {
                        return@breaking
                    }
                } else {
                    patterns.add(grid.toPattern())
                    consecutiveRepeats = 0
                }
            }
        }

        println("First $firstRepeat")
        println("First ${repeats.size}")

        val lookup = 1_000_000_000 - firstRepeat
        val index = (lookup % repeats.size)

        grid = input.map {
            it.toCharArray().toMutableList()
        }.toMutableList()

        repeat(index + firstRepeat) {
            grid.flipNorth()
            grid.flipLeft()
            grid.flipSouth()
            grid.flipRight()
        }

        return grid.sum()
    }

    fun MutableList<MutableList<Char>>.toPattern(): String {
        return joinToString("\n") {
            it.joinToString("")
        }
    }
}