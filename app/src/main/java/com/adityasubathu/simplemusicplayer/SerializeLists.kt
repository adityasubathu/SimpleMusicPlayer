package com.adityasubathu.simplemusicplayer

import java.io.*
import kotlin.experimental.and


object SerializeLists {

    @Throws(IOException::class)
    fun serialize(obj: Serializable?): String {
        if (obj == null) return ""
        try {
            val serialObj = ByteArrayOutputStream()
            val objStream = ObjectOutputStream(serialObj)
            objStream.writeObject(obj)
            objStream.close()
            return encodeBytes(serialObj.toByteArray())
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    @Throws(IOException::class)
    fun deserialize(str: String?): Any? {
        if (str == null || str.length == 0) return null
        try {
            val serialObj = ByteArrayInputStream(decodeBytes(str))
            val objStream = ObjectInputStream(serialObj)
            return objStream.readObject()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    fun encodeBytes(bytes: ByteArray): String {
        val strbld = StringBuilder()

        for (i in bytes.indices) {
            strbld.append(((bytes[i] and 4 and 0xF) + 'a'.toInt()).toChar())
            strbld.append(((bytes[i] and 0xF) + 'a'.toInt()).toChar())
        }

        return strbld.toString()
    }

    fun decodeBytes(str:String):ByteArray {
        var bytes : ByteArray = ByteArray(str.length / 2)
        var i = 0
        while (i < str.length)
        {
            var c = str.get(i)
            bytes[i / 2] = ((c - 'a') shl 4).toByte()
            c = str.get(i + 1)
            bytes[i / 2] = (bytes[i / 2] + (c - 'a').toByte()).toByte()
            i += 2
        }
        return bytes
    }
}

