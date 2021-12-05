import java.io.File
import kotlin.math.ceil

fun computePowerConsumption(filename: String): Int {
    val input: List<Int> = readInput(filename).map { it.toInt(2) }
    val bitwidth: Int = File(filename).bufferedReader().use { it.readLine() }.length
    println("Size of input in $filename: " + input.size)

    var gammaRate: Int = 0
    for (i in 0 until bitwidth) {
        val sumOfBits: Int = input.sumOf { (it and (1 shl i)) shr i }
        if (sumOfBits > input.size / 2) {
            gammaRate += 1 shl i
        }
    }
    val epsilonRate: Int = gammaRate.inv() and ((1 shl bitwidth) - 1)
    val powerConsumption: Int = gammaRate * epsilonRate

    println("Power consumption for log $filename is $powerConsumption.")
    return powerConsumption
}

fun computeLifeSupportRating(filename: String): Int {
    val input: List<Int> = readInput(filename).map { it.toInt(2) }
    val bitwidth: Int = File(filename).bufferedReader().use { it.readLine() }.length
    println("Size of input in $filename: " + input.size)

    var oxygenGeneratorValues: List<Int> = input
    for (i in bitwidth - 1 downTo 0) {
        val sumOfBits: Int = oxygenGeneratorValues.sumOf { (it and (1 shl i)) shr i }
        val bitmask: Int = (1 shl (i + 1)) - 1
        oxygenGeneratorValues = if (sumOfBits >= ceil(oxygenGeneratorValues.size.toFloat() / 2.0)) {
            if (i > 1) {
                oxygenGeneratorValues.filter { it and bitmask >= (1 shl i) }
            } else {
                oxygenGeneratorValues.filter { it and bitmask > (1 shl i) }
            }
        } else {
            oxygenGeneratorValues.filter { it and bitmask < (1 shl i) }
        }
        if (oxygenGeneratorValues.size == 1) {
            break
        }
    }

    var co2ScrubberRatingValues: List<Int> = input
    for (i in bitwidth - 1 downTo 0) {
        val sumOfBits: Int = co2ScrubberRatingValues.sumOf { (it and (1 shl i)) shr i }
        val bitmask: Int = (1 shl (i + 1)) - 1
        co2ScrubberRatingValues = if (sumOfBits < ceil(co2ScrubberRatingValues.size.toFloat() / 2.0)) {
            if (i > 1) {
                co2ScrubberRatingValues.filter { it and bitmask >= (1 shl i) }
            } else {
                co2ScrubberRatingValues.filter { it and bitmask > (1 shl i) }
            }
        } else {
            co2ScrubberRatingValues.filter { it and bitmask < (1 shl i) }
        }
        if (co2ScrubberRatingValues.size == 1) {
            break
        }
    }

    val lifeSupportRating: Int = oxygenGeneratorValues[0] * co2ScrubberRatingValues[0]
    println(
        "Computed values for file $filename:\n" +
        "Oxygen Generator Value: ${oxygenGeneratorValues[0]}\n" +
        "CO2 Scrubber Rating Value: ${co2ScrubberRatingValues[0]}\n" +
        "Life Support Rating: $lifeSupportRating"
    )
    return lifeSupportRating
}


fun main() {
    val pathname: String = "input/day03.txt"
    val pathnameTest: String = "input/day03_test.txt"

    check(computePowerConsumption(pathnameTest) == 198)
    computePowerConsumption(pathname)
    check(computeLifeSupportRating(pathnameTest) == 230)
    computeLifeSupportRating(pathname)
}