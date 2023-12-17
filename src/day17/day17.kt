package day17

import readInput
import utils.graph.AStarPath
import utils.graph.getByCoordinate
import utils.graph.model.Coordinate
import utils.graph.model.CoordinateAndDirection
import utils.graph.model.Direction

fun main() {
    val solver = Day17()
    val input = readInput("src/day17/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day17 {

    fun part1(input: List<String>): Long {
        val grid = input.map {
            it.map { it.digitToInt() }
        }
        val start = Coordinate(0, 0)
        val end = Coordinate(grid.size - 1, grid.first().size - 1)

        val path = AStarPath.findShortestPath(
            startNode = CoordinateAndDirection(start, Direction.R, 0),
            isEnd = { it.coordinate == end },
            neighboursOf = {
                it.neighbours()
            },
            costOf = { from, to ->
                val fromCost = grid.getByCoordinate(from.coordinate) ?: Int.MAX_VALUE
                val toCost = grid.getByCoordinate(from.coordinate) ?: Int.MAX_VALUE

                toCost.toLong()
            }
        )

        return path.visited[path.end]?.cost ?: -1L
    }


    fun part2(input: List<String>): Long {
        return 0
    }

}