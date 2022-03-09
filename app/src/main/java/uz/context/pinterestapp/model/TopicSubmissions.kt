package uz.context.pinterestapp.model

import com.google.gson.annotations.SerializedName

data class TopicSubmissions(

    @field:SerializedName("people")
    val people: People? = null,

    @field:SerializedName("business-work")
    val businessWork: BusinessWork? = null,

    @field:SerializedName("interiors")
    val interiors: Interiors? = null
)
