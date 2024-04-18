package com.example.oskar

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

class Utils {
    companion object{
        var coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.localizedMessage?.let { Log.d("TAG", it) }
        }
    }
}