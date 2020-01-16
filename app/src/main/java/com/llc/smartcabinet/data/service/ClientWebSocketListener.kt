package com.llc.smartcabinet.data.service

import android.util.Base64
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.llc.smartcabinet.App
import com.llc.smartcabinet.constants.Constants
import com.llc.smartcabinet.data.DataManager
import com.llc.smartcabinet.data.enumtype.WebSokcetAction
import com.llc.smartcabinet.data.model.Face
import com.llc.smartcabinet.data.model.WebSocketData
import com.llc.smartcabinet.utils.Base64Util
import com.llc.smartcabinet.utils.DataUtil
import com.orhanobut.logger.Logger
import com.rgbsdk.yshface.YSHFacePass
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.net.URLDecoder
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *
 * @what
 * @author newler
 * @date 2020/1/12
 *
 */
class ClientWebSocketListener : WebSocketListener() {
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val gson: Gson by lazy {
        DataManager.getInstance().gson
    }

    private val db: AppDatabase by lazy {
        AppDatabase.getInstance()
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Logger.d("打开连接")
        sendPone(webSocket)
       val disposable =  Observable.interval(10, TimeUnit.SECONDS)
            .subscribe ({
                sendPone(webSocket)
            }, {
                Logger.e(it.message?:"")
            })
        compositeDisposable.add(disposable)
    }

    private fun sendPone(webSocket: WebSocket) {
        val webSocketData = WebSocketData(WebSokcetAction.PONE, "")
        webSocket.send(DataManager.getInstance().gson.toJson(webSocketData))
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        t.printStackTrace()
        Logger.e("连接失败:${URLDecoder.decode(webSocket.request().url().toString(), "utf-8")}-${response?.body()}")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        compositeDisposable.clear()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Logger.e(text)
        val webSocketData = gson.fromJson<WebSocketData>(text, WebSocketData::class.java)
        when(webSocketData.action) {
            WebSokcetAction.FACE -> handleFace(webSocketData.json)
        }
    }

    private fun handleFace(json: String) {
        val face = gson.fromJson<List<Face>>(json, object : TypeToken<List<Face>>() {}.type)
        face.forEach {face->
            val disposable =  Observable.create<Face> {
                val group = DataUtil.convert2String(Date(), "yyyyMMdd")
                val feature = Base64Util.base64ToByteArray(face.feature)
                YSHFacePass.delFace(face.faceId, group)
                YSHFacePass.addFace(face.faceId, group,feature)
                it.onNext(face)
            }
                .subscribeOn(Schedulers.io())
                .flatMap {
                    db.userDao().deleteFace(it)
                    db.userDao().insertAll(it)
                    return@flatMap Observable.just(it)
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        compositeDisposable.clear()
    }
}