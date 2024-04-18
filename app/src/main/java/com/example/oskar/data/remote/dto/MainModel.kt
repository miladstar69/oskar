package com.example.oskar.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainModel(
    @SerializedName("id") var id: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("expires_at") var expiresAt: String? = null,
    @SerializedName("issued_at") var issuedAt: String? = null,
    @SerializedName("request_url") var requestUrl: String? = null,
    @SerializedName("ui") var ui: Ui? = Ui(),
    @SerializedName("organization_id") var organizationId: String? = null,
    @SerializedName("state") var state: String? = null
):Parcelable
