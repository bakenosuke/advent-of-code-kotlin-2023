package day04

import readInput

fun main() {
    val solver = Day04()
    val input = readInput("src/day04/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day04 {

    fun part1(input: List<String>): Int {
        val scores = input.map { cardScore(it) }
        return scores.sumOf { it }
    }

    fun cardScore(input: String): Int {
        val numbers = input.substringAfter(":")
        val winningNumbers = numbers.substringBefore("|").split(" ").map { it.trim() }.filter { it.isNotBlank() }
        val myNumbers = numbers.substringAfter("|").split(" ").map { it.trim() }

        var points = 0
        myNumbers.forEach {
            if (winningNumbers.contains(it)) {
                if (points == 0) {
                    points = 1
                } else {
                    points *= 2
                }
            }
        }
        return points
    }

    fun part2(input: List<String>): Int {
        var scores = input.map { cardScore2(it) }.toMutableList()

        scores.forEachIndexed { i, card ->
            (1..card.points).forEach {
                scores[i + it].cards += card.cards
            }
        }

        scores.forEachIndexed { index, card -> println("$index: ${card.cards}") }

        return scores.sumOf { it.cards }
    }


    fun cardScore2(input: String): Card {
        val numbers = input.substringAfter(":")
        val winningNumbers = numbers.substringBefore("|").split(" ").map { it.trim() }.filter { it.isNotBlank() }
        val myNumbers = numbers.substringAfter("|").split(" ").map { it.trim() }

        var points = 0
        myNumbers.forEach {
            if (winningNumbers.contains(it)) {
                points++
            }
        }
        return Card(points, 1)
    }

    data class Card(
        val points: Int,
        var cards: Int
    )

}