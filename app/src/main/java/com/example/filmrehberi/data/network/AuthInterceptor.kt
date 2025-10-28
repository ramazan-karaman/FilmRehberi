package com.example.filmrehberi.data.network

import okhttp3.Interceptor
import okhttp3.Response
import com.example.filmrehberi.BuildConfig

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response{

        val originalRequest= chain.request()

        val originalHttpUrl= originalRequest.url

        val url= originalHttpUrl.newBuilder()
            .addQueryParameter("apikey",BuildConfig.OMDB_API_KEY)
            .build()

        val requestBuilder= originalRequest.newBuilder()
            .url(url)

        val request= requestBuilder.build()

        return chain.proceed(request)
    }
}