package day02

import readInput

fun main() {
    val solver = Day02()
    val input = readInput("src/day02/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day02 {

    fun part1(input: List<String>): Int {
        val colourAvailable = ColourCount(r = 12, g = 13, b = 14)
        val maxColourCounts = input.map { getMaxColours(it) }

        var sumOfPossibleIds = 0
        maxColourCounts.forEach { maxColourCount ->
            if (colourAvailable.r >= maxColourCount.second.r && colourAvailable.g >= maxColourCount.second.g && colourAvailable.b >= maxColourCount.second.b) {
                sumOfPossibleIds += maxColourCount.first
            }
        }

        return sumOfPossibleIds
    }

    data class ColourCount(var r: Int = 0, var g: Int = 0, var b: Int = 0)

    fun getMaxColours(input: String): Pair<Int, ColourCount> {
        val game = input.substringBefore(":").replace("Game ", "").toInt()
        val sets = input.substringAfter(":").split(";")
        val maxColourCount = ColourCount()
        sets.forEach {
            val tokens = it.split(",")
            tokens.map { it.trim() }.forEach {
                val amount = it.substringBefore(" ").toInt()
                val colour = it.substringAfter(" ")
                when (colour) {
                    "red" -> {
                        maxColourCount.r = maxOf(maxColourCount.r, amount)
                    }

                    "blue" -> {
                        maxColourCount.b = maxOf(maxColourCount.b, amount)
                    }

                    "green" -> {
                        maxColourCount.g = maxOf(maxColourCount.g, amount)
                    }

                    else -> {}
                }
            }
        }
        return Pair(game, maxColourCount)
    }

    fun part2(input: List<String>): Int {
        val maxColourCounts = input.map { getMaxColours(it) }

        var sumOfPowers = 0
        maxColourCounts.forEach { maxColourCount ->
            sumOfPowers += (maxColourCount.second.r *maxColourCount.second.g *maxColourCount.second.b)
        }

        return sumOfPowers
    }

}