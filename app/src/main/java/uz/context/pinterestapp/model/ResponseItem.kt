package uz.context.pinterestapp.model

import com.google.gson.annotations.SerializedName

data class ResponseItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("liked_by_user")
	val likedByUser: Boolean? = null,


	@field:SerializedName("urls")
	val urls: Urls? = null,

	@field:SerializedName("links")
	val links: Links? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("categories")
	val categories: List<Any?>? = null,

)

