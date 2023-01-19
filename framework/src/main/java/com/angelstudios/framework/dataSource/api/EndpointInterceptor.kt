package com.angelstudios.framework.dataSource.api

import android.content.Context
import com.angelstudios.framework.global.extension.isNetworkAvailable
import com.angelstudios.framework.global.utils.NoInternetException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EndpointInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
) : Interceptor {

    @Throws(NoInternetException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (context.isNetworkAvailable()) {
            val requestBuilder = request.newBuilder()

            request = requestBuilder.build()

        } else {
            throw NoInternetException("No network available")
        }

        return chain.proceed(request)


    }
}