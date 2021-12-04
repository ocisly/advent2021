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
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    // check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}


fun <T> List<List<T>>.transposed(): List<List<T>> {
    return this.first().indices.map { index -> this.map { it[index] } }
}
