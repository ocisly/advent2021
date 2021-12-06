import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val parsed = parseLines(input)
        val counts = parsed.fold(mutableMapOf<Pair<Int, Int>, Int>()) { acc, (start, end) ->
            val (startX, startY) = start
            val (endX, endY) = end
            if (startX != endX && startY != endY) return@fold acc

            val (lowX, highX) = listOf(startX, endX).sorted()
            val (lowY, highY) = listOf(startY, endY).sorted()

            for (x in lowX..highX) {
                for (y in lowY..highY) {
                    acc.merge(Pair(x, y), 1) { old, value -> old + value }
                }
            }
            acc
        }

        return counts.count { it.value > 1 }
    }

    fun part2(input: List<String>): Int {
        val parsed = parseLines(input)
        val counts = parsed.fold(mutableMapOf<Pair<Int, Int>, Int>()) { acc, (start, end) ->
            val (startX, startY) = start
            val (endX, endY) = end
            val range = { a: Int, b: Int -> if (a < b) a..b else a downTo b }
            for (x in range(startX, endX)) {
                for (y in range(startY, endY)) {
                    if (startX == endX || startY == endY || abs(x - startX) == abs(y - startY)) {
                        acc.merge(Pair(x, y), 1) { old, value -> old + value }
                    }
                }
            }
            acc
        }
        return counts.count { it.value > 1 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun parseLines(input: List<String>) = input
    .map { line ->
        line.split(" -> ")
            .map { it.split(',').map(String::toInt) }
    }
