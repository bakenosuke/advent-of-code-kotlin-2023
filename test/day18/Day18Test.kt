package day18

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day18Test {

    private val solver = Day18()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day18/part1")
        val expected = 4361

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example`() {
        val input = readInput("test/day18/part1")
        val expected = 467835

        val result = solver.part2(input)
        result shouldBe expected
    }

}