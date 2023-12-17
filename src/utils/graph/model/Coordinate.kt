package utils.graph.model

data class Coordinate(
    val row: Int,
    val col: Int,
) {
    operator fun minus(other: Coordinate): Coordinate = Coordinate(row - other.row, col - other.col)
    operator fun plus(other: Coordinate) = Coordinate(row + other.row, col + other.col)
}