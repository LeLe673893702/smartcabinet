package com.llc.smartterminal.utils

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @what
 * @author newler
 * @date 2019/12/13
 *
 */
class RxUtils {
    companion object {
        fun <T> schedulersTransformer(): ObservableTransformer<T, T>? {
            return ObservableTransformer { upstream: Observable<T> ->
                upstream.unsubscribeOn(
                    Schedulers.io()
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }


        fun <T> schedulersNextTransformer(): ObservableTransformer<T, T>? {
            return ObservableTransformer { upstream: Observable<T> ->
                upstream.unsubscribeOn(
                    Schedulers.io()
                ).subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
            }
        }
    }
}