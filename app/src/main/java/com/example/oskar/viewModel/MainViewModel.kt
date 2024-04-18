package com.example.oskar.viewModel

import android.text.InputType
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oskar.Utils
import com.example.oskar.data.remote.ApiService
import com.example.oskar.data.remote.dto.MainModel
import com.example.oskar.data.repositoryImpl.MainRepositoryImpl
import com.example.oskar.databinding.ButtonLayoutBinding
import com.example.oskar.databinding.HaveAccountBinding
import com.example.oskar.databinding.InputLayoutBinding
import com.example.oskar.databinding.NotHaveAccountBinding
import com.example.oskar.domain.MainRepository
import com.example.oskar.domain.MainUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class MainViewModel : ViewModel(), MainRepository {

    private val mainUseCase = MainUseCase(this)

    val mapRegisterJson: ArrayMap<String, String> = ArrayMap()
    val mapLoginJson: ArrayMap<String, String> = ArrayMap()
    var holdRegisterRef: HashMap<String, AppCompatEditText> = hashMapOf()
    var holdLoginRef: HashMap<String, AppCompatEditText> = hashMapOf()

    var registerSuccess = MutableLiveData<Boolean?>(null)
    var loginSuccess = MutableLiveData<Boolean?>(null)


    var registerFlowId: String? = null
    var loginFlowId: String? = null


    var registerModel = MutableLiveData<MainModel?>(null)
    var loginModel = MutableLiveData<MainModel?>(null)

    fun getRegister() {
        CoroutineScope(Dispatchers.IO + Utils.coroutineExceptionHandler).launch {
            val response = ApiService.getInstance2().getRegister()
            if (response.isSuccessful) {
                registerModel.postValue(response.body())
            } else {
                launch(Dispatchers.Main) {
                    Log.d("error", "getRegister: " + response.errorBody())
                }
            }
        }
    }

    fun doRegister() {
        CoroutineScope(Dispatchers.IO + Utils.coroutineExceptionHandler).launch {
            val response = mainUseCase.sendRegister(
                registerFlowId!!,
                mapRegisterJson
            )
            if (response.isSuccessful) {
                registerSuccess.postValue(true)
            } else {
                registerSuccess.postValue(false)
                launch(Dispatchers.Main) {
                    Log.d("error", "doRegister: " + response.errorBody())
                }
            }
        }
    }

    fun doLogin() {
        CoroutineScope(Dispatchers.IO + Utils.coroutineExceptionHandler).launch {
            val response = mainUseCase.sendLogin(
                loginFlowId!!,
                mapLoginJson
            )
            if (response.isSuccessful) {
                loginSuccess.postValue(true)
            } else {
                loginSuccess.postValue(false)
                launch(Dispatchers.Main) {
                    Log.d("error", "doLogin: " + response.errorBody())
                }
            }
        }
    }


    fun getLogin() {
        CoroutineScope(Dispatchers.IO + Utils.coroutineExceptionHandler).launch {
            val response = ApiService.getInstance2().getLogin()
            if (response.isSuccessful) {
                loginModel.postValue(response.body())
            } else {
                launch(Dispatchers.Main) {
                    Log.d("error", "getLogin: " + response.errorBody())
                }
            }
        }
    }

    override suspend fun getRegisterRepository(): Response<MainModel> {
        return MainRepositoryImpl(ApiService.getInstance2()).getRegisterRepository()
    }


    override suspend fun getLoginRepository(): Response<MainModel> {
        return MainRepositoryImpl(ApiService.getInstance2()).getLoginRepository()
    }

    override suspend fun sendRegisterRepository(
        flowId: String,
        map: Map<String, String>
    ): Response<ResponseBody> {
        return MainRepositoryImpl(ApiService.getInstance3()).sendRegisterRepository(flowId, map)
    }

    override suspend fun sendLoginRepository(
        flowId: String,
        map: Map<String, String>
    ): Response<ResponseBody> {
        return MainRepositoryImpl(ApiService.getInstance3()).sendLoginRepository(flowId, map)
    }
}