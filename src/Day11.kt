fun main() {
    fun part1(input: List<String>): Int {
        var levels = input.map { it.map(Char::digitToInt) }
        return generateSequence {
            levels = step(levels)
            countFlashed(levels)
        }.take(100).sum()
    }

    fun part2(input: List<String>): Int {
        var levels = input.map { it.map(Char::digitToInt) }
        val allFlashing = levels.sumOf { it.count() }
        return generateSequence {
            levels = step(levels)
            countFlashed(levels).takeUnless { it == allFlashing }
        }.count() + 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

fun step(levels: List<List<Int>>): List<List<Int>> {
    var newLevels = levels.map { row -> row.map { it + 1 } }
    var flashed = levels.map { it.map { false } }
    var previousFlashCount: Int
    do {
        val newFlashes = findFlashes(newLevels)
        newLevels = nextLevels(newLevels, flashed)
        previousFlashCount = countFlashes(flashed)
        flashed = mergeFlashes(flashed, newFlashes)
    } while (countFlashes(flashed) != previousFlashCount)
    return newLevels.map { row -> row.map { if (it > 9) 0 else it } }
}

private fun nextLevels(
    levels: List<List<Int>>,
    flashed: List<List<Boolean>>
) = levels.mapIndexed { i, row ->
    row.mapIndexed { j, level ->
        (-1..1).sumOf { dx ->
            (-1..1).count { dy ->
                val x = i + dx
                val y = j + dy
                val adj = levels.getOrNull(x)?.getOrNull(y) ?: 0
                adj > 9 && !flashed[x][y]
            }
        } + level
    }
}

private fun mergeFlashes(
    oldFlashes: List<List<Boolean>>,
    newFlashes: List<List<Boolean>>
) = oldFlashes.zip(newFlashes).map { (old, new) ->
    old.zip(new).map { (a, b) -> a || b }
}


private fun countFlashed(levels: List<List<Int>>) = levels.sumOf { row -> row.count { it == 0 } }
private fun countFlashes(newFlashes: List<List<Boolean>>) = newFlashes.sumOf { row -> row.count { it } }
private fun findFlashes(levels: List<List<Int>>) = levels.map { row -> row.map { it > 9 } }
