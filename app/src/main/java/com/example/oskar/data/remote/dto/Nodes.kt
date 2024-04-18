package com.example.oskar.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nodes(

    @SerializedName("type") var type: String? = null,
    @SerializedName("group") var group: String? = null,
    @SerializedName("attributes") var attributes: Attributes? = Attributes(),
    @SerializedName("messages") var messages: ArrayList<String> = arrayListOf(),
    @SerializedName("meta") var meta: Meta? = Meta()

): Parcelable
