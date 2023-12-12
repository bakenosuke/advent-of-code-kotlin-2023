package day12

import readInput

fun main() {
    val solver = Day12()
    val input = readInput("src/day12/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day12 {

    data class Row(
        val springs: List<Char>,
        val groups: List<Int>
    )

    fun part1(input: List<String>): Long {
        val data = input.map {
            Row(
                springs = it.substringBefore(" ").toCharArray().toList(),
                groups = if (it.contains(" ")) {
                    it.substringAfter(" ").split(",").map { it.toInt() }
                } else {
                    emptyList()
                }
            )
        }

        val possibilities = data.map { it.possibilities() }

        return possibilities.sum()
    }

    fun Row.possibilities(): Long {
        if (groups.isEmpty()) return 1

        var permutations = mutableListOf(this.springs)
        (0 until springs.size).forEach { i ->
            permutations = permutations.flatMap { spring ->
                if (spring[i] == '.' || spring[i] == '#') {
                    listOf(spring)
                } else {
                    listOf(
                        spring.toMutableList().apply { set(i, '.') },
                        spring.toMutableList().apply { set(i, '#') }
                    )
                }
            }.toMutableList()
        }

        return permutations.count { it.matches(groups) }.toLong()
    }

    fun List<Char>.matches(expectedGroups: List<Int>): Boolean {
        val actualGroups = mutableListOf<Int>()
        var count = 0
        forEach { spring ->
            if (spring == '#') {
                count++
            } else {
                if (count > 0) {
                    actualGroups.add(count)
                    count = 0
                }
            }
        }
        if (count > 0) {
            actualGroups.add(count)
            count = 0
        }
        return actualGroups == expectedGroups
    }

    fun part2(input: List<String>): Long {
        return 0
    }

}