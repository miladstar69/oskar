package com.example.oskar.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attributes(
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("value") var value: String? = null,
    @SerializedName("required") var required: Boolean? = null,
    @SerializedName("disabled") var disabled: Boolean? = null,
    @SerializedName("node_type") var nodeType: String? = null
): Parcelable
