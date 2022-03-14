package uz.context.pinterestapp.networking

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHttp {
    const val SERVER_DEVELOPMENT = "https://api.unsplash.com/"

    private val client = getClient()
    private val retrofit = getRetrofit(client)

    private fun getRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_DEVELOPMENT)
            .client(client)
            .build()
    }

    fun <T> createService(service:Class<T>):T{
        return retrofit.create(service)
    }

    private fun getClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            //  builder.header("Authorization", "Client-ID KR7Tcw-RNnurIM-7JDGj9S-5DUeFhVTx1YNxoR-vRkg")
               builder.header("Authorization", "Client-ID lOwYkRhXb7OgyGquor9WgJsk1uBNU4zhYjtlWfvMFqo")
            chain.proceed(builder.build())
        })
        .build()

    fun <T> createServiceWithAuth(service: Class<T>?): T {

        val newClient =
            client.newBuilder().addInterceptor(Interceptor { chain ->
                var request = chain.request()
                val builder = request.newBuilder()
                //  builder.addHeader("Authorization", "Client-ID KR7Tcw-RNnurIM-7JDGj9S-5DUeFhVTx1YNxoR-vRkg")
                request = builder.build()
                chain.proceed(request)
            }).build()
        val newRetrofit = retrofit.newBuilder().client(newClient).build()
        return newRetrofit.create(service!!)
    }

    val posterService: HomeService = retrofit.create(HomeService::class.java)
}