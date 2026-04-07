package com.levi.rokiu.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levi.rokiu.domain.model.Channel
import com.levi.rokiu.domain.model.toChannel
import com.levi.rokiu.domain.usecase.GetAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAppsViewModel @Inject constructor(
    private val useCase: GetAppsUseCase
) : ViewModel() {

    private val _channels = MutableLiveData<List<Channel>>()
    val channels: LiveData<List<Channel>> = _channels

    fun start(baseURL: String) {
        viewModelScope.launch {
            try {
                val result = useCase(baseURL)
                Log.e("DEBUG", result.toString())
                val channels = result
                    .filter { it.type == "appl" }
                    .map { it.toChannel() }
                _channels.value = channels
            } catch (e: Exception) {
                Log.e("DEBUG", "Error: ${e.message}")
                _channels.value = emptyList()
            }
        }
    }
}