package day01

import println
import readInput

fun main() {
    println("Test Part 1")
    testPart1()
    println()

    println("Test Part 2")
    testPart2()
    println()

    println("Puzzle")
    val input = readInput("day01/puzzle/input")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Int {
    val calibrationValues = input.map { determineCalibrationValue(it) }
    return calibrationValues.sum()
}

fun part2(input: List<String>): Int {
    val calibrationValues = input.map { determineCalibrationValue(it) }
    return calibrationValues.sum()
}

fun testPart1() {
    val exampleInput = readInput("day01/test/part1")
    val exampleExpected = 142

    val exampleResult = part1(exampleInput)

    check(exampleResult == exampleExpected) {
        println(exampleResult)
    }
    println("Passed")
}

fun testPart2() {
    val exampleInput = readInput("day01/test/part2")
    val exampleExpected = 281

    val exampleResult = part2(exampleInput)

    check(exampleResult == exampleExpected) {
        println(exampleResult)
    }
    println("Passed")
}

fun determineCalibrationValue(input: String): Int {
    val sanitisedInput = input.sanitise()
    val reverseSanitisedInput = input.reverseSanitise()

    val firstDigit = sanitisedInput.firstOrNull { it.isDigit() }?.digitToInt() ?: return 0
    val lastDigit = reverseSanitisedInput.lastOrNull { it.isDigit() }?.digitToInt() ?: return 0

    val result =  (firstDigit * 10) + lastDigit
    println("$input -> $sanitisedInput -> $result")

    return result
}

val translationMap = listOf(
    "zero" to "0",
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)

fun String.sanitise(): String {
    var unsanitised = this.lowercase()
    var sanitised = ""

    while (unsanitised.isNotEmpty()) {
        val firstMatch = translationMap.firstOrNull { unsanitised.startsWith(it.first) }
        if (firstMatch != null) {
            sanitised += firstMatch.second
            unsanitised = unsanitised.drop(firstMatch.first.length)
        } else {
            sanitised += unsanitised.take(1)
            unsanitised = unsanitised.drop(1)
        }
    }

    return sanitised
}

fun String.reverseSanitise(): String {
    var unsanitised = this.lowercase().reversed()
    var sanitised = ""

    while (unsanitised.isNotEmpty()) {
        val firstMatch = translationMap.firstOrNull { unsanitised.startsWith(it.first.reversed()) }
        if (firstMatch != null) {
            sanitised += firstMatch.second
            unsanitised = unsanitised.drop(firstMatch.first.length)
        } else {
            sanitised += unsanitised.take(1)
            unsanitised = unsanitised.drop(1)
        }
    }

    return sanitised.reversed()
}