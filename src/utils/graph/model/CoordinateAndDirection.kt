package utils.graph.model

data class CoordinateAndDirection(
    val coordinate: Coordinate,
    val direction: Direction,
    val directionCount: Int,
    val maxDirectionCount: Int = -1
) {
    fun neighbours(): List<CoordinateAndDirection> {
        return buildList {
            if (maxDirectionCount < 0 || directionCount < maxDirectionCount ) {
                add(CoordinateAndDirection(coordinate + direction.moveCoordinate, direction, directionCount + 1))
            }
            add(CoordinateAndDirection(coordinate + direction.turnRight.moveCoordinate, direction.turnRight, 1))
            add(CoordinateAndDirection(coordinate + direction.turnLeft.moveCoordinate, direction.turnLeft, 1))
        }
    }
}