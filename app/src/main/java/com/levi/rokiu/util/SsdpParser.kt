package com.levi.rokiu.util

object SsdpParser {
    fun parseHeaders(response: String): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        response.lines()
            .drop(1)
            .forEach { line ->
                val index = line.indexOf(":")
                if (index > 0) {
                    val key = line.substring(0, index).trim().uppercase()
                    val value = line.substring(index + 1).trim()
                    headers[key] = value
                }
            }
        return headers
    }

    fun extractWakeupMac(wakeup: String?): String? {
        wakeup ?: return null
        val parts = wakeup.split(";")
        for (part in parts) {
            if (part.startsWith("MAC=", true)) {
                return part.substringAfter("MAC=")
            }
        }
        return null
    }
}