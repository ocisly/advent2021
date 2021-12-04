fun main() {
    fun parseNumbers(input: List<String>) = input.first().split(',').map { it.toInt() }

    fun part1(input: List<String>): Int {
        val numbers = parseNumbers(input)
        val boards = parseBoards(input.drop(1))
        val (n, winner) = findWinningBoard(boards, numbers, first = true)
        return winner.sumOf { it.sum() } * n
    }

    fun part2(input: List<String>): Int {
        val numbers = parseNumbers(input)
        val boards = parseBoards(input.drop(1))
        val (n, winner) = findWinningBoard(boards, numbers)
        return winner.sumOf { it.sum() } * n
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun findWinningBoard(boards: List<Board>, nums: List<Int>, first: Boolean = false): Pair<Int, Board> {
    var boards = boards
    var transposedBoards = boards.map { it.transposed() }
    for (n in nums) {
        val markN = mark(n)
        boards = boards.map(markN)
        transposedBoards = transposedBoards.map(markN)
        val winningBoards = boards.map(::checkWinner).takeIf { it.contains(true) }
            ?: transposedBoards.map(::checkWinner).takeIf { it.contains(true) }
        winningBoards?.let { winners ->
            val winner = boards[winners.indexOf(true)]
            boards = boards.filterIndexed { i, _ -> !winners[i] }
            transposedBoards = transposedBoards.filterIndexed { i, _ -> !winners[i] }
            if (first || boards.isEmpty()) {
                return Pair(n, winner)
            }
        }
    }
    error("no winning board")
}

fun checkWinner(board: Board) = board.any { row -> row.isEmpty() }

typealias Board = List<List<Int>>

fun mark(n: Int) = { board: Board ->
    board.map { row -> row.filterNot { it == n } }
}

fun parseBoards(input: List<String>): List<Board> = input
    .filter(String::isNotEmpty)
    .chunked(5) { board ->
        board.map { it.split(spaces).filter(String::isNotEmpty).map(String::toInt) }
    }

val spaces = Regex("""\s+""")
