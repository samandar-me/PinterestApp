package uz.context.pinterestapp.modelSearch

import com.google.gson.annotations.SerializedName

data class Result (
    val id: String,
    val description: String? = null,
    val urls: Urls2? = null,
)

