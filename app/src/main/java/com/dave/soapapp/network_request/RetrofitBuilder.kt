package com.dave.soapapp.network_request


import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {

    fun getRetrofit(BASE_URL:String): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .addInterceptor(BasicAuthInterceptor())
            .addNetworkInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().addHeader("Connection", "close").build())
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)

            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    class BasicAuthInterceptor(): Interceptor {
        private var credentials: String = Credentials.basic("MOBILE", "Mobile@1")

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request = request.newBuilder().header("Authorization", credentials).build()

            return chain.proceed(request)
        }
    }

}