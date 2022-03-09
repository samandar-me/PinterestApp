package uz.context.pinterestapp.model

import com.google.gson.annotations.SerializedName

data class Interiors(

    @field:SerializedName("approved_on")
    val approvedOn: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)