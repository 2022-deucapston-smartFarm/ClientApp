package com.android.smartfarm.data.repositories

import com.android.smartfarm.ui.listener.SFWebSocketListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {
    @Inject lateinit var listener: SFWebSocketListener
    private val baseUrl=""

    @Provides
    @Singleton
    fun providesOkHttpInstance(request:Request):OkHttpClient{
        return OkHttpClient.Builder().build().apply {
            newWebSocket(request,listener)
        }
    }

    @Provides
    @Singleton
    fun providesRequestInstance():Request{
        return Request.Builder().url(baseUrl).build()
    }
}