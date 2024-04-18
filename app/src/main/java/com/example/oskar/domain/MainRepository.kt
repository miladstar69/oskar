package com.example.oskar.domain

import com.example.oskar.data.remote.dto.MainModel
import okhttp3.ResponseBody
import retrofit2.Response

interface MainRepository {
    suspend fun getRegisterRepository(): Response<MainModel>

    suspend fun getLoginRepository(): Response<MainModel>

    suspend fun sendRegisterRepository(
        flowId: String,
        map: Map<String, String>,
    ): Response<ResponseBody>

    suspend fun sendLoginRepository(
        flowId: String,
        map: Map<String, String>,
    ): Response<ResponseBody>
}