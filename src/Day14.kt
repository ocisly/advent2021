fun main() {
    fun expand(template: String, rules: Map<String, String>, n: Int): String {
        val result = (1..n).fold(template) { template, _ ->
            template.zipWithNext()
                .mapIndexed { i, (a, b) -> "${if (i == 0) a else ""}${rules.getValue("$a$b")}$b" }
                .joinToString("")
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val template = input.first()
        val rules = input.drop(2).map { it.split(" -> ") }.associate { (a, b) -> a to b }
        val result = expand(template, rules, 10)
        val counts = result.groupingBy { it }.eachCount()
        val max = counts.maxOf { it.value }
        val min = counts.minOf { it.value }
        return max - min
    }

    fun part2(input: List<String>): Int {
        throw NotImplementedError("not implemented yet")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
    // check(part2(testInput).toLong() == 2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    // println(part2(input))
}
