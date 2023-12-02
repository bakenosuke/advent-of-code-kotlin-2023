package day02

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day02Test {

    private val solver = Day02()

    @Test
    fun `should match part 1 example` () {
        val input = readInput("test/day02/part1")
        val expected = 8

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example` () {
        val input = readInput("test/day02/part1")
        val expected = 2286

        val result = solver.part2(input)
        result shouldBe expected
    }

}