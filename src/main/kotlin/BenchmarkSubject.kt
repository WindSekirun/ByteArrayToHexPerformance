object BuilderFormat {
    fun toHexString(byteArray: ByteArray): String {
        val sbx = StringBuilder()
        for (i in byteArray.indices) {
            sbx.append(String.format("%02X", byteArray[i]))
        }
        return sbx.toString()
    }
}

object StringFormat {
    fun toHexString(byteArray: ByteArray): String {
        var target = ""
        for (i in byteArray.indices) {
            target += String.format("%02X", byteArray[i])
        }
        return target
    }
}

object CharArrayRightShift {
    private val digits = "0123456789ABCDEF"

    fun toHexString(byteArray: ByteArray): String {
        val hexChars = CharArray(byteArray.size * 2)
        for (i in byteArray.indices) {
            val v = byteArray[i].toInt() and 0xFF
            hexChars[i * 2] = digits[v.ushr(4)]
            hexChars[i * 2 + 1] = digits[v and 0x0F]
        }

        return String(hexChars)
    }
}

object BuilderShift {
    private val digits = "0123456789ABCDEF"

    fun toHexString(byteArray: ByteArray): String {
        val buf = StringBuilder(byteArray.size * 2)
        for (i in byteArray.indices) {
            val v = byteArray[i].toInt() and 0xff
            buf.append(digits[v shr 4])
            buf.append(digits[v and 0xf])
        }
        return buf.toString()
    }
}

object CharArrayShift {
    private val digits = "0123456789ABCDEF"

    fun toHexString(byteArray: ByteArray): String {
        val hexChars = CharArray(byteArray.size * 2)
        for (i in byteArray.indices) {
            val v = byteArray[i].toInt() and 0xff
            hexChars[i * 2] = digits[v shr 4]
            hexChars[i * 2 + 1] = digits[v and 0xf]
        }
        return String(hexChars)
    }
}