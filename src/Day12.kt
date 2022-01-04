fun main() {
    fun part1(input: List<String>): Int {
        val edges = parseEdges(input)
        return findAllPaths(edges, "start", "end", maxSmallCaveRevisits = 0).count()
    }

    fun part2(input: List<String>): Int {
        val edges = parseEdges(input)
        return findAllPaths(edges, "start", "end", maxSmallCaveRevisits = 1).count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 226)
    check(part2(testInput) == 3509)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

private fun parseEdges(input: List<String>) =
    input.map { it.split('-') }.flatMap { (a, b) -> listOf(a to b, b to a) }

private fun findAllPaths(
    edges: List<Edge>,
    start: Cave,
    end: Cave,
    maxSmallCaveRevisits: Int,
    path: Path = emptyList()
): List<Path> {
    val newPath = path.plusElement(start)
    if (start == end) return listOf(newPath)

    return edges.filter { (first) -> first == start }.flatMap { (_, node) ->
        if (shouldExplore(node, newPath, maxSmallCaveRevisits)) findAllPaths(
            edges,
            node,
            end,
            maxSmallCaveRevisits,
            newPath
        )
        else emptyList()
    }
}

private fun shouldExplore(cave: Cave, path: Path, threshold: Int): Boolean {
    val leaves = setOf("start", "end")
    return cave.isLargeCave()
            || !path.contains(cave)
            || (!leaves.contains(cave) && maxSmallCaveCount(path) <= threshold)
}

private fun maxSmallCaveCount(path: Path) = path
    .filter { it.isSmallCave() }
    .groupingBy { it }
    .eachCount()
    .maxOfOrNull { it.value }
    ?: 0

private fun Cave.isSmallCave() = this.getOrNull(0)?.isLowerCase() ?: false
private fun Cave.isLargeCave() = !this.isSmallCave()

typealias Edge = Pair<Cave, Cave>
typealias Path = List<Cave>
typealias Cave = String
