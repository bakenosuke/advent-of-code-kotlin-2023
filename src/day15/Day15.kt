package day15

import readInput

fun main() {
    val solver = Day15()
    val input = readInput("src/day15/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day15 {

    fun part1(input: List<String>): Long {
        val tokens = input.first().split(",")

        val scores = tokens.map { it.score() }

        return scores.sum()
    }

    fun String.score(): Long {
        var i = 0L
        toCharArray().forEach {
            i += it.code
            i *= 17
            i = i % 256
        }
        return i
    }

    fun part2(input: List<String>): Long {
        val tokens = input.first().split(",")

        val boxes = tokens.box()

        var total = 0L
        boxes.forEachIndexed { boxIndex, box ->
            box.forEachIndexed { slotIndex, slot ->
                total += slot.value * (slotIndex + 1) * (boxIndex + 1)
            }
        }

        return total
    }

    fun List<String>.box(): List<List<LabelValue>> {
        val boxes = (0..255).map {
            mutableListOf<LabelValue>()
        }

        forEach {
            val label = it.substringBefore("=").substringBefore("-")
            val score = label.score()
            val box = boxes[score.toInt()]
            when {
                it.contains('-') -> {
                    box.removeIf { it.label == label }
                }
                it.contains('=') -> {
                    val value = it.takeLast(1).toLong()
                    if (box.any { it.label == label }) {
                        box.first { it.label == label }.value = value
                    } else {
                        box.add(LabelValue(label, value))
                    }
                }
            }
        }

        return boxes
    }

    data class LabelValue(
        val label: String,
        var value: Long,
    )

}