package day09

import readInput

fun main() {
    val solver = Day09()
    val input = readInput("src/day09/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day09 {

    fun part1(input: List<String>): Int {
        val patterns = input.map {
            it.expandPatterns()
        }

        val nextItems = patterns.map { pattern ->
            (pattern.size - 1 downTo 0).forEach {
                if (pattern.size - 1 == it) {
                    pattern[it].add(pattern[it].last())
                } else {
                    pattern[it].add(pattern[it].last() + pattern[it + 1].last())
                }
            }

            pattern.first().last()
        }

        return nextItems.sum()
    }

    fun String.expandPatterns(): List<MutableList<Int>> {
        val patterns = mutableListOf(split(" ").map { it.toInt() }.toMutableList())
        var found = false
        while (!found) {
            val row = patterns.last()
            val nextRow = (0 until row.size - 1).map { i ->
                row[i + 1] - row[i]
            }.toMutableList()
            patterns.add(nextRow)

            if (nextRow.distinct().size == 1) {
                found = true
            }
        }

        return patterns
    }

    fun part2(input: List<String>): Int {
        val patterns = input.map {
            it.expandPatterns()
        }

        val previousItems = patterns.map { pattern ->
            (pattern.size - 1 downTo 0).forEach {
                if (pattern.size - 1 == it) {
                    pattern[it].add(0, pattern[it].first())
                } else {
                    pattern[it].add(0, pattern[it].first() - pattern[it + 1].first())
                }
            }

            pattern.first().first()
        }

        return previousItems.sum()
    }

}