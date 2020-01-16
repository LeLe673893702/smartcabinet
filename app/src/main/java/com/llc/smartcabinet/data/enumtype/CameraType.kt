package com.llc.smartterminal.model.enumtype

import androidx.annotation.IntDef

/**
 *
 * @what
 * @author newler
 * @date 2020/1/4
 *
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(CameraType.RGB, CameraType.IR)
annotation class CameraType {
    companion object {
        const val RGB = 0
        const val IR = 1
    }
}