import oshi.SystemInfo
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)

    println("Please enter the number you want to execute.")
    val count = input.nextInt()

    println("Please enter the length of bytes to be generated.")
    val arrayLength = input.nextInt()

    println()
    println("==== ByteArray to Hex String Benchmark ====")
    // get Hardware information using oshi
    val systemInfo = SystemInfo()
    val operatingSystem = systemInfo.operatingSystem
    val hardwareAbstractionLayer = systemInfo.hardware
    val processor = hardwareAbstractionLayer.processor
    val totalMemory = hardwareAbstractionLayer.memory.total
    val osName = operatingSystem.toString()

    println("OS: $osName")
    println("Processors: ${processor.name}")
    println("Total Memory: ${totalMemory / 1024 / 1024 / 1024}GB")
    println("Time: ${SimpleDateFormat("yyyy-MM-dd a hh:mm:ss").format(Date().time)}")

    println()
    println("Starting with count as $count with length of ByteArray as $arrayLength")
    println("Measure in progress...")
    println()

    // setting target
    val bytes = ByteArray(arrayLength)
    SecureRandom.getInstanceStrong().nextBytes(bytes)

    // measure
    val builderFormatTime = measure(count) { BuilderFormat.toHexString(bytes) }
    val stringFormatTime = measure(count) { StringFormat.toHexString(bytes) }
    val charArrayRightShiftTime = measure(count) { CharArrayRightShift.toHexString(bytes) }
    val builderShiftTime = measure(count) { BuilderShift.toHexString(bytes) }
    val charArrayShiftTime = measure(count) { CharArrayShift.toHexString(bytes) }
    val totalTime = builderFormatTime + stringFormatTime + charArrayRightShiftTime + builderShiftTime + charArrayShiftTime


    // print result
    println("BuilderFormat ${String.format("%.3f", builderFormatTime.toFloat())}ms")
    println("StringFormat ${String.format("%.3f", stringFormatTime.toFloat())}ms")
    println("CharArrayRightShift ${String.format("%.3f", charArrayRightShiftTime.toFloat())}ms")
    println("BuilderShift ${String.format("%.3f", builderShiftTime.toFloat())}ms")
    println("CharArrayShift ${String.format("%.3f", charArrayShiftTime.toFloat())}ms")
    println()
    println("Finished! in ${String.format("%.3f", totalTime.toFloat())}ms")
}

private fun measure(count: Int, block: () -> Unit): Double {
    val nano = measureNanoTime {
        for (i in 0 until count) {
            block()
        }
    }

    return nano / 1000000.0
}