package utils.graph.model

data class Coordinate(
    val row: Int,
    val col: Int,
) {
    fun neighbours(): List<Coordinate> {
        return listOf(
            Coordinate(col = col + 1, row = row),
            Coordinate(col = col - 1, row = row),
            Coordinate(col = col, row = row + 1),
            Coordinate(col = col, row = row - 1)
        )
    }

    infix fun existsIn(trailMap: Array<CharArray>): Boolean {
        return row in trailMap.indices && col in trailMap[row].indices
    }

    operator fun minus(other: Coordinate): Coordinate = Coordinate(row - other.row, col - other.col)
    operator fun plus(other: Coordinate) = Coordinate(row + other.row, col + other.col)
}