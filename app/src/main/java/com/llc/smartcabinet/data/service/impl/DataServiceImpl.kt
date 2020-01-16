package com.llc.smartterminal.model.service.impl

import com.llc.smartcabinet.data.model.BaseResult
import com.llc.smartcabinet.data.model.Order
import com.llc.smartcabinet.data.service.DataService
import com.llc.smartterminal.model.api.RequestApi
import com.llc.smartterminal.model.enumtype.Platform
import io.reactivex.Observable
import retrofit2.Retrofit

/**
 *
 * @what
 * @author newler
 * @date 2019/12/27
 *
 */
class DataServiceImpl(retrofitCreator:Retrofit, platform: Platform) : DataService {
    private val requestApi: RequestApi =
        retrofitCreator.create(RequestApi::class.java)

    private fun <T> convert(baseResult: BaseResult<T>): Observable<T> {
        return if (baseResult.code == 1) {
            if (baseResult.data != null) {
                Observable.just(baseResult.data)
            } else {
                Observable.just("" as T)
            }
        } else {
            Observable.error(Throwable(baseResult.msg))
        }
    }

    override fun getUserOrderByPassword(devId: String, phone: String, password: String): Observable<Order> {
        return Observable.defer {
            requestApi.getUserOrderByPassword(devId, phone, password)
        }.flatMap {convert(it) }
    }

    override fun finisOrderByPassword(
        devId: String,
        phone: String,
        password: String
    ): Observable<String> {
        return Observable.defer {
            requestApi.finishOrderByPassword(phone, password, devId)
        }.flatMap { convert(it) }
    }

    override fun getUserOrderByFace(devId: String, faceId: String): Observable<Order> {
        return Observable.defer {
            requestApi.getUserOrderByFace(devId, faceId)
        }.flatMap {convert(it) }
    }

    override fun finishOrderByFace(devId: String, faceId: String): Observable<String> {
        return Observable.defer {
            requestApi.finisOrderByFace(faceId, devId)
        }.flatMap { convert(it) }
    }

}