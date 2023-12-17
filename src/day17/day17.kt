package day17

import readInput

fun main() {
    val solver = Day17()
    val input = readInput("src/day17/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day17 {


    data class Coordinate(
        val row: Int,
        val col: Int,
    )

    enum class Direction {
        U, D, L, R
    }

    data class Option(
        val last3Directions: List<Direction>,
        val coordinate: Coordinate,
        val score: Int
    )

    fun part1(input: List<String>): Long {
        val grid = input.map {
            it.map { it.digitToInt() }
        }
        val options = mutableListOf<Option>()
        options.add(Option(listOf(), Coordinate(0, 0), 0))
        val visited = mutableListOf<Pair<Coordinate, Int>>()

        while (options.isNotEmpty()) {
            options.removeAt(0).let {
                val next = options(it, grid, visited)
                options.addAll(next)
            }
            println(visited.size)
        }

        return visited.filter {
            it.first.row == grid.size - 1 && it.first.col == grid.first().size - 1
        }.minOf { it.second }.toLong()
    }

    fun options(
        option: Option,
        grid: List<List<Int>>,
        visited: MutableList<Pair<Coordinate, Int>>
    ): List<Option> {
        val options = mutableListOf<Option>()
        // Up
        if (option.last3Directions.lastOrNull() != Direction.D && option.last3Directions.count { it == Direction.U } < 3 && option.coordinate.row > 0) {
            val target = Coordinate(option.coordinate.row - 1, option.coordinate.col)
            val score = option.score + grid[target.row][target.col]
            if (!visited.contains(Pair(target, score))) {
                options.add(Option((option.last3Directions + Direction.U).takeLast(3), target, score))
                visited.add(Pair(target, score))
            }
        }
        // Down
        if (option.last3Directions.lastOrNull() != Direction.U && option.last3Directions.count { it == Direction.D } < 3 && option.coordinate.row < grid.size - 1) {
            val target = Coordinate(option.coordinate.row + 1, option.coordinate.col)
            val score = option.score + grid[target.row][target.col]
            if (!visited.contains(Pair(target, score))) {
                options.add(Option((option.last3Directions + Direction.D).takeLast(3), target, score))
                visited.add(Pair(target, score))
            }
        }
        // Left
        if (option.last3Directions.lastOrNull() != Direction.R && option.last3Directions.count { it == Direction.L } < 3 && option.coordinate.col > 0) {
            val target = Coordinate(option.coordinate.row, option.coordinate.col - 1)
            val score = option.score + grid[target.row][target.col]
            if (!visited.contains(Pair(target, score))) {
                options.add(Option((option.last3Directions + Direction.L).takeLast(3), target, score))
                visited.add(Pair(target, score))
            }
        }
        // Right
        if (option.last3Directions.lastOrNull() != Direction.L && option.last3Directions.count { it == Direction.R } < 3 && option.coordinate.col < grid.first().size - 1) {
            val target = Coordinate(option.coordinate.row, option.coordinate.col + 1)
            val score = option.score + grid[target.row][target.col]
            if (!visited.contains(Pair(target, score))) {
                options.add(Option((option.last3Directions + Direction.R).takeLast(3), target, score))
                visited.add(Pair(target, score))
            }
        }
        return options
    }

    fun part2(input: List<String>): Long {
        return 0
    }

}