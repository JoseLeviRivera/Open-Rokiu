package com.levi.rokiu.domain.usecase


import com.levi.rokiu.domain.repository.RokuRepository
import javax.inject.Inject

class SendTextUseCase @Inject constructor(
    private val repository: RokuRepository
) {
    suspend operator fun invoke(baseURL: String, text: String) {
        repository.sendText(baseURL, text)
    }
}