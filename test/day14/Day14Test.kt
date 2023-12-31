package day14

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day14Test {

    private val solver = Day14()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day14/part1")
        val expected = 136

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example`() {
        val input = readInput("test/day14/part1")
        val expected = 64

        val result = solver.part2(input)
        result shouldBe expected
    }

}