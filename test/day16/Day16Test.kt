package day16

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day16Test {

    private val solver = Day16()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day16/part1")
        val expected = 46

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example`() {
        val input = readInput("test/day16/part1")
        val expected = 51

        val result = solver.part2(input)
        result shouldBe expected
    }

}