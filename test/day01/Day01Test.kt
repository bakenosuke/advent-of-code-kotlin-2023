package day01

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day01Test {

    private val solver = Day01()

    @Test
    fun `should match part 1 example` () {
        val input = readInput("test/day01/part1")
        val expected = 142

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example` () {
        val input = readInput("test/day01/part2")
        val expected = 281

        val result = solver.part2(input)
        result shouldBe expected
    }

}