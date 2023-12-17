package utils.string

fun <T> List<String>.toGrid(cellExtractor: (Char) -> T): List<List<T>> {
    return map {
        it.map {
            cellExtractor.invoke(it)
        }
    }
}