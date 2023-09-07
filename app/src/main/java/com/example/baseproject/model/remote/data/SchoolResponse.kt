package com.example.baseproject.model.remote.data

import com.google.gson.annotations.SerializedName

/**
 * Data class for school API response
 */
data class SchoolResponse(
    @SerializedName("dbn")
    val id: String,
    @SerializedName("school_name")
    val name: String,
    @SerializedName("location")
    val location: String? = "",
    @SerializedName("phone_number")
    val phoneNumber: String? = "",
    @SerializedName("school_email")
    val email: String? = ""
)