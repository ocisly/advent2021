fun main() {

    fun part1(input: List<String>): Int {
        var levels = input.map { it.map(Char::digitToInt) }
        return generateSequence {
            val (l, n) = step(levels)
            levels = l
            n
        }.take(100).sum()
    }

    fun part2(input: List<String>): Int {
        var levels = input.map { it.map(Char::digitToInt) }
        return generateSequence {
            val (l, n) = step(levels)
            levels = l
            if (n == l.sumOf { it.count() }) {
                null
            } else {
                n
            }
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


fun countFlashes(flashes: List<List<Boolean>>): Int {
    return flashes.sumOf { row -> row.count { it } }
}


fun step(levels: List<List<Int>>): Pair<List<List<Int>>, Int>{
    var newLevels = levels.map { row -> row.map { it + 1 } }
    var newFlashes = levels.map { it.map { false } }
    var lastSum = 0
    var afterFlashes = newLevels.map { row -> row.map { it > 9 } }
    do {
        newLevels = newLevels.mapIndexed { i, row ->
            row.mapIndexed { j, level ->
                var newLevel = level
                for (dx in -1..1) {
                    for (dy in -1..1) {
                        val x = i + dx
                        val y = j + dy
                        val adj = newLevels.getOrNull(x)?.getOrNull(y)
                        if (adj != null && adj > 9 && !newFlashes[x][y]) {
                            newLevel++
                        }
                    }
                }
                newLevel
            }
        }
        lastSum = countFlashes(newFlashes)
        newFlashes = afterFlashes.zip(newFlashes).map { (a, n) ->
            a.zip(n).map { it.first || it.second }
        }
        afterFlashes = newLevels.map { row -> row.map { it > 9 } }
    } while (newFlashes.sumOf { row -> row.count { it } } != lastSum)
    return Pair(
        newLevels.map { row -> row.map { if (it > 9) 0 else it } },
        countFlashes(newFlashes)
    )
}
