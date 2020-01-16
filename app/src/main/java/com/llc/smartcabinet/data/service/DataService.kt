package com.llc.smartcabinet.data.service

import com.llc.smartcabinet.data.model.Order
import io.reactivex.Observable


/**
 *
 * @what
 * @author newler
 * @date 2019/12/15
 *
 */
interface DataService {
    fun getUserOrderByPassword(devId:String, phone: String, password: String) : Observable<Order>
    fun getUserOrderByFace(devId:String, faceId: String) : Observable<Order>

    fun finishOrderByFace(devId: String, faceId: String) : Observable<String>
    fun finisOrderByPassword(devId: String, phone: String, password:String): Observable<String>
}