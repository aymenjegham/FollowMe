package com.angelstudios.framework.di.module


import android.content.Context
import com.angelstudios.framework.dataSource.api.EndpointInterceptor
import com.angelstudios.framework.global.constants.CONNECT_TIMEOUT
import com.angelstudios.framework.global.constants.OKHTTP_CACHE_FILE_NAME
import com.angelstudios.framework.global.constants.READ_TIMEOUT
import com.angelstudios.framework.global.constants.WRITE_TIMEOUT
import com.angelstudios.framework.global.helper.DebugLog
import com.angelstudios.framework.di.qualifiers.PicassoHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
        DebugLog.v(
            "follow_me_log",
            message
        )
    }.run {
        level = HttpLoggingInterceptor.Level.BODY
        this
    }

    @Provides
    @Singleton
    fun providesOkHTTPClient(
        loggingInterceptor: HttpLoggingInterceptor,
        endpointInterceptor: EndpointInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(endpointInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesCacheFile(@ApplicationContext context: Context): File =
        File(context.cacheDir, OKHTTP_CACHE_FILE_NAME)


    @Provides
    @Singleton
    fun providesCache(cacheFile: File): Cache = Cache(cacheFile, 10L * 1000 * 1000)//10MB Cache


    @Provides
    @Singleton
    @PicassoHttpClient
    fun providesPicassoOkHTTPClient(
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: EndpointInterceptor,
        cache: Cache
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .cache(cache)
            .build()


}