package uz.context.pinterestapp.model

import com.google.gson.annotations.SerializedName

data class Urls(

    @field:SerializedName("small")
    val small: String? = null,

    @field:SerializedName("small_s3")
    val smallS3: String? = null,

    @field:SerializedName("thumb")
    val thumb: String? = null,

    @field:SerializedName("raw")
    val raw: String? = null,

    @field:SerializedName("regular")
    val regular: String? = null,

    @field:SerializedName("full")
    val full: String? = null
)
