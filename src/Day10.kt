fun main() {
    val pairs = mapOf(
        '(' to ')',
        '[' to ']',
        '<' to '>',
        '{' to '}',
    )

    fun part1(input: List<String>): Int {
        val score = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137,
        )
        return input.mapNotNull { line ->
            val stack = ArrayDeque<Char>()
            for (char in line) {
                if (pairs.containsKey(char)) {
                    stack.addFirst(char)
                } else {
                    val last = stack.removeFirst()
                    if (char != pairs[last]) {
                        return@mapNotNull score[char]
                    }
                }
            }
            null
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val score = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4,
        )
        return input.mapNotNull { line ->
            val stack = ArrayDeque<Char>()
            line.forEach { char ->
                if (pairs.containsKey(char)) {
                    stack.addFirst(char)
                } else {
                    val last = stack.removeFirst()
                    if (char != pairs[last]) {
                        return@mapNotNull null
                    }
                }
            }
            stack
        }.map {
            it.map(pairs::getValue)
                .takeWhile(score::contains)
                .fold(0L) { total, char -> total * 5 + score.getValue(char) }
        }.sorted().run { this[size / 2] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    // 1028976 too low
    println(part2(input))
}
