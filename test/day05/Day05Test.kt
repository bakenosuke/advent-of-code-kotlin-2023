package day05

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day05Test {

    private val solver = Day05()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day05/part1")
        val expected = 35

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example`() {
        val input = readInput("test/day05/part1")
        val expected = 46

        val result = solver.part2(input)
        result shouldBe expected
    }

}