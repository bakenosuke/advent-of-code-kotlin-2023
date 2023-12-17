package day17

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput

class Day17Test {

    private val solver = Day17()

    @Test
    fun `should match part 1 example`() {
        val input = readInput("test/day17/part1")
        val expected = 107

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example`() {
        val input = readInput("test/day17/part1")
        val expected = 467835

        val result = solver.part2(input)
        result shouldBe expected
    }

}