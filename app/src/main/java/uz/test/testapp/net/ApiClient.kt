package uz.test.testapp.net


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.test.testapp.BuildConfig
import java.io.IOException

object ApiClient {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val client = buildClient()
    private val retrofit: Retrofit = buildRetrofit(client)
    private var apiService: ApiService? = null
    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }

    private fun buildClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val builder = OkHttpClient.Builder()
            .addNetworkInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    val builder = request.newBuilder()
                    builder.header("Accept", "application/json")
                    request = builder.build()
                    return chain.proceed(request)
                }
            })
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    fun client(): ApiService {
        if (apiService == null) {
            apiService = buildRetrofit(buildClient()).create(ApiService::class.java)
        }
        return apiService!!
    }
}