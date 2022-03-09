package uz.context.pinterestapp.model

import com.google.gson.annotations.SerializedName

data class Sponsorship(

    @field:SerializedName("sponsor")
    val sponsor: Sponsor? = null,

    @field:SerializedName("tagline_url")
    val taglineUrl: String? = null,

    @field:SerializedName("tagline")
    val tagline: String? = null,

    @field:SerializedName("impression_urls")
    val impressionUrls: List<String?>? = null
)
