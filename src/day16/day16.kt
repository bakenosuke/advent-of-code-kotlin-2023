package day16

import readInput

fun main() {
    val solver = Day16()
    val input = readInput("src/day16/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day16 {

    enum class Direction {
        U, D, L, R
    }

    data class Beam(
        val row: Int,
        val col: Int,
        val direction: Direction
    )

    fun part1(input: List<String>): Long {
        val map = input.map {
            it.toCharArray().toMutableList()
        }.toMutableList()

        val start = Beam(0, 0, Direction.R)
        val beamHistory = followBeam(setOf(start), map)

        return beamHistory.map { Pair(it.row, it.col) }.distinct().count().toLong()
    }

    fun followBeam(beams: Set<Beam>, map: List<List<Char>>, beamHistory: MutableSet<Beam> = mutableSetOf()): Set<Beam> {
        val rows = map.size
        val cols = map.first().size
        val nextBeams = mutableSetOf<Beam>()
        beams.forEach {
            if (!beamHistory.contains(it)) {
                beamHistory.add(it)
                val curr = map[it.row][it.col]
                when (curr) {
                    '.' -> {
                        when (it.direction) {
                            Direction.U -> {
                                if (it.row > 0) {
                                    nextBeams.add(Beam(it.row - 1, it.col, Direction.U))
                                }
                            }

                            Direction.D -> {
                                if (it.row < rows - 1) {
                                    nextBeams.add(Beam(it.row + 1, it.col, Direction.D))
                                }
                            }

                            Direction.L -> {
                                if (it.col > 0) {
                                    nextBeams.add(Beam(it.row, it.col - 1, Direction.L))
                                }
                            }

                            Direction.R -> {
                                if (it.col < cols - 1) {
                                    nextBeams.add(Beam(it.row, it.col + 1, Direction.R))
                                }
                            }
                        }
                    }

                    '-' -> {
                        when (it.direction) {
                            Direction.U -> {
                                if (it.col > 0) {
                                    nextBeams.add(Beam(it.row, it.col - 1, Direction.L))
                                }
                                if (it.col < cols - 1) {
                                    nextBeams.add(Beam(it.row, it.col + 1, Direction.R))
                                }
                            }

                            Direction.D -> {
                                if (it.col > 0) {
                                    nextBeams.add(Beam(it.row, it.col - 1, Direction.L))
                                }
                                if (it.col < cols - 1) {
                                    nextBeams.add(Beam(it.row, it.col + 1, Direction.R))
                                }
                            }

                            Direction.L -> {
                                if (it.col > 0) {
                                    nextBeams.add(Beam(it.row, it.col - 1, Direction.L))
                                }
                            }

                            Direction.R -> {
                                if (it.col < cols - 1) {
                                    nextBeams.add(Beam(it.row, it.col + 1, Direction.R))
                                }
                            }
                        }
                    }

                    '|' -> {
                        when (it.direction) {
                            Direction.U -> {
                                if (it.row > 0) {
                                    nextBeams.add(Beam(it.row - 1, it.col, Direction.U))
                                }
                            }

                            Direction.D -> {
                                if (it.row < rows - 1) {
                                    nextBeams.add(Beam(it.row + 1, it.col, Direction.D))
                                }
                            }

                            Direction.L -> {
                                if (it.row > 0) {
                                    nextBeams.add(Beam(it.row - 1, it.col, Direction.U))
                                }
                                if (it.row < rows - 1) {
                                    nextBeams.add(Beam(it.row + 1, it.col, Direction.D))
                                }
                            }

                            Direction.R -> {
                                if (it.row > 0) {
                                    nextBeams.add(Beam(it.row - 1, it.col, Direction.U))
                                }
                                if (it.row < rows - 1) {
                                    nextBeams.add(Beam(it.row + 1, it.col, Direction.D))
                                }
                            }
                        }
                    }

                    '/' -> {
                        when (it.direction) {
                            Direction.U -> {
                                if (it.col < cols - 1) {
                                    nextBeams.add(Beam(it.row, it.col + 1, Direction.R))
                                }
                            }

                            Direction.D -> {
                                if (it.col > 0) {
                                    nextBeams.add(Beam(it.row, it.col - 1, Direction.L))
                                }
                            }

                            Direction.L -> {
                                if (it.row < rows - 1) {
                                    nextBeams.add(Beam(it.row + 1, it.col, Direction.D))
                                }
                            }

                            Direction.R -> {
                                if (it.row > 0) {
                                    nextBeams.add(Beam(it.row - 1, it.col, Direction.U))
                                }
                            }
                        }
                    }

                    '\\' -> {
                        when (it.direction) {
                            Direction.U -> {
                                if (it.col > 0) {
                                    nextBeams.add(Beam(it.row, it.col - 1, Direction.L))
                                }

                            }

                            Direction.D -> {
                                if (it.col < cols - 1) {
                                    nextBeams.add(Beam(it.row, it.col + 1, Direction.R))
                                }
                            }

                            Direction.L -> {
                                if (it.row > 0) {
                                    nextBeams.add(Beam(it.row - 1, it.col, Direction.U))
                                }
                            }

                            Direction.R -> {
                                if (it.row < rows - 1) {
                                    nextBeams.add(Beam(it.row + 1, it.col, Direction.D))
                                }
                            }
                        }
                    }
                }
            }
        }
        if (nextBeams.isNotEmpty()) {
            followBeam(nextBeams, map, beamHistory)
        }
        return beamHistory
    }

    fun part2(input: List<String>): Long {
        val map = input.map {
            it.toCharArray().toMutableList()
        }.toMutableList()

        val starts = mutableListOf<Beam>()
        (0 until map.first().size).forEach { col ->
            starts.add(Beam(0, col, Direction.D))
            starts.add(Beam(map.size - 1, col, Direction.U))
        }
        (0 until map.size).forEach { row ->
            starts.add(Beam(row, 0, Direction.R))
            starts.add(Beam(row, map.first().size - 1, Direction.L))
        }

        val energised = starts.map {
            val beamHistory = followBeam(setOf(it), map)
            beamHistory.map { Pair(it.row, it.col) }.distinct().count().toLong()
        }


        return energised.max()
    }


}