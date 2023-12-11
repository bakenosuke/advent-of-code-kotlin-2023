package day11

import readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val solver = Day11()
    val input = readInput("src/day11/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input, 1000000)}")
}

class Day11 {

    data class Location(
        val id: Int,
        var row: Int = -1,
        var col: Int = -1,
        var distance: Int = 1
    )

    fun part1(input: List<String>): Int {
        var i = 0
        var map = input.mapIndexed { rowIndex, row ->
            row.mapIndexed { colIndex, col ->
                if (col == '#') {
                    i++
                    Location(i, rowIndex, colIndex)
                } else {
                    Location(-1, rowIndex, colIndex)
                }
            }.toMutableList()
        }.toMutableList()

        map = map.expand()
        map.print()

        val locations = map.getLocations()
        val permutations = locations.permutate()
        permutations.forEach {
            println("${it.first} -> ${it.second} (${distance(it.first, it.second)})")
        }
        return permutations.sumOf {
//            distance(it.first, it.second, map)
            distance(it.first, it.second)
        }
    }

    fun distance(from: Location, to: Location, map: MutableList<MutableList<Location>>): Int {
        val fromCol = min(from.col, to.col)
        val toCol = max(from.col, to.col)
        val colDistance = (fromCol until toCol).sumOf {
            map[0][it].distance
        }

        val fromRow = min(from.row, to.row)
        val toRow = max(from.row, to.row)
        val rowDistance = (fromRow until toRow).sumOf {
            map[it][0].distance
        }

        return colDistance + rowDistance
    }

    fun distance(from: Location, to: Location): Int {
        return abs(from.row - to.row) + abs(from.col - to.col)
    }

    fun MutableList<Location>.permutate(): MutableList<Pair<Location, Location>> {
        val pairs = mutableListOf<Pair<Location, Location>>()
        (0 until size - 1).forEach { from ->
            (from + 1 until size).forEach { to ->
                pairs.add(Pair(this[from], this[to]))
            }
        }
        return pairs
    }

    fun MutableList<MutableList<Location>>.getLocations(): MutableList<Location> {
        val locations = mutableListOf<Location>()
        forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                if (cell.id != -1) locations.add(cell)
            }
        }
        return locations
    }

    fun MutableList<MutableList<Location>>.print() {
        forEach { row ->
            row.forEach { col ->
                if (col.id == -1) {
                    print("[       ]")
                } else {
                    print("[${col.id.toString().padStart(7)}]")
                }
            }
            println()
        }
    }

    fun MutableList<MutableList<Location>>.expand(times: Int = 2): MutableList<MutableList<Location>> {
        val emptyRows = (0 until this.size).filter { row ->
            var hasContent = false
            for (col in 0 until this[row].size) {
                if (this[row][col].id != -1) {
                    hasContent = true
                    break
                }
            }
            !hasContent
        }
        val emptyCols = (0 until this.size).filter { col ->
            var hasContent = false
            for (row in 0 until this.size) {
                if (this[row][col].id != -1) {
                    hasContent = true
                    break
                }
            }
            !hasContent
        }

        val expanded = this.toMutableList()

        emptyRows.forEach { rowIndex ->
            expanded[rowIndex].forEach { it.distance = times }
        }
        emptyCols.forEach { colIndex ->
            expanded.forEach { row ->
                row[colIndex].distance = times
            }
        }

        expanded.forEach { row ->
            row.forEach { cell ->
                if (cell.id != -1) {
                    cell.row += emptyRows.count { it < cell.row } * (times - 1)
                    cell.col += emptyCols.count { it < cell.col } * (times - 1)
                }
            }
        }

        return expanded
    }

    fun part2(input: List<String>, times: Int): Int {
        var i = 0
        var map = input.mapIndexed { rowIndex, row ->
            row.mapIndexed { colIndex, col ->
                if (col == '#') {
                    i++
                    Location(i, rowIndex, colIndex)
                } else {
                    Location(-1, rowIndex, colIndex)
                }
            }.toMutableList()
        }.toMutableList()

        map = map.expand(times)
        map.print()

        val locations = map.getLocations()
        val permutations = locations.permutate()
        permutations.forEach {
            println("${it.first} -> ${it.second} (${distance(it.first, it.second)})")
        }
        return permutations.sumOf {
            distance(it.first, it.second)
        }
    }

}