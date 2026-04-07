package com.levi.rokiu.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levi.rokiu.data.model.RokuDeviceUi
import com.levi.rokiu.domain.usecase.DiscoverRokuUseCase
import com.levi.rokiu.domain.usecase.GetDeviceInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val discoverRokuUseCase: DiscoverRokuUseCase,
    private val getDeviceInformationUseCase: GetDeviceInformationUseCase
) : ViewModel() {

    private val _devices = MutableLiveData<List<RokuDeviceUi>>()
    val devices: LiveData<List<RokuDeviceUi>> = _devices

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun discover() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val discoveredDevices = discoverRokuUseCase()
                Log.d("DEBUG", "Discovered ${discoveredDevices.size} devices")
                val devicesWithInfo = discoveredDevices.map { device ->
                    async {
                        try {
                            val deviceInfo = getDeviceInformationUseCase(device.location.toString())
                            RokuDeviceUi(
                                name = deviceInfo.vendorName ?: deviceInfo.friendlyDeviceName ?: device.server,
                                ip = deviceInfo.modelName ?: deviceInfo.modelName ?: device.ip,
                                location = device.location.toString()
                            )
                        } catch (e: Exception) {
                            Log.e("DEBUG", "Error getting info for ${device.ip}: ${e.message}")
                            RokuDeviceUi(
                                name = device.server,
                                ip = device.ip,
                                location = device.location.toString()
                            )
                        }
                    }
                }.awaitAll()
                _devices.postValue(devicesWithInfo)
            } catch (e: Exception) {
                Log.e("DEBUG", "Discovery error: ${e.message}")
                _devices.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}