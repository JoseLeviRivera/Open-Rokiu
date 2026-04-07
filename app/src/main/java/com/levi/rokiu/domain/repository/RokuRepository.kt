package com.levi.rokiu.domain.repository

import com.levi.rokiu.domain.model.App
import com.levi.rokiu.domain.model.DeviceInfo
import com.levi.rokiu.domain.model.RokuDevice

interface RokuRepository {

    suspend fun discoverDevices(): List<RokuDevice>

    suspend fun sendCommand(
        baseURL: String,
        command: String
    )

    suspend fun launchAppId(baseURL: String, appId: String)

    suspend fun getApps(baseURL: String): List<App>

    suspend fun getDeviceInformation(baseURL: String): DeviceInfo

    suspend fun sendText(baseURL: String, text: String)
}