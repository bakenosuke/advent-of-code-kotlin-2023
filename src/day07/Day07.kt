package day07

import readInput

fun main() {
    val solver = Day07()
    val input = readInput("src/day07/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2(input)}")
}

class Day07 {

    enum class Type(val rank: Int) {
        FIVE_OF_A_KIND(1), FOUR_OF_A_KIND(2), FULL_HOUSE(3), THREE_OF_A_KIND(4), TWO_PAIR(5), ONE_PAIR(6), HIGH_CARD(7)
    }

    enum class Card(val rank: Int) {
        A(14), K(13), Q(12), J(11), T(10), NINE(9), EIGHT(8), SEVEN(7), SIX(6), FIVE(5), FOUR(4), THREE(3), TWO(2),
    }

    data class Row(val hand: List<Card>, val bid: Int, val type: Type, val raw: String)

    fun part1(input: List<String>): Int {
        val rows = input.map {
            val hand = it.substringBefore(" ").toCharArray()
            val bid = it.substringAfter(" ").toInt()

            val type = hand.toType()
            Row(hand = hand.map { it.toCard() }, bid = bid, type = type, raw = it)
        }

        val sortedRows = rows.sortedWith(compareBy(
            { -it.type.rank },
            { it.hand[0].rank },
            { it.hand[1].rank },
            { it.hand[2].rank },
            { it.hand[3].rank },
            { it.hand[4].rank }
        ))

        return sortedRows.mapIndexed { i, row ->
            row.bid * (i + 1)
        }.sum()
    }

    fun Char.toCard(): Card {
        return when (this) {
            'A' -> Card.A
            'K' -> Card.K
            'Q' -> Card.Q
            'J' -> Card.J
            'T' -> Card.T
            '9' -> Card.NINE
            '8' -> Card.EIGHT
            '7' -> Card.SEVEN
            '6' -> Card.SIX
            '5' -> Card.FIVE
            '4' -> Card.FOUR
            '3' -> Card.THREE
            '2' -> Card.TWO
            else -> Card.A
        }
    }

    fun Char.toCard2(): Card2 {
        return when (this) {
            'A' -> Card2.A
            'K' -> Card2.K
            'Q' -> Card2.Q
            'J' -> Card2.J
            'T' -> Card2.T
            '9' -> Card2.NINE
            '8' -> Card2.EIGHT
            '7' -> Card2.SEVEN
            '6' -> Card2.SIX
            '5' -> Card2.FIVE
            '4' -> Card2.FOUR
            '3' -> Card2.THREE
            '2' -> Card2.TWO
            else -> Card2.A
        }
    }

    fun CharArray.toType(): Type {
        if (distinct().size == 1) return Type.FIVE_OF_A_KIND
        if (distinct().size == 5) return Type.HIGH_CARD

        val groupCount = groupCount()
        if (groupCount[0].second == 5) return Type.FIVE_OF_A_KIND
        if (groupCount[0].second == 4) return Type.FOUR_OF_A_KIND
        if (groupCount[0].second == 3 && groupCount[1].second == 2) return Type.FULL_HOUSE
        if (groupCount[0].second == 3 && groupCount[1].second == 1) return Type.THREE_OF_A_KIND
        if (groupCount[0].second == 2 && groupCount[1].second == 2) return Type.TWO_PAIR
        if (groupCount[0].second == 2 && groupCount[1].second == 1 && groupCount[2].second == 1) return Type.ONE_PAIR
        return Type.HIGH_CARD
    }

    enum class Card2(val rank: Int) {
        A(14), K(13), Q(12), T(10), NINE(9), EIGHT(8), SEVEN(7), SIX(6), FIVE(5), FOUR(4), THREE(3), TWO(2), J(1)
    }

    fun CharArray.groupCount():  MutableList<Pair<Char, Int>> {
        return toList().groupCount()
    }

    fun List<Char>.groupCount(): MutableList<Pair<Char, Int>> {
        return groupBy { it }.mapValues { it.value.size }.entries.sortedByDescending { it.value }.map { Pair(it.key, it.value) }.toMutableList()
    }

    data class Row2(val hand: List<Card2>, val bid: Int, val type: Type, val raw: String)


    fun CharArray.toType2(): Type {
        if (distinct().size == 1) return Type.FIVE_OF_A_KIND

        val js = filter { it == 'J' }.size
        val groupCount = filter { it != 'J' }.groupCount()
        groupCount[0] = Pair(groupCount[0].first, groupCount[0].second + js)

        if (groupCount[0].second == 5) return Type.FIVE_OF_A_KIND
        if (groupCount[0].second == 4) return Type.FOUR_OF_A_KIND
        if (groupCount[0].second == 3 && groupCount[1].second == 2) return Type.FULL_HOUSE
        if (groupCount[0].second == 3 && groupCount[1].second == 1) return Type.THREE_OF_A_KIND
        if (groupCount[0].second == 2 && groupCount[1].second == 2) return Type.TWO_PAIR
        if (groupCount[0].second == 2 && groupCount[1].second == 1 && groupCount[2].second == 1) return Type.ONE_PAIR
        return Type.HIGH_CARD
    }

    fun part2(input: List<String>): Int {
        val rows = input.map {
            val hand = it.substringBefore(" ").toCharArray()
            val bid = it.substringAfter(" ").toInt()

            val type = hand.toType2()
            Row2(hand = hand.map { it.toCard2() }, bid = bid, type = type, raw = it)
        }

        val sortedRows = rows.sortedWith(compareBy(
            { -it.type.rank },
            { it.hand[0].rank },
            { it.hand[1].rank },
            { it.hand[2].rank },
            { it.hand[3].rank },
            { it.hand[4].rank }
        ))

        return sortedRows.mapIndexed { i, row ->
            row.bid * (i + 1)
        }.sum()
    }

}