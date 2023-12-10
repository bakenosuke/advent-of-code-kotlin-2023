package day10

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day10Test {

    private val solver = Day10()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day10/part1")
        val expected = 8

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2a example`() {
        val input = readInput("test/day10/part2a")
        val expected = 4

        val result = solver.part2(input, 'F')
        result shouldBe expected
    }

    @Test
    fun `should match part 2b example`() {
        val input = readInput("test/day10/part2b")
        val expected = 8

        val result = solver.part2(input, 'F')
        result shouldBe expected
    }

    @Test
    fun `should match part 2c example`() {
        val input = readInput("test/day10/part2c")
        val expected = 10

        val result = solver.part2(input, '7')
        result shouldBe expected
    }

}