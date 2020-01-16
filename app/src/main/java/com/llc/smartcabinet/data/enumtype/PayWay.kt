package com.llc.smartterminal.model.enumtype

import androidx.annotation.IntDef

/**
 *
 * @what
 * @author liule
 * @date 2020/1/3
 *
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(PayWay.ALI, PayWay.WEI_XIN)
annotation class PayWay {
    companion object {
        const val ALI = 1
        const val WEI_XIN = 2
    }
}