package com.levi.rokiu.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levi.rokiu.domain.model.DeviceInfo
import com.levi.rokiu.domain.usecase.GetDeviceInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetDeviceInfoViewModel @Inject constructor(
    private val useCase: GetDeviceInformationUseCase
) : ViewModel() {

    private val _deviceInfo = MutableLiveData<DeviceInfo>()
    val deviceInfo: LiveData<DeviceInfo> = _deviceInfo

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getDeviceInfo(baseURL: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val result = useCase(baseURL)
                _deviceInfo.value = result

            } catch (e: Exception) {
                Log.e("DEBUG", "Error getting device info: ${e.message}")
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}