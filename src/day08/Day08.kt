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
        val mappings = input.loadMappings()
        println(mappings)
        return instructions.follow(mappings, "AAA", "ZZZ")
    }

    fun CharArray.follow(mappings: Map<String, String>, start: String, target: String): Int {
        var current = start
        var steps = 0

        while (true) {
            forEach { instruction ->
                if (current == target) return steps

                current = mappings[current + instruction]!!
                steps++
            }
        }
    }

    fun List<String>.loadMappings(): Map<String, String> {
        val mappings = mutableMapOf<String, String>()
        drop(2).forEach {
            val key = it.substring(0, 3)

            mappings["${key}L"] = it.substring(7, 10)
            mappings["${key}R"] = it.substring(12, 15)
        }
        return mappings
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0].toCharArray()
        val mappings = input.loadMappings()

        val starts = mappings.map { it.key.dropLast(1) }.filter { it.endsWith("A") }
        val firstZ = mutableListOf<Long>()
        runBlocking {
            starts.forEach {
                launch {
                    firstZ.add(instructions.follow(mappings, listOf(it), "Z"))
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

    fun CharArray.follow(mappings: Map<String, String>, start: List<String>, target: String): Long {
        var current = start
        var steps = 0L

        while (true) {
            forEach { instruction ->
                if (current.all { it.endsWith(target) }) return steps

                current = current.map {
                    mappings[it + instruction]!!
                }
                steps++
            }
        }
    }

}