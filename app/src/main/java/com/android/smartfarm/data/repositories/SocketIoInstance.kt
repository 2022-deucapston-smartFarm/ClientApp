package com.android.smartfarm.data.repositories

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketIoInstance {//서버 연결 클래스
    val ioOption :IO.Options by lazy { IO.Options().apply {
    path = "/socket.io"
    transports = arrayOf("websocket")
    } }

    @Singleton
    @Provides
    fun providesSocketInstance(): Socket {
        return IO.socket("http://ec2-35-78-118-67.ap-northeast-1.compute.amazonaws.com:3000", ioOption)
    }
}
