package uz.context.pinterestapp.networking

import com.example.pinterest.Networking.ResponseItem
import retrofit2.Call
import retrofit2.http.*

interface HomeService {

//    https://api.unsplash.com/search/photos?query=minimal
//    https://api.unsplash.com/photos/random?count=5 █
//      https://api.unsplash.com/users/samuelzeller/photos

//    @GET("photos")
    @GET("photos/random?count=50")
//    @GET("users/samuelzeller/photos")
//    @GET("photos/random")
    fun listPhotos(): Call<ArrayList<ResponseItem>>

    @GET("photos/{id}")
    fun singlePhotos(@Path("id")id:Int):Call<ResponseItem>

    @POST("photos")
    fun createPhotos(@Body post: ResponseItem):Call<ResponseItem>

    @PUT("photos/{id}")
    fun updatePhotos(@Path("id")id: Int,@Body post: ResponseItem):Call<ResponseItem>

    @DELETE("photos/{id}")
    fun deletePhotos(@Path("id")id:Int):Call<ResponseItem>

}