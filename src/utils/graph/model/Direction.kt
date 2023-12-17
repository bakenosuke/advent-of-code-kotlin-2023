package utils.graph.model

enum class Direction {
    U {
        override val turnLeft get() = L
        override val turnRight get() = R
        override val turnAround get() = D
        override val moveCoordinate get() = Coordinate(-1, 0)
    },
    D {
        override val turnLeft get() = R
        override val turnRight get() = L
        override val turnAround get() = U
        override val moveCoordinate get() = Coordinate(1, 0)

    },
    L {
        override val turnLeft get() = D
        override val turnRight get() = U
        override val turnAround get() = R
        override val moveCoordinate get() = Coordinate(0, -1)
    },
    R {
        override val turnLeft get() = U
        override val turnRight get() = D
        override val turnAround get() = L
        override val moveCoordinate get() = Coordinate(0, 1)
    };

    abstract val turnLeft: Direction
    abstract val turnRight: Direction
    abstract val turnAround: Direction

    abstract val moveCoordinate: Coordinate
}