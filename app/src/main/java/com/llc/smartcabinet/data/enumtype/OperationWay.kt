package com.llc.smartcabinet.data.enumtype

import androidx.annotation.IntDef

/**
 *
 * @what
 * @author newler
 * @date 2020/1/11
 *
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(OperationWay.FINISH_ORDER, OperationWay.OPEN_DOOR)
annotation class OperationWay {
    companion object {
        const val FINISH_ORDER = 0
        const val OPEN_DOOR = 1
    }
}