fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            val (_, output) = it.split(" | ")
            output.split(" ")
        }.sumOf { output ->
            output.map { chars -> chars.groupBy { it }.count() }.count { setOf(2, 3, 4, 7).contains(it) }
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    println(part2(testInput))

    val input = readInput("Day08")
    println(part1(input))
    // println(part2(input))
}
