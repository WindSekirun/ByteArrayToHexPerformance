import oshi.SystemInfo
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.measureNanoTime

// information to benchmark
val count = 1000
val arrayLength = 32

fun main(args: Array<String>) {

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
    println("Starting with count as $count")
    println("Measure in progress...")
    println()

    // setting target
    val bytes = ByteArray(arrayLength)
    SecureRandom.getInstanceStrong().nextBytes(bytes)

    // measure
    val builderFormatTime = measure { BuilderFormat.toHexString(bytes) }
    val stringFormatTime = measure { StringFormat.toHexString(bytes) }
    val charArrayRightShiftTime = measure { CharArrayRightShift.toHexString(bytes) }
    val builderShiftTime = measure { BuilderShift.toHexString(bytes) }
    val charArrayShiftTime = measure { CharArrayShift.toHexString(bytes) }

    // print result
    println("BuilderFormat ${builderFormatTime}ms")
    println("StringFormat ${stringFormatTime}ms")
    println("CharArrayRightShift ${charArrayRightShiftTime}ms")
    println("BuilderShift ${builderShiftTime}ms")
    println("CharArrayShift ${charArrayShiftTime}ms")
    println()
    println("Finished!")
}

private fun measure(block: () -> Unit): String {
    val nano = measureNanoTime {
        for (i in 0 until count) {
            block()
        }
    }

    val ms = nano / 1000000.0
    return String.format("%.3f", ms.toFloat())
}