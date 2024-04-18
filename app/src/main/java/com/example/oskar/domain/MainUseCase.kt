package com.example.oskar.domain

import com.example.oskar.data.remote.dto.MainModel
import okhttp3.ResponseBody
import retrofit2.Response

class MainUseCase(private val mainRepository: MainRepository) {

    suspend fun getRegister(): Response<MainModel> {
        return mainRepository.getRegisterRepository()
    }

    suspend fun getLogin(): Response<MainModel> {
        return mainRepository.getLoginRepository()
    }

    suspend fun sendRegister(
        flowId: String,
        map: Map<String, String>
    ): Response<ResponseBody> {
        return mainRepository.sendRegisterRepository(flowId,map)
    }

    suspend fun sendLogin(
        flowId: String,
        map: Map<String, String>
    ): Response<ResponseBody> {
        return mainRepository.sendLoginRepository(flowId,map)
    }

}