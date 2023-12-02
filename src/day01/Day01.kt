package day01

import println
import readInput

fun main() {
    val solver = Day01()
    val input = readInput("src/day01/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day01 {

    fun part1(input: List<String>): Int {
        val calibrationValues = input.map { determineCalibrationValuePart1(it) }
        return calibrationValues.sum()
    }

    private fun determineCalibrationValuePart1(input: String): Int {
        val firstDigit = input.first { it.isDigit() }.digitToInt()
        val lastDigit = input.last { it.isDigit() }.digitToInt()

        return (firstDigit * 10) + lastDigit
    }

    fun part2(input: List<String>): Int {
        val calibrationValues = input.map { determineCalibrationValuePart2(it) }
        return calibrationValues.sum()
    }

    private fun determineCalibrationValuePart2(input: String): Int {
        val firstDigit = input.firstDigit()
        val lastDigit = input.lastDigit()

        return (firstDigit * 10) + lastDigit
    }

    private fun String.firstDigit(): Int {
        var input = this
        while (input.isNotEmpty()) {
            val firstMatch = translationMap.firstOrNull { input.startsWith(it.first) }
            if (firstMatch != null) return firstMatch.second
            input = input.drop(1)
        }
        return 0
    }

    private fun String.lastDigit(): Int {
        var input = this.reversed()
        while (input.isNotEmpty()) {
            val firstMatch = translationMap.firstOrNull { input.startsWith(it.first.reversed()) }
            if (firstMatch != null) return firstMatch.second
            input = input.drop(1)
        }
        return 0
    }

    private val translationMap = setOf(
        "zero" to 0,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "0" to 0,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
    )

}