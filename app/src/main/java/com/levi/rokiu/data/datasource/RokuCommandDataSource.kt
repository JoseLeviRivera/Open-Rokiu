package com.levi.rokiu.data.datasource

import com.levi.rokiu.di.JsonRetrofit
import com.levi.rokiu.network.RokuApi
import retrofit2.Retrofit
import javax.inject.Inject

class RokuCommandDataSource @Inject constructor(
    @JsonRetrofit private val retrofitBuilder: Retrofit.Builder
) {

    suspend fun sendCommand(baseURL: String, command: String) {
        val api = createApi(baseURL)
        api.sendKeyPress(command)
    }

    suspend fun launchAppId(baseURL: String, appId: String) {
        val api = createApi(baseURL)
        api.launchApp(appId)
    }

    suspend fun sendText(baseURL: String, text: String) {
        val api = createApi(baseURL)
        text.forEach { char ->
            api.sendText(char.toString())
        }
    }

    private fun createApi(baseURL: String): RokuApi {
        return retrofitBuilder
            .baseUrl(baseURL)
            .build()
            .create(RokuApi::class.java)
    }
}