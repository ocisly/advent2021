fun main() {
    fun part1(input: List<String>): Int {
        val parsed = input.map { it.map(Char::digitToInt) }
        val gamma = parsed.transposed()
            .map { nums -> nums.groupingBy { it }.eachCount() }
            .map { counts -> counts.maxByOrNull { it.value }!!.key }
        val epsilon = gamma.map { if (it == 0) 1 else 0 }
        val gammaD = gamma.joinToString("").toInt(2)
        val epsilonD = epsilon.joinToString("").toInt(2)
        return gammaD*epsilonD
    }

    fun part2(input: List<String>): Int {
        val parsed = input.map { it.map(Char::digitToInt) }

        val o2 = findRating(parsed) { zeros, ones -> if (zeros <= ones) 1 else 0 }.joinToString("").toInt(2)
        val co2 = findRating(parsed) { zeros, ones -> if (zeros <= ones) 0 else 1 }.joinToString("").toInt(2)

        return o2 * co2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun findRating(parsed: List<List<Int>>, pos: Int = 0, pred: (Int, Int) -> Int): List<Int> {
    if (parsed.size == 1) return parsed.first()

    val needle = parsed
        .transposed()[pos]
        .groupingBy { it }.eachCount()
        .let {
            pred(it.getOrDefault(0,0), it.getOrDefault(1,0))
        }

    return findRating(parsed.filter { it[pos] == needle }, pos + 1, pred)
}
