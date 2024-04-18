package com.example.oskar.data.repositoryImpl

import com.example.oskar.data.remote.ApiService
import com.example.oskar.data.remote.dto.MainModel
import com.example.oskar.domain.MainRepository
import okhttp3.ResponseBody
import retrofit2.Response

class MainRepositoryImpl(private val apiService: ApiService):MainRepository {
    override suspend fun getRegisterRepository(): Response<MainModel> {
        return apiService.getRegister()
    }

    override suspend fun getLoginRepository(): Response<MainModel> {
        return apiService.getLogin()
    }

    override suspend fun sendRegisterRepository(
        flowId: String,
        map: Map<String, String>
    ): Response<ResponseBody> {
        return apiService.sendRegister(flowId,map)
    }

    override suspend fun sendLoginRepository(
        flowId: String,
        map: Map<String, String>
    ): Response<ResponseBody> {
        return apiService.sendLogin(flowId,map)
    }
}