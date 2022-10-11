package com.android.smartfarm.ui.listener

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class SFWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {

    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

    }

    override fun onMessage(webSocket: WebSocket, text: String) {

    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {

    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {

    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {

    }
}