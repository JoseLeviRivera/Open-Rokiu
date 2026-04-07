package com.levi.rokiu.network

import com.levi.rokiu.domain.model.AppsResponse
import com.levi.rokiu.domain.model.DeviceInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RokuApi {
    @POST("keypress/{command}")
    suspend fun sendKeyPress(
        @Path("command") command: String
    ): Response<Unit>

    @POST("launch/{appId}")
    suspend fun launchApp(
        @Path("appId") appId: String
    ): Response<Unit>

    @GET("query/apps")
    suspend fun getApps(): AppsResponse

    @GET("query/device-info")
    suspend fun getInfo(): DeviceInfo

    @POST("keypress/Lit_{text}")
    suspend fun sendText(@Path("text") text: String)
}