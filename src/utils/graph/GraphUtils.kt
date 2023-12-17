package utils.graph

import utils.graph.model.Coordinate
import java.lang.IndexOutOfBoundsException

fun <T> List<List<T>>.getByCoordinate(coordinate: Coordinate): T? {
    return try {
        this[coordinate.row][coordinate.col]
    } catch(e:IndexOutOfBoundsException) {
        null
    }
}