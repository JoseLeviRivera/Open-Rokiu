package com.levi.rokiu.domain.usecase

import com.levi.rokiu.domain.model.RokuDevice
import com.levi.rokiu.domain.repository.RokuRepository
import javax.inject.Inject

class DiscoverRokuUseCase @Inject constructor(
    private val repository: RokuRepository
) {

    suspend operator fun invoke(): List<RokuDevice> {
        return repository.discoverDevices()
    }
}