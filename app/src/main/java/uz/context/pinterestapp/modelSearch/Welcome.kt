package uz.context.pinterestapp.modelSearch

import com.google.gson.annotations.SerializedName

data class Welcome (
    val total: Long,
    val totalPages: Long,
    val results: List<Result>? = null
)
