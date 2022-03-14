package uz.context.pinterestapp.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.context.pinterestapp.model.ResponseItem
import uz.context.pinterestapp.model.User
import uz.context.pinterestapp.modelSearch.Result
import uz.context.pinterestapp.modelSearch.Welcome
import uz.context.pinterestapp.modelSearchFrag.CollectionsModelItem

interface HomeService {

    //fragment1 all
    @GET("photos/random?count=1000")
    fun listPhotos1(): Call<ArrayList<ResponseItem>>

    @GET("search/photos?page=1&per_page=19&query=")
    fun searchPhotos(@Query("page") page: Int, @Query("query") search: String): Call<Welcome>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<User>

    @GET("photos/{id}")
    fun getImagesCategories(@Path("id") id: String): Call<Result>

    @GET("collections/")
    fun getCollections(): Call<List<CollectionsModelItem>>
}



//    @GET("photos/{id}")
//    fun singlePhotos(@Path("id")id:Int):Call<ResponseItem>
//
//    @POST("photos")
//    fun createPhotos(@Body post: ResponseItem):Call<ResponseItem>
//
//    @PUT("photos/{id}")
//    fun updatePhotos(@Path("id")id: Int,@Body post: ResponseItem):Call<ResponseItem>
//
//    @DELETE("photos/{id}")
//    fun deletePhotos(@Path("id")id:Int):Call<ResponseItem>

    //    @GET("search/photos?")
////    @GET("search/photos?page=2&query=tourism")
//    fun searchPhotos(@Query("query") search: String) : Call<Welcome>
////    fun searchPhotos() : Call<Welcome>