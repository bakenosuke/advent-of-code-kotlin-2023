package day06

import readInput

fun main() {
    val solver = Day06()
    val input = readInput("src/day06/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day06 {

    fun part1(input: List<String>): Int {
        val time = input[0].substringAfter(":").readNumbers()
        val distance = input[1].substringAfter(":").readNumbers()

        val solutions = time.indices.map {
            getNumberOfSolutions(time[it].toLong(), distance[it].toLong())
        }
        var result = 1
        solutions.forEach {
            result *= it
        }
        return result
    }

    fun String.readNumbers(): List<Int> {
        return split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
    }

    fun getNumberOfSolutions(time: Long, distance: Long): Int {
        var solutions = 0
        (1..time).forEach {
            val timeToFinish = it.toDouble() + (distance.toDouble() / it.toDouble())
            if (timeToFinish < time) {
                solutions++
            }
        }
        return solutions
    }

    fun part2(input: List<String>): Int {
        val time = input[0].substringAfter(":").replace(" ", "").toLong()
        val distance = input[1].substringAfter(":").replace(" ", "").toLong()
        return getNumberOfSolutions(time, distance)
    }

}