package com.levi.rokiu.domain.usecase

import com.levi.rokiu.domain.repository.RokuRepository
import javax.inject.Inject

class LaunchAppIdUseCase @Inject constructor(
    private val repository: RokuRepository
){
    suspend operator fun invoke(url: String, appId: String){
        repository.launchAppId(url, appId)
    }
}