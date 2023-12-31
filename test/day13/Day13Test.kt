package day13

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day13Test {

    private val solver = Day13()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day13/part1")
        val expected = 405

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example`() {
        val input = readInput("test/day13/part1")
        val expected = 400

        val result = solver.part2(input)
        result shouldBe expected
    }

}