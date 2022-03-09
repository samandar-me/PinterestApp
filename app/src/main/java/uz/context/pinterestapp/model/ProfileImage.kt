package uz.context.pinterestapp.model

import com.google.gson.annotations.SerializedName


data class ProfileImage(

    @field:SerializedName("small")
    val small: String? = null,

    @field:SerializedName("large")
    val large: String? = null,

    @field:SerializedName("medium")
    val medium: String? = null
)