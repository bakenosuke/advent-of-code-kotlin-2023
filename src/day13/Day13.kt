package day13

import readInput

fun main() {
    val solver = Day13()
    val input = readInput("src/day13/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day13 {

    data class Reflection(
        val type: ReflectionType,
        val index: Int
    ) {
        enum class ReflectionType { VERTICAL, HORIZONTAL }
    }

    fun part1(input: List<String>): Long {
        val groups = input.joinToString("\n").split("\n\n").map {
            it.split("\n")
        }
        val reflections = groups.map { it.findReflection() }
        val score = reflections.sumOf {
            when (it.type) {
                Reflection.ReflectionType.HORIZONTAL -> 100 * it.index
                Reflection.ReflectionType.VERTICAL -> it.index
            }
        }

        return score.toLong()
    }

    fun List<String>.findReflection(): Reflection {
        // Vertical
        (1 until this[0].length).map { i ->
            val compares = Pair(
                this.map { it.substring(0, i) },
                this.map { it.substring(i, it.length) }
            )
            if (compares.isVerticalReflection()) {
                return Reflection(Reflection.ReflectionType.VERTICAL, i)
            }
        }

        // Horizontal
        (1 until this.size).map { i ->
            val compares = Pair(
                this.subList(0, i),
                this.subList(i, this.size)
            )
            if (compares.isHorizontalReflection()) {
                return Reflection(Reflection.ReflectionType.HORIZONTAL, i)
            }
        }

        return Reflection(
            type = Reflection.ReflectionType.VERTICAL,
            index = 0
        )
    }

    fun Pair<List<String>, List<String>>.isVerticalReflection(differences :Int = 0): Boolean {
        val left = first.map { it.takeLast(second.first().length) }
        val right = second.map { it.take(first.first().length) }

        return Pair(left, right.map { it.reversed() }).countDifferences() == differences
    }

    fun Pair<List<String>, List<String>>.isHorizontalReflection(differences :Int = 0): Boolean {
        val top = first.takeLast(second.size)
        val bottom = second.take(first.size)
        return Pair(top, bottom.reversed()).countDifferences() == differences
    }

    fun Pair<List<String>, List<String>>.countDifferences(): Int {
        var differences = 0
        first.forEachIndexed{ rowIndex, row ->
            row.forEachIndexed { colIndex, col ->
                if(col != second[rowIndex][colIndex]) {
                    differences ++
                }
            }
        }
        return differences
    }

    fun part2(input: List<String>): Long {
        val groups = input.joinToString("\n").split("\n\n").map {
            it.split("\n")
        }
        val reflections = groups.map { it.findReflection2() }
        val score = reflections.sumOf {
            when (it.type) {
                Reflection.ReflectionType.HORIZONTAL -> 100 * it.index
                Reflection.ReflectionType.VERTICAL -> it.index
            }
        }

        return score.toLong()
    }

    fun List<String>.findReflection2(): Reflection {
        // Vertical
        (1 until this[0].length).map { i ->
            val compares = Pair(
                this.map { it.substring(0, i) },
                this.map { it.substring(i, it.length) }
            )
            if (compares.isVerticalReflection(1)) {
                return Reflection(Reflection.ReflectionType.VERTICAL, i)
            }
        }

        // Horizontal
        (1 until this.size).map { i ->
            val compares = Pair(
                this.subList(0, i),
                this.subList(i, this.size)
            )
            if (compares.isHorizontalReflection(1)) {
                return Reflection(Reflection.ReflectionType.HORIZONTAL, i)
            }
        }

        return Reflection(
            type = Reflection.ReflectionType.VERTICAL,
            index = 0
        )
    }

}