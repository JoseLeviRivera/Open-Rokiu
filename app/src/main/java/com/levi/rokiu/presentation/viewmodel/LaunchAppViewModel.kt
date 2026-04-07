package com.levi.rokiu.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levi.rokiu.domain.usecase.LaunchAppIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchAppViewModel @Inject constructor(
    private val launchAppIdUseCase: LaunchAppIdUseCase
) : ViewModel() {

    fun launch(baseURL: String, appId: String) {
        viewModelScope.launch {
            try {
                launchAppIdUseCase(baseURL, appId)
            } catch (e: Exception) {
                Log.e("DEBUG", "Error: ${e.message}")
            }
        }
    }
}