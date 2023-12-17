package utils.graph

import java.util.PriorityQueue

object AStarPath {

    data class VisitedVertex<K>(val from: K?, val to: K, val cost: Long)

    data class QueuedVertex<K>(val node: K, val score: Long, val distance: Long) : Comparable<QueuedVertex<K>> {
        override fun compareTo(other: QueuedVertex<K>): Int = (score + distance).compareTo(other.score + other.distance)
    }

    data class AStarPath<T>(
        val start: T,
        val end: T?,
        val visited: Map<T, VisitedVertex<T>>
    )

    fun <T> findShortestPath(
        startNode: T,
        isEnd: (T) -> Boolean,
        neighboursOf: (T) -> List<T>,
        costOf: (T, T) -> Long = { _, _ -> 1 },
        distanceOf: (T) -> Long = { _ -> 0 }
    ): AStarPath<T> {
        val queue = PriorityQueue(listOf(QueuedVertex(startNode, 0, distanceOf(startNode))))
        val visitedNodes = mutableMapOf(startNode to VisitedVertex(null, startNode, 0))
        var endNode: T? = null

        while (endNode == null) {
            if (queue.isEmpty()) {
                return AStarPath(startNode, endNode, visitedNodes)
            }

            val currentVertex = queue.remove()
            endNode = if (isEnd(currentVertex.node)) currentVertex.node else null

            val neighbours = neighboursOf(currentVertex.node)
                .filter { it !in visitedNodes }
                .map { next ->
                    QueuedVertex(
                        next,
                        currentVertex.score + costOf(currentVertex.node, next),
                        distanceOf(next)
                    )
                }

            queue.addAll(neighbours)
            visitedNodes.putAll(neighbours.associate { neighbour ->
                neighbour.node to VisitedVertex(
                    currentVertex.node,
                    neighbour.node,
                    neighbour.score
                )
            })
        }

        return AStarPath(startNode, endNode, visitedNodes)
    }


}