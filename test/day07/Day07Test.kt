package day07

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day07Test {

    private val solver = Day07()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day07/part1")
        val expected = 6440

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example`() {
        val input = readInput("test/day07/part1")
        val expected = 5905

        val result = solver.part2(input)
        result shouldBe expected
    }

}