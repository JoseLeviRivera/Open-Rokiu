package com.levi.rokiu.domain.usecase

import com.levi.rokiu.domain.repository.RokuRepository
import javax.inject.Inject

class SendCommandUseCase @Inject constructor(
    private val repository: RokuRepository
) {

    suspend operator fun invoke(
        ip: String,
        command: String
    ) {
        repository.sendCommand(ip, command)
    }
}