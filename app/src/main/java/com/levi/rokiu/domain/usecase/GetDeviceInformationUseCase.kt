package com.levi.rokiu.domain.usecase

import com.levi.rokiu.domain.model.DeviceInfo
import com.levi.rokiu.domain.repository.RokuRepository
import javax.inject.Inject

class GetDeviceInformationUseCase @Inject constructor(
    private val repository: RokuRepository
){
    suspend operator fun invoke(url: String): DeviceInfo{
        return repository.getDeviceInformation(url)
    }
}