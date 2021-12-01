fun countSingleMeasurementIncreases(input: Array<Int>, file: String) {
    var previousValue = input[0]
    var increases = 0

    input.forEach {
        val increase = it - previousValue
        if (increase > 0) {
            increases += 1
        }
        previousValue = it
    }
    println("There are $increases increases in single measurements from file $file")
}

fun countWindowedMeasurementIncreases(input: Array<Int>, windowSize: Int, file: String) {
    val iterations = input.size - windowSize

    var previousSum = input.copyOfRange(0, windowSize).sum()

    var increases = 0

    for (i in 0..iterations) {
        val sum = input.copyOfRange(i, i + windowSize).sum()
        val increase = sum - previousSum
        if (increase > 0) {
            increases += 1
        }
        previousSum = sum
    }
    println("There are $increases increases in $windowSize-windowed sum measurements from file $file")
}


fun main() {
    val pathname = "input/day01.txt"
    val pathnameTest = "input/day01_test.txt"

    val input = readInput(pathname).map { it.toInt() }.toTypedArray()
    val inputTest = readInput(pathnameTest).map { it.toInt() }.toTypedArray()
    println("Size of input: " + input.size)
    println("Size of test input: " + inputTest.size)

    countSingleMeasurementIncreases(input, pathname)
    countSingleMeasurementIncreases(inputTest, pathnameTest)

    countWindowedMeasurementIncreases(input, 3, pathname)
    countWindowedMeasurementIncreases(inputTest, 3, pathnameTest)
}
