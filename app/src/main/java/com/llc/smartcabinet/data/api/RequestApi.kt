package com.llc.smartterminal.model.api

import com.llc.smartcabinet.data.model.BaseResult
import com.llc.smartcabinet.data.model.Order
import com.llc.smartterminal.model.enumtype.PayWay
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 *
 * @what
 * @author newler
 * @date 2019/12/27
 *
 */
interface RequestApi {

    @GET("mobile/order/cabinet/getUserOrderByOpenCode")
    fun getUserOrderByPassword(
        @Query("terminalId")devId:String,
        @Query("phone")phone:String,
        @Query("openCode")openCode: String
    ) : Observable<BaseResult<Order>>


    @FormUrlEncoded
    @POST("mobile/order/cabinet/completeUserOrderByOpenCode")
    fun finishOrderByPassword(
        @Field("phone")phone: String,
        @Field("openCode") password: String,
        @Field("terminalId")devId:String
    ) : Observable<BaseResult<String>>


    @GET("mobile/order/cabinet/getUserOrder")
    fun getUserOrderByFace(
        @Query("terminalId")devId:String,
        @Query("userId")userId: String
    ) : Observable<BaseResult<Order>>

    @FormUrlEncoded
    @POST("mobile/order/cabinet/completeUserOrder")
    fun finisOrderByFace(
        @Field("userId") userId: String,
        @Field("terminalId")devId:String
    ) : Observable<BaseResult<String>>

}