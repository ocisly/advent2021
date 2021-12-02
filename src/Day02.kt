fun main() {
    fun part1(input: List<String>): Int {
        val (x, y) = input.map { it.split(' ') }
            .fold(listOf(0, 0)) { (oldA, oldB), (instruction, a) ->
                val amount = a.toInt()
                when (instruction) {
                    "forward" -> listOf(oldA, oldB + amount)
                    "down" -> listOf(oldA + amount, oldB)
                    "up" -> listOf(oldA - amount, oldB)
                    else -> listOf(oldA, oldB)
                }
            }
        return x * y
    }

    fun part2(input: List<String>): Int {
        val (depth, pos, _aim) = input.map { it.split(' ') }
            .fold(listOf(0, 0, 0)) { (oldDepth, oldPos, oldAim), (instruction, a) ->
                val amount = a.toInt()
                when (instruction) {
                    "forward" -> listOf(oldDepth + oldAim * amount, oldPos + amount, oldAim)
                    "down" -> listOf(oldDepth, oldPos, oldAim + amount)
                    "up" -> listOf(oldDepth, oldPos, oldAim - amount)
                    else -> listOf(oldDepth, oldPos, oldAim)
                }
            }
        return pos * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
