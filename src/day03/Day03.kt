package day03

import readInput

fun main() {
    val solver = Day03()
    val input = readInput("src/day03/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day03 {

    data class Location(val row: Int, val col: Int)

    data class NumberLocation(
        val number: Int,
        val locations: List<Location>
    )

    fun part1(input: List<String>): Int {
        val numberLocations = getNumberLocations(input)
        val symbolLocations = getSymbolLocations(input)

        val numbersNearSymbols = numberLocations.filter { (number, numberLocation) ->
            numberLocation.any {
                it.getNeighbouringLocations().any { symbolLocations.contains(it) }
            }
        }

        return numbersNearSymbols.sumOf { it.number }
    }

    fun Location.getNeighbouringLocations(): List<Location> {
        return listOf(
            Location(row - 1, col - 1),
            Location(row - 1, col),
            Location(row - 1, col + 1),
            Location(row, col - 1),
            Location(row, col + 1),
            Location(row + 1, col - 1),
            Location(row + 1, col),
            Location(row + 1, col + 1),
        )
    }

    fun getNumberLocations(input: List<String>): List<NumberLocation> {
        val numberLocations = mutableListOf<NumberLocation>()
        input.forEachIndexed { row, line ->
            var num = 0
            var locations = mutableListOf<Location>()
            line.forEachIndexed { col, char ->
                if (char.isDigit()) {
                    num = num * 10 + char.digitToInt()
                    locations.add(Location(row, col))
                } else {
                    if (num > 0) {
                        numberLocations.add(NumberLocation(num, locations))
                        num = 0
                        locations = mutableListOf()
                    }
                }
            }
            if (num > 0) {
                numberLocations.add(NumberLocation(num, locations))
                num = 0
                locations = mutableListOf()
            }
        }
        return numberLocations
    }

    fun getSymbolLocations(input: List<String>): List<Location> {
        val locations = mutableListOf<Location>()
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (!char.isDigit() && char != '.') {
                    locations.add(Location(row, col))
                }
            }
        }
        return locations
    }

    fun getAstrixLocations(input: List<String>): List<Location> {
        val locations = mutableListOf<Location>()
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char == '*') {
                    locations.add(Location(row, col))
                }
            }
        }
        return locations
    }

    fun part2(input: List<String>): Int {
        val numberLocations = getNumberLocations(input)
        val astrixLocations = getAstrixLocations(input)

        val astrixAndNeighbours = astrixLocations.map {
            val nearNumbers = numberLocations.filter { numberLocation ->
                numberLocation.locations.any { location ->
                    it.getNeighbouringLocations().contains(location)
                }
            }
            nearNumbers
        }

        return astrixAndNeighbours.filter { it.size == 2 }.sumOf { it.first().number * it.last().number}
    }

}