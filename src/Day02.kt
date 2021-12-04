sealed interface Instruction {
    data class Forward(val amount: Int) : Instruction
    data class Up(val amount: Int) : Instruction
    data class Down(val amount: Int) : Instruction

    companion object {
        fun parse(input: String): Instruction {
            val (i, a) = input.split(' ')
            val amount = a.toInt()
            return when (i) {
                "forward" -> Forward(amount)
                "down" -> Down(amount)
                "up" -> Up(amount)
                else -> error("invalid instruction")
            }
        }
    }
}

data class Position(val depth: Int, val horizontal: Int)
data class State(val pos: Position, val aim: Int)

fun main() {
    fun part1(input: List<String>): Int {
        val (depth, horizontal) = input.map(Instruction::parse)
            .fold(Position(0, 0)) { pos, instruction ->
                when (instruction) {
                    is Instruction.Forward -> pos.copy(horizontal=pos.horizontal + instruction.amount)
                    is Instruction.Down -> pos.copy(depth=pos.depth + instruction.amount)
                    is Instruction.Up -> pos.copy(depth=pos.depth - instruction.amount)
                }
            }
        return depth * horizontal
    }

    fun part2(input: List<String>): Int {
        val (depth, horizontal) = input.map(Instruction::parse)
            .fold(State(Position(0, 0), 0)) { state, instruction ->
                when (instruction) {
                    is Instruction.Forward -> state.copy(
                        pos = state.pos.copy(
                            depth = state.pos.depth + state.aim * instruction.amount,
                            horizontal = state.pos.horizontal + instruction.amount
                        )
                    )
                    is Instruction.Down -> state.copy(aim = state.aim + instruction.amount)
                    is Instruction.Up -> state.copy(aim = state.aim - instruction.amount)
                }
            }.pos
        return depth * horizontal
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
