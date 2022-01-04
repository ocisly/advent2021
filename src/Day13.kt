fun main() {
    fun part1(input: List<String>): Int {
        val (coordinates, instructions) = parseInput(input)
        return foldPaper(coordinates, instructions.first()).size

    }

    fun part2(input: List<String>): String {
        val (coordinates, instructions) = parseInput(input)
        return instructions.fold(coordinates) { coords, instruction ->
            foldPaper(coords, instruction)
        }.let(::render)
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    check(part2(testInput) == """
    #####
    #...#
    #...#
    #...#
    #####
    """.trimIndent()
    )

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

private fun foldPaper(coordinates: Set<Pair<Int, Int>>, instruction: Pair<String, Int>): Set<Pair<Int, Int>> {
    val (axis, n) = instruction
    return coordinates.map { (x, y) ->
        when {
            axis == "x" && x >= n -> 2 * n - x to y
            axis == "y" && y >= n -> x to 2 * n - y
            else -> x to y
        }
    }.toSet()
}

private fun parseInput(input: List<String>): Pair<Set<Pair<Int, Int>>, List<Pair<String, Int>>> {
    val (folds, coords) = input.filter { it.isNotEmpty() }.partition { it.startsWith("fold") }
    return Pair(
        coords.map { it.split(',').map(String::toInt) }.map { (a, b) -> a to b }.toSet(),
        folds.map { it.substring(11).split('=') }.map { (axis, n) -> axis to n.toInt() }
    )
}

fun render(it: Set<Pair<Int, Int>>): String {
    val minX = 0
    val minY = 0
    val maxX = it.maxOf { it.first }
    val maxY = it.maxOf { it.second }
    return (minY..maxY).joinToString("\n") { y ->
        (minX..maxX).joinToString("") { x ->
            if (it.contains(x to y)) "#" else "."
        }
    }
}
