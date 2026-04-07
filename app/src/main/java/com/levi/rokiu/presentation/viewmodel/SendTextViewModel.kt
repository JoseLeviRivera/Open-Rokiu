package com.levi.rokiu.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levi.rokiu.domain.usecase.SendTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendTextViewModel @Inject constructor(
    private val sendTextUseCase: SendTextUseCase
) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun sendText(baseURL: String, text: String) {
        if (text.isEmpty()) {
            _error.value = "El texto no puede estar vacío"
            return
        }

        viewModelScope.launch {
            try {
                sendTextUseCase(baseURL, text)
                _status.value = true
            } catch (e: Exception) {
                _error.value = e.message
                _status.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}