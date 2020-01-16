package com.llc.smartcabinet.utils

import android.util.Base64

/**
 *
 * @what
 * @author 17173
 * @date 2020/1/16
 *
 */
class Base64Util {
    companion object {
        @JvmStatic
        fun base64ToByteArray(base64String : String) : ByteArray {
            return String(
                Base64.decode(base64String, Base64.DEFAULT),
                Charsets.UTF_8).toByteArray(Charsets.UTF_8)
        }
    }
}