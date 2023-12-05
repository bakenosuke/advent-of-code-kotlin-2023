package day05

import readInput

fun main() {
    val solver = Day05()
    val input = readInput("src/day05/input")
    println("Part 1: ${solver.part1(input)}")
    println("Part 2: ${solver.part2b(input)}")
}

class Day05 {

    fun part1(input: List<String>): Long {
        val seeds = input.first().substringAfter(":")
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.trim().toLong() }
        val seedToSoil = input.getLinesBetween("seed-to-soil map:", "soil-to-fertilizer map:").readRange()
        val soilToFertilizer = input.getLinesBetween("soil-to-fertilizer map:", "fertilizer-to-water map:").readRange()
        val fertilizerToWater = input.getLinesBetween("fertilizer-to-water map:", "water-to-light map:").readRange()
        val waterToLight = input.getLinesBetween("water-to-light map:", "light-to-temperature map:").readRange()
        val lightToTemperature =
            input.getLinesBetween("light-to-temperature map:", "temperature-to-humidity map:").readRange()
        val temperatureToHumidity =
            input.getLinesBetween("temperature-to-humidity map:", "humidity-to-location map:").readRange()
        val humidityToLocation = input.getLinesBetween("humidity-to-location map:", "").readRange()

        val seedLocations = seeds.map {
            val soil = mapValue(it, seedToSoil)
            val fertilizer = mapValue(soil, soilToFertilizer)
            val water = mapValue(fertilizer, fertilizerToWater)
            val light = mapValue(water, waterToLight)
            val temperature = mapValue(light, lightToTemperature)
            val humidity = mapValue(temperature, temperatureToHumidity)
            val location = mapValue(humidity, humidityToLocation)
            location
        }
        return seedLocations.min()
    }

    fun mapValue(value: Long, ranges: List<MappingRange>): Long {
        val matchedRange = ranges.find { value >= it.sourceStart && value <= it.sourceStart + it.range }
        return if (matchedRange == null) {
            value
        } else {
            matchedRange.destStart + (value - matchedRange.sourceStart)
        }
    }

    fun List<String>.getLinesBetween(from: String, to: String): List<String> {
        return if (to == "") {
            subList(indexOf(from) + 1, size - 1)
        } else {
            subList(indexOf(from) + 1, indexOf(to) - 1)
        }
    }

    fun List<String>.readRange(): List<MappingRange> {
        return map {
            val tokens = it.split(" ")
            MappingRange(
                destStart = tokens[0].toLong(),
                sourceStart = tokens[1].toLong(),
                range = tokens[2].toLong()
            )
        }
    }

    data class MappingRange(val destStart: Long, val sourceStart: Long, val range: Long)

    fun part2(input: List<String>): Long {
        val seedToSoil = input.getLinesBetween("seed-to-soil map:", "soil-to-fertilizer map:").readRange()
        val soilToFertilizer = input.getLinesBetween("soil-to-fertilizer map:", "fertilizer-to-water map:").readRange()
        val fertilizerToWater = input.getLinesBetween("fertilizer-to-water map:", "water-to-light map:").readRange()
        val waterToLight = input.getLinesBetween("water-to-light map:", "light-to-temperature map:").readRange()
        val lightToTemperature =
            input.getLinesBetween("light-to-temperature map:", "temperature-to-humidity map:").readRange()
        val temperatureToHumidity =
            input.getLinesBetween("temperature-to-humidity map:", "humidity-to-location map:").readRange()
        val humidityToLocation = input.getLinesBetween("humidity-to-location map:", "").readRange()

        val seedRanges = getSeedRanges(input[0])

        var minLocation = Long.MAX_VALUE
        seedRanges.forEach { range ->
            (0..(range.length - 1)).forEach {
                val soil = mapValue(range.from + it, seedToSoil)
                val fertilizer = mapValue(soil, soilToFertilizer)
                val water = mapValue(fertilizer, fertilizerToWater)
                val light = mapValue(water, waterToLight)
                val temperature = mapValue(light, lightToTemperature)
                val humidity = mapValue(temperature, temperatureToHumidity)
                val location = mapValue(humidity, humidityToLocation)
                if (location < minLocation) {
                    minLocation = location
                }
            }
        }
        return minLocation
    }

    fun part2b(input: List<String>): Long {
        val seedToSoil = input.getLinesBetween("seed-to-soil map:", "soil-to-fertilizer map:").readRangeReversed()
        val soilToFertilizer =
            input.getLinesBetween("soil-to-fertilizer map:", "fertilizer-to-water map:").readRangeReversed()
        val fertilizerToWater =
            input.getLinesBetween("fertilizer-to-water map:", "water-to-light map:").readRangeReversed()
        val waterToLight = input.getLinesBetween("water-to-light map:", "light-to-temperature map:").readRangeReversed()
        val lightToTemperature =
            input.getLinesBetween("light-to-temperature map:", "temperature-to-humidity map:").readRangeReversed()
        val temperatureToHumidity =
            input.getLinesBetween("temperature-to-humidity map:", "humidity-to-location map:").readRangeReversed()
        val humidityToLocation = input.getLinesBetween("humidity-to-location map:", "").readRangeReversed()

        val seedRanges = getSeedRanges(input[0])
        var found = false
        var i = 0L
        while (!found) {
            val location = i
            val humidity = mapValue(location, humidityToLocation)
            val temperature = mapValue(humidity, temperatureToHumidity)
            val light = mapValue(temperature, lightToTemperature)
            val water = mapValue(light, waterToLight)
            val fertilizer = mapValue(water, fertilizerToWater)
            val soil = mapValue(fertilizer, soilToFertilizer)
            val seed = mapValue(soil, seedToSoil)
            if (exists(seed, seedRanges)) {
                found = true
            } else {
                i++
            }
        }

        return i
    }

    fun exists(seed: Long, seedRanges: List<SeedRange>): Boolean {
        return seedRanges.any {
            seed >= it.from && seed <= it.from + it.length
        }
    }

    fun getSeedRanges(input: String): List<SeedRange> {
        val seedRanges = mutableListOf<SeedRange>()
        val numbers = input.substringAfter(":")
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.trim().toLong() }

        (0..(numbers.size / 2 - 1)).forEach {
            seedRanges.add(SeedRange(numbers[it * 2], numbers[it * 2 + 1]))
        }
        return seedRanges
    }

    fun List<String>.readRangeReversed(): List<MappingRange> {
        return map {
            val tokens = it.split(" ")
            MappingRange(
                destStart = tokens[1].toLong(),
                sourceStart = tokens[0].toLong(),
                range = tokens[2].toLong()
            )
        }
    }

    data class SeedRange(val from: Long, val length: Long)
}