package com.llc.smartcabinet.data

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.llc.smartcabinet.constants.Constants
import com.llc.smartcabinet.data.service.ClientWebSocketListener
import com.llc.smartcabinet.utils.SPUtil
import com.llc.smartterminal.model.enumtype.Platform
import com.llc.smartterminal.model.service.impl.DataServiceImpl
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *
 * @what
 * @author newler
 * @date 2019/12/14
 *
 */
class DataManager(var platform: Platform) {
    val client  by lazy {
        val httpLoggingInterceptor= HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("okhttp", it.toString())
        }).setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()

        return@lazy client
    }
    val gson by lazy {
        GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(Date::class.java, object:JsonDeserializer<Date> {
                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ): Date {
                    return Date(json?.asJsonPrimitive?.asLong!!)
                }

            })
            .create()

    }
    private val retrofitCreator: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(getInstance().platform.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    val dataService by lazy {
        DataServiceImpl(retrofitCreator, platform)
    }

    companion object {
        private var sDataManager : DataManager ?= null

        @JvmStatic
        fun getInstance():DataManager {
            return sDataManager!!
        }

        fun init(platform: Platform) : DataManager {
            if (sDataManager == null) {
                synchronized(DataManager::class) {
                    if (sDataManager == null) {
                        sDataManager = DataManager(platform)
                    }
                }
            }
            return sDataManager!!
        }
    }
}