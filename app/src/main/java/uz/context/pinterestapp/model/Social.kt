package uz.context.pinterestapp.model

import com.google.gson.annotations.SerializedName

data class Social(

    @field:SerializedName("twitter_username")
    val twitterUsername: String? = null,

    @field:SerializedName("paypal_email")
    val paypalEmail: Any? = null,

    @field:SerializedName("instagram_username")
    val instagramUsername: String? = null,

    @field:SerializedName("portfolio_url")
    val portfolioUrl: String? = null
)
