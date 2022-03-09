package uz.context.pinterestapp.networking

import uz.context.pinterestapp.model.ResponseItem
import retrofit2.Call
import retrofit2.http.*

interface HomeService {

//    https://api.unsplash.com/search/photos?query=minimal
//    https://api.unsplash.com/photos/random?count=5 █
//      https://api.unsplash.com/users/samuelzeller/photos


    //fragment1 all
    @GET("photos/random?count=1000")
//    @GET("photos?page=1&query=shape")
    fun listPhotos1(): Call<ArrayList<ResponseItem>>

    //fragment2 all
    @GET("topics/wallpapers/photos")
    fun listPhotos2(): Call<ArrayList<ResponseItem>>

    //fragment6 all
    @GET("topics/animals/photos")
    fun listPhotos6(): Call<ArrayList<ResponseItem>>

    @GET("photos/{id}")
    fun singlePhotos(@Path("id")id:Int):Call<ResponseItem>

    @POST("photos")
    fun createPhotos(@Body post: ResponseItem):Call<ResponseItem>

    @PUT("photos/{id}")
    fun updatePhotos(@Path("id")id: Int,@Body post: ResponseItem):Call<ResponseItem>

    @DELETE("photos/{id}")
    fun deletePhotos(@Path("id")id:Int):Call<ResponseItem>

}