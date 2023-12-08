package day08

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import readInput
import timer

fun main() {
    val solver = Day08()
    val input = readInput("src/day08/input")
    timer {
        println("Part 1: ${solver.part1(input)}")
    }
    timer {
        println("Part 2: ${solver.part2(input)}")
    }
}

class Day08 {

    data class Group(val l: String, val r: String)

    fun part1(input: List<String>): Int {
        val instructions = input[0].toCharArray()
        val mappings = input.drop(2).map {
            val key = it.substringBefore(" = ")
            val l = it.substringAfter(" = ").replace("(", "").replace(")", "").substringBefore(",").trim()
            val r = it.substringAfter(" = ").replace("(", "").replace(")", "").substringAfter(",").trim()

            key to Group(l, r)
        }

        return instructions.follow(mappings.toMap(), "AAA", "ZZZ")
    }

    fun CharArray.follow(mappings: Map<String, Group>, start: String, target: String): Int {
        var current = start
        var steps = 0

        while (true) {
            forEach { instruction ->
                if (current == target) return steps

                val mapping = mappings[current]!!
                current = if (instruction == 'L') mapping.l else mapping.r
                steps++
            }
        }
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0].toCharArray()
        val mappings = input.drop(2).map {
            val key = it.substringBefore(" = ")
            val l = it.substringAfter(" = ").replace("(", "").replace(")", "").substringBefore(",").trim()
            val r = it.substringAfter(" = ").replace("(", "").replace(")", "").substringAfter(",").trim()

            key to Group(l, r)
        }

        val starts = mappings.map { it.first }.filter { it.endsWith("A") }
        val firstZ = mutableListOf<Long>()
        runBlocking {
            starts.forEach {
                launch {
                    firstZ.add(instructions.follow(mappings.toMap(), listOf(it), "Z"))
                }
            }
        }

        var stepSize = firstZ.max()
        var steps = stepSize
        while (true) {
            if (firstZ.all { steps % it == 0L }) return steps

            steps += stepSize
        }
    }

    fun CharArray.follow(mappings: Map<String, Group>, start: List<String>, target: String): Long {
        var current = start
        var steps = 0L

        while (true) {
            forEach { instruction ->
                if (current.all { it.endsWith(target) } && steps != 0L) return steps

                current = current.map {
                    val mapping = mappings[it]!!
                    if (instruction == 'L') mapping.l else mapping.r
                }
                steps++
            }
        }
    }

}