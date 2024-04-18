package com.example.oskar.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ui(
    @SerializedName("action") var action: String? = null,
    @SerializedName("method") var method: String? = null,
    @SerializedName("nodes") var nodes: ArrayList<Nodes> = arrayListOf()
): Parcelable
