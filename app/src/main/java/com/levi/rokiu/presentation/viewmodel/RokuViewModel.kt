package com.levi.rokiu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levi.rokiu.domain.usecase.SendCommandUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RokuViewModel @Inject constructor(
    private val commandUseCase: SendCommandUseCase
) : ViewModel() {
    fun sendCommand(baseURL: String, command: String) {
        viewModelScope.launch {
            commandUseCase(baseURL, command)
        }
    }
}