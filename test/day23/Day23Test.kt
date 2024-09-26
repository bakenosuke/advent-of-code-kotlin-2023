package day23

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day23Test {

    private val solver = Day23()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day23/part1")
        val expected = 94

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example`() {
        val input = readInput("test/day23/part1")
        val expected = 154

        val result = solver.part2(input)
        result shouldBe expected
    }

}