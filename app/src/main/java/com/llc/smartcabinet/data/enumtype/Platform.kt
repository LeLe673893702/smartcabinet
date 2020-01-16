package com.llc.smartterminal.model.enumtype

import androidx.annotation.IntDef
import androidx.annotation.StringDef
import java.text.ParsePosition

/**
 *
 * @what 两个测试
 * @author newler
 * @date 2019/12/13
 *
 */

enum class Platform(baseUrl: String) {
    DEV("http://118.31.103.94:8082"),
    PROD("");
    var baseUrl: String = ""

    init {
       this.baseUrl = baseUrl;
    }

    companion object {

        fun getPlatform(position: Int):Platform {
            return when(position) {
                0 -> DEV
                else -> PROD
            }
        }

        fun getPositionByPlatform(platform: Platform) {
            when(platform) {
                DEV -> 0
                else -> 1
            }
        }
    }
}