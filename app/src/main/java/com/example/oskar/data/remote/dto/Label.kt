package com.example.oskar.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Label(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("text") var text: String? = null,
    @SerializedName("type") var type: String? = null
): Parcelable
