package day12

import readInput

fun main() {
    val solver = Day12()
    val input = readInput("src/day12/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day12 {

    data class Spring(
        val chars: List<Char>,
        var index: Int = 0
    )

    data class Row(
        val springs: List<Spring>,
        val groups: List<Int>
    )

    fun part1(input: List<String>): Long {
        val data = input.map {
            Row(
                springs = it.substringBefore(" ").toCharArray().map {
                    Spring(
                        chars = when (it) {
                            '.' -> listOf('.')
                            '#' -> listOf('#')
                            else -> listOf('.', '#')
                        }
                    )
                }.toList(),
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

        var checked = 0L
        var matches = 0L
        var combination: List<Spring>? = springs
        while (combination != null) {
            checked++
            if (combination.map { it.chars[it.index] }.matches(groups)) {
                matches++
            }
            combination = combination.next()
        }
        return matches
    }

    fun List<Spring>.next(): List<Spring>? {
        forEachIndexed { i, it ->
            if (it.chars.size == 2 && it.index == 0) {
                (0 until i).forEach { j ->
                    this[j].index = 0
                }
                it.index = 1
                return this
            }
        }
        return null
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
        val data = input.map { line ->
            val springs = (0 until 5).joinToString("?") { line.substringBefore(" ") }.toCharArray().map {
                Spring(
                    chars = when (it) {
                        '.' -> listOf('.')
                        '#' -> listOf('#')
                        else -> listOf('.', '#')
                    }
                )
            }
            val groups = (0 until 5).flatMap { line.substringAfter(" ").split(",").map { it.toInt() } }

            Row(
                springs = springs,
                groups = groups
            )
        }

        val possibilities = data.mapIndexed { i, it ->
            println(i + 1)
            it.possibilities2()
        }

        return possibilities.sum()
    }

    fun Row.possibilities2(): Long {
        var combinations = mutableListOf<MutableList<Char>>()
        combinations.add(springs.map { it.chars[it.index] }.toMutableList())

        (0 until springs.size).forEach { i ->
            val spring = springs[i]
            if (spring.chars.size == 2) {
                combinations = combinations.flatMap {
                    spring.chars.map { c ->
                        it.toMutableList().apply {
                            this[i] = c
                        }
                    }
                }.filter {
                    it.matchesSoFar(groups, i + 1)
                }.toMutableList()
            }
        }

        return combinations.filter { it.matches(groups) }.size.toLong()
    }

    fun List<Char>.matchesSoFar(expectedGroups: List<Int>, upTo: Int): Boolean {
        val actualGroups = mutableListOf<Int>()
        var count = 0
        subList(0, upTo).joinToString("").forEach { spring ->
            if (spring == '#') {
                count++
            } else {
                if (count > 0) {
                    actualGroups.add(count)
                    count = 0
                }
            }
        }
        if (size > upTo && get(upTo) == '.') {
            if (count > 0) {
                actualGroups.add(count)
                count = 0
            }
        }

        return if(actualGroups.isEmpty()) {
            true
        } else {
            actualGroups.size <= expectedGroups.size
                    && actualGroups.last() <= expectedGroups[actualGroups.size-1]
                    && actualGroups.subList(0, actualGroups.size - 1) == expectedGroups.subList(0, actualGroups.size - 1)
        }
    }

}