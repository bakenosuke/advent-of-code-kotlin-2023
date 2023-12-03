package day09

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day09Test {

    private val solver = Day09()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day09/part1")
        val expected = 4361

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example`() {
        val input = readInput("test/day09/part1")
        val expected = 467835

        val result = solver.part2(input)
        result shouldBe expected
    }

}