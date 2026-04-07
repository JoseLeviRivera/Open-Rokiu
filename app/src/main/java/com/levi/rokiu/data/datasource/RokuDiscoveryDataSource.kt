package com.levi.rokiu.data.datasource

import com.levi.rokiu.domain.model.RokuDevice
import com.levi.rokiu.util.SsdpParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException
import javax.inject.Inject


class RokuDiscoveryDataSource @Inject constructor() {

    suspend fun discover(): List<RokuDevice> = withContext(Dispatchers.IO) {
        val devices = mutableMapOf<String, RokuDevice>()
        val message = """
            M-SEARCH * HTTP/1.1
            HOST: 239.255.255.250:1900
            MAN: "ssdp:discover"
            ST: roku:ecp
            MX: 3
            
        """.trimIndent()
        val socket = DatagramSocket()
        val address = InetAddress.getByName("239.255.255.250")
        val packet = DatagramPacket(
            message.toByteArray(),
            message.length,
            address,
            1900
        )
        socket.send(packet)
        val buffer = ByteArray(2048)
        val responsePacket = DatagramPacket(buffer, buffer.size)
        socket.soTimeout = 3000
        try {
            while (true) {
                socket.receive(responsePacket)
                val response = String(
                    responsePacket.data,
                    0,
                    responsePacket.length
                )
                val ip = responsePacket.address.hostAddress
                val headers = SsdpParser.parseHeaders(response)
                val location = headers["LOCATION"]
                val server = headers["SERVER"]
                val usn = headers["USN"]
                val wakeupMac = SsdpParser.extractWakeupMac(headers["WAKEUP"])
                ip?.let {
                    if (!devices.containsKey(it)) {
                        devices[it] = RokuDevice(
                            ip = it,
                            location = location,
                            server = server,
                            usn = usn,
                            wakeupMac = wakeupMac
                        )
                    }
                }
            }

        } catch (_: SocketTimeoutException) {
        }
        socket.close()
        devices.values.toList()
    }
}