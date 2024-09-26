package day23

import readInput
import utils.graph.model.Coordinate
import kotlin.math.max

fun main() {
    val solver = Day23()
    val input = readInput("src/day23/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day23 {

    companion object {
        const val DISTANCE_TO_NEIGHBOUR = 1L
    }

    fun part1(input: List<String>): Long {
        val trailMap = input.map { it.toCharArray() }.toTypedArray() // y, x
        val start = Coordinate(col = input.first().indexOfFirst { it == '.' }, row = 0)
        val goal = Coordinate(col = input.last().indexOfFirst { it == '.' }, row = input.lastIndex)
        val solution = takeAHike(start, goal) { here ->
            here.neighbours()
                .filter { neighbour -> neighbour existsIn trailMap }
                .filter { neighbour -> canMoveIcy(here, neighbour, trailMap) }
                .map { neighbour -> neighbour to DISTANCE_TO_NEIGHBOUR }
        }

        return solution
    }

    private fun canMoveIcy(from: Coordinate, to: Coordinate, trailMap: Array<CharArray>): Boolean {
        val toTile = trailMap[to]
        val canMove = when (toTile) {
            '.' -> true
            '^' -> from.row - 1 == to.row && from.col == to.col
            'v' -> from.row + 1 == to.row && from.col == to.col
            '<' -> from.col - 1 == to.col && from.row == to.row
            '>' -> from.col + 1 == to.col && from.row == to.row
            else -> false
        }
        return canMove
    }

    private operator fun Array<CharArray>.get(coordinate: Coordinate) = this[coordinate.row][coordinate.col]

    /**
     * @param neighboursOf a function that returns the valid neighbours of a given coordinate
     */
    private fun takeAHike(
        start: Coordinate,
        goal: Coordinate,
        neighboursOf: (Coordinate) -> List<Pair<Coordinate, Long>>
    ): Long {
        val visited = mutableSetOf<Coordinate>()

        return takePath(start, goal, visited, neighboursOf, 0, 0)
    }

    private fun takePath(
        location: Coordinate,
        goal: Coordinate,
        visited: MutableSet<Coordinate>,
        neighboursOf: (Coordinate) -> List<Pair<Coordinate, Long>>,
        currentLength: Long,
        longestLength: Long
    ): Long {
        if (location == goal) {
            return max(currentLength, longestLength)
        }

        visited += location
        val neighbours = neighboursOf(location)
            .filter { (neighbour, _) -> neighbour !in visited }
        val maxNeighbourPathLength = neighbours.maxOfOrNull { (neighbour, lengthToNeighbour) ->
            takePath(neighbour, goal, visited, neighboursOf, lengthToNeighbour + currentLength, longestLength)
        } ?: 0
        visited -= location

        return max(maxNeighbourPathLength, longestLength)
    }

    fun part2(input: List<String>): Long {
        val trailMap = input.map { it.toCharArray() }.toTypedArray() // y, x
        val start = Coordinate(col = input.first().indexOfFirst { it == '.' }, row = 0)
        val goal = Coordinate(col = input.last().indexOfFirst { it == '.' }, row = input.lastIndex)
        val solution = takeAHike(start, goal) { here ->
            here.neighbours()
                .filter { neighbour -> neighbour existsIn trailMap }
                .filter { neighbour -> canMoveDry(here, neighbour, trailMap) }
                .map { neighbour -> neighbour to DISTANCE_TO_NEIGHBOUR }
        }

        return solution
    }


    private fun canMoveDry(from: Coordinate, to: Coordinate, trailMap: Array<CharArray>): Boolean {
        return trailMap[to] != '#'
    }

}