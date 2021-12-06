fun main() {
    fun part1(input: List<String>): Long {
        return fishCount(input, 80)
    }

    fun part2(input: List<String>): Long {
        return fishCount(input, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

fun fishCount(input: List<String>, days: Int): Long {
    val parsed = input.first().split(",").map(String::toInt)
    val fish = LongArray(9) { i -> parsed.count { it == i }.toLong() }
    repeat(days) {
        fish.withIndex().reversed().forEach { (i, f) ->
            if (i == 0) {
                fish[6] += f
                fish[8] = f
            } else {
                fish[i - 1] = f
            }
        }
    }
    return fish.sum()
}
