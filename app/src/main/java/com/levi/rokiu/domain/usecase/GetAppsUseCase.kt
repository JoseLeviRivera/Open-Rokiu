package com.levi.rokiu.domain.usecase

import com.levi.rokiu.domain.model.App
import com.levi.rokiu.domain.repository.RokuRepository
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val repository: RokuRepository
){
    suspend operator fun invoke(url: String): List<App> {
        return repository.getApps(url)
    }
}