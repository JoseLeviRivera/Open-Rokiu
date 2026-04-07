package com.levi.rokiu.data.repository

import com.levi.rokiu.data.datasource.RokuCommandDataSource
import com.levi.rokiu.data.datasource.RokuDiscoveryDataSource
import com.levi.rokiu.data.datasource.RokuXmlCommandDataSource
import com.levi.rokiu.domain.model.App
import com.levi.rokiu.domain.model.DeviceInfo
import com.levi.rokiu.domain.model.RokuDevice
import com.levi.rokiu.domain.repository.RokuRepository
import javax.inject.Inject

class RokuRepositoryImpl @Inject constructor(
    private val discovery: RokuDiscoveryDataSource,
    private val commandDataSource: RokuCommandDataSource,
    private val commandXMLDateSource: RokuXmlCommandDataSource
) : RokuRepository {

    override suspend fun discoverDevices(): List<RokuDevice> {
        return discovery.discover()
    }

    override suspend fun sendCommand(baseURL: String, command: String) {
        commandDataSource.sendCommand(baseURL, command)
    }

    override suspend fun launchAppId(baseURL: String, appId: String) {
        commandDataSource.launchAppId(baseURL, appId)
    }

    override suspend fun getApps(baseURL: String): List<App> {
        return commandXMLDateSource.getApps(baseURL)
    }

    override suspend fun getDeviceInformation(baseURL: String): DeviceInfo {
        return commandXMLDateSource.getDeviceInformation(baseURL)
    }

    override suspend fun sendText(baseURL: String, text: String) {
        commandDataSource.sendText(baseURL, text)
    }

}