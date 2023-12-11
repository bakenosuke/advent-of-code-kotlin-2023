package day11

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day11Test {

    private val solver = Day11()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day11/part1")
        val expected = 374

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2a example`() {
        val input = readInput("test/day11/part1")
        val expected = 1030

        val result = solver.part2(input, 10)
        result shouldBe expected
    }

    @Test
    fun `should match part 2b example`() {
        val input = readInput("test/day11/part1")
        val expected = 8410

        val result = solver.part2(input, 100)
        result shouldBe expected
    }

}