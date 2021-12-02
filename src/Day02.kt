fun findMultipliedHorizontalPositionAndDepth(input: List<String>, file: String): Int {
    var horizontalPosition: Int = 0
    var depth: Int = 0

    input.forEach {
        val splitString: List<String> = it.split(" ")
        if (splitString[0] == "forward") {
            horizontalPosition += splitString[1].toInt()
        } else if (splitString[0] == "up") {
            depth -= splitString[1].toInt()
        } else if (splitString[0] == "down") {
            depth += splitString[1].toInt()
        } else {
            println("This should have never happened.")
        }
    }
    val multipliedPosition: Int = horizontalPosition * depth
    println(
        "The final horizontal position is $horizontalPosition and the depth is $depth. " +
                "Multiplied: $multipliedPosition. Used file $file."
    )
    return multipliedPosition
}

fun findMultipliedHorizontalPositionAndDepthWithAim(input: List<String>, file: String): Int {
    var horizontalPosition: Int = 0
    var depth: Int = 0
    var aim: Int = 0

    input.forEach {
        val splitString: List<String> = it.split(" ")
        if (splitString[0] == "forward") {
            horizontalPosition += splitString[1].toInt()
            depth += aim * splitString[1].toInt()
        } else if (splitString[0] == "up") {
            aim -= splitString[1].toInt()
        } else if (splitString[0] == "down") {
            aim += splitString[1].toInt()
        } else {
            println("This should have never happened.")
        }
    }
    val multipliedPosition: Int = horizontalPosition * depth
    println(
        "The final horizontal position is $horizontalPosition and the depth is $depth computed with aim. " +
                "Multiplied: $multipliedPosition. Used file $file."
    )
    return multipliedPosition
}


fun main() {
    val pathname: String = "input/day02.txt"
    val pathnameTest: String = "input/day02_test.txt"

    val input: List<String> = readInput(pathname)
    val inputTest: List<String> = readInput(pathnameTest)
    println("Size of input: " + input.size)
    println("Size of test input: " + inputTest.size)

    check(findMultipliedHorizontalPositionAndDepth(inputTest, pathnameTest) == 150)
    findMultipliedHorizontalPositionAndDepth(input, pathname)

    check(findMultipliedHorizontalPositionAndDepthWithAim(inputTest, pathnameTest) == 900)
    findMultipliedHorizontalPositionAndDepthWithAim(input, pathname)
}