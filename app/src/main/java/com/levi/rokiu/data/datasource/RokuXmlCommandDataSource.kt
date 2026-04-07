package com.levi.rokiu.data.datasource

import com.levi.rokiu.di.XmlRetrofit
import com.levi.rokiu.domain.model.App
import com.levi.rokiu.domain.model.DeviceInfo
import com.levi.rokiu.network.RokuApi
import retrofit2.Retrofit
import javax.inject.Inject

class RokuXmlCommandDataSource @Inject constructor(
    @XmlRetrofit private val retrofitBuilder: Retrofit.Builder
) {

    suspend fun getApps(baseURL: String): List<App> {
        val api = createApi(baseURL)
        return api.getApps().apps ?: emptyList()
    }

    suspend fun getDeviceInformation(baseURL: String): DeviceInfo {
        val api = createApi(baseURL)
        return api.getInfo()
    }

    private fun createApi(baseURL: String): RokuApi {
        return retrofitBuilder
            .baseUrl(baseURL)
            .build()
            .create(RokuApi::class.java)
    }
}