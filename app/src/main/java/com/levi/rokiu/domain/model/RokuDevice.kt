package com.levi.rokiu.domain.model

data class RokuDevice(
    val ip: String,
    val location: String?,
    val server: String?,
    val usn: String?,
    val wakeupMac: String?
)