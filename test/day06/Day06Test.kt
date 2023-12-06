package day06

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import readInput
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.TimeZone

class Day06Test {

    private val solver = Day06()

    @Test
    fun `should match part 1 example` () {
        val input = readInput("test/day06/part1")
        val expected = 288

        val result = solver.part1(input)
        result shouldBe expected
    }

    @Test
    fun `should match part 2 example` () {
        val input = readInput("test/day06/part1")
        val expected = 71503

        val result = solver.part2(input)
        result shouldBe expected
    }

}