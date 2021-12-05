class Board(private val dimension: Int) {
    private var board: Array<Array<Int>> = arrayOf<Array<Int>>()
    private var hits: Array<Array<Int>> = arrayOf<Array<Int>>()
    private var hasWon: Boolean = false
    private var lastNumber: Int = 0
    private var drawings: Int = 0

    init {
        for (i: Int in 0 until this.dimension) {
            var numberValues = arrayOf<Int>()
            var numberHits = arrayOf<Int>()
            for (j: Int in 0 until this.dimension) {
                numberValues += 0
                numberHits += 0
            }
            this.board += numberValues
            this.hits += numberHits
        }
    }

    fun setValue(x: Int, y: Int, value: Int) {
        this.board[x][y] = value
    }

    private fun setHit(x: Int, y: Int) {
        this.hits[x][y] = 1
    }

    fun checkHit(value: Int): Boolean {
        var hit: Boolean = false
        this.lastNumber = value
        this.drawings++
        for (i: Int in 0 until this.dimension) {
            for (j: Int in 0 until this.dimension) {
                if (this.board[i][j] == value) {
                    this.setHit(i, j)
                    hit = true
                    break
                }
            }
            if (hit) break
        }
        return hit
    }

    fun checkWin(): Boolean {
        for (i: Int in 0 until this.dimension) {
            val rowCount: Int = this.getHitRowCount(i)
            val columnCount: Int = this.getHitColumnCount(i)
            if ((rowCount == this.dimension) or (columnCount == this.dimension)) {
                this.hasWon = true
                return this.hasWon
            }
        }
        return false
    }

    private fun getHitRowCount(row: Int): Int {
        return this.hits[row].sum()
    }

    private fun getHitColumnCount(column: Int): Int {
        var count: Int = 0
        for (i in 0 until this.dimension) {
            count += this.hits[i][column]
        }
        return count
    }

    fun getSumOfAllMarkedValues(value: Int): Int {
        var sum: Int = 0
        for (i: Int in 0 until this.dimension) {
            for (j: Int in 0 until this.dimension) {
                if (this.hits[i][j] == value) {
                    sum += this.board[i][j]
                }
            }
        }
        return sum
    }

    fun hasWon(): Boolean {
        return this.hasWon
    }

    fun getScore(): Int {
        return this.lastNumber * this.getSumOfAllMarkedValues(0)
    }

    fun getLastNumber(): Int {
        return this.lastNumber
    }

    fun getNumberOfDrawings(): Int {
        return this.drawings
    }

}


fun playBingo(filename: String): List<Int> {
    val input: List<String> = readInput(filename)
    println("Size of input in $filename: " + input.size)

    val drawnNumbers: Array<Int> = input[0].split(',').map { it.toInt() }.toTypedArray()

    val boards = mutableListOf<Board>()
    var board = Board(0)

    var boardIndex = 0
    for (i: Int in 2 until input.size) {
        if (input[i].isNotEmpty()) {
            val rowValues: List<Int> = input[i].trim().split("\\s+".toRegex()).map { it.toInt() }
            if (boardIndex == 0) {
                board = Board(rowValues.size)
            }
            for (j: Int in rowValues.indices) {
                board.setValue(boardIndex, j, rowValues[j])
            }
            boardIndex++
        } else {
            boardIndex = 0
            boards += board
        }
    }
    boards += board

    var winningBoard: Int = 0
    val boardWinningOrder: MutableList<Int> = mutableListOf<Int>().toMutableList()
    for ((i, number: Int) in drawnNumbers.withIndex()) {
        for ((j, _board) in boards.withIndex()) {
            if (!_board.hasWon()) {
                _board.checkHit(number)
                if (_board.checkWin()) {
                    if (winningBoard < 1) {
                        winningBoard = j
                        println(
                            "$filename: " +
                                    "Board ${j + 1} won the game with number $number after ${i + 1} drawings. " +
                                    "Sum of all unmarked values: ${_board.getSumOfAllMarkedValues(0)}. " +
                                    "Score: ${_board.getScore()}."
                        )
                    }
                    boardWinningOrder += j
                }
            }
        }
    }
    if (boardWinningOrder.size == boards.size) {
        val lastWinningBoard: Board = boards[boardWinningOrder.last()]
        println(
            "$filename: " +
                    "Board ${boardWinningOrder.last() + 1} won the game last with number " +
                    "${lastWinningBoard.getLastNumber()} after ${lastWinningBoard.getNumberOfDrawings()} drawings. " +
                    "Sum of all unmarked values: ${lastWinningBoard.getSumOfAllMarkedValues(0)}. " +
                    "Score: ${lastWinningBoard.getScore()}."
        )
    }
    return listOf<Int>(boards[boardWinningOrder.first()].getScore(), boards[boardWinningOrder.last()].getScore())
}

fun part1(filename: String): Int {
    return 1
}

fun main() {
    val pathname: String = "input/day04.txt"
    val pathnameTest: String = "input/day04_test.txt"

    val (scoreOfWinningBoardTest: Int, scoreOfLastWinningBoardTest: Int) = playBingo(pathnameTest)
    val (scoreOfWinningBoard: Int, scoreOfLastWinningBoard: Int) = playBingo(pathname)

    check(scoreOfWinningBoardTest == 4512)
    check(scoreOfLastWinningBoardTest == 1924)
}