fun main() {
    fun part1(input: List<String>): Int {
        val parsed = input.map { it.map(Char::digitToInt) }
        return parsed.flatMapIndexed { i, row ->
            val padded = listOf(10) + row + listOf(10)
            padded.windowed(3).mapIndexedNotNull { j, (left, mid, right) ->
                val top = parsed.getOrNull(i - 1)?.getOrNull(j) ?: 10
                val bottom = parsed.getOrNull(i + 1)?.getOrNull(j) ?: 10
                if (mid < left && mid < right && mid < top && mid < bottom) mid + 1 else null
            }
        }.sum()
    }

    fun mergeBasins(basins: List<Set<Pair<Int, Int>>>): List<Set<Pair<Int, Int>>> {
        val basin = basins.first()
        val (matching, others) = basins.drop(1).partition { it.any { pair -> basin.intersect(neighbors(pair)).isNotEmpty() } }
        return others.plusElement(matching.fold(basin) { a, b -> a.union(b) })
    }

    fun part2(input: List<String>): Int {
        val parsed = input.map { it.map(Char::digitToInt) }

        val basins = parsed.flatMapIndexed { i, row ->
            row.mapIndexedNotNull { j, depth ->
                if (depth != 9) setOf(Pair(i, j)) else null
            }
        }

        var n = -1
        var merged = basins
        var t = basins.size
        while (t > 0) {
            if (n == merged.size) {
                t--
            }
            n = merged.size
            merged = mergeBasins(merged)
        }
        return merged.map { it.size }.sortedDescending().take(3).reduce(Int::times)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    // 1028976 too low
    println(part2(input))
}


fun neighbors(pair: Pair<Int, Int>): Set<Pair<Int, Int>> {
    val (x, y) = pair
    return listOf(
        Pair(x + 1, y),
        Pair(x - 1, y),
        Pair(x, y + 1),
        Pair(x, y - 1),
    ).toSet()
}
