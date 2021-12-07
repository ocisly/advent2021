import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val parsed = input.first().split(',').map(String::toInt)
        val median = parsed.sorted().run { this[size / 2] }
        return parsed.sumOf { (median - it).absoluteValue }
    }

    fun part2(input: List<String>): Int {
        val parsed = input.first().split(',').map(String::toInt)
        val min = parsed.minOf { it }
        val max = parsed.maxOf { it }
        return (min..max).minOf { i ->
            parsed.sumOf { crab ->
                val distance = (i - crab).absoluteValue
                (distance * (distance + 1)) / 2
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
