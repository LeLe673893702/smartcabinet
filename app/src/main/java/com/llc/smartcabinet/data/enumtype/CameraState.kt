package com.llc.smartterminal.model.enumtype

import androidx.annotation.IntDef

/**
 *
 * @what
 * @author newler
 * @date 2020/1/1
 *
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    CameraState.STATE_PREVIEW,
    CameraState.STATE_WAITING_LOCK,
    CameraState.STATE_WAITING_PRECAPTURE,
    CameraState.STATE_WAITING_NON_PRECAPTURE,
    CameraState.STATE_PICTURE_TAKEN
    )
annotation class CameraState {
    companion object {
        /**
         * 相机预览
         */
       const val STATE_PREVIEW = 0

        /**
         * 相机等待聚焦
         */
        const val STATE_WAITING_LOCK = 1

        /**
         * 相机准备状态.
         */
         const val STATE_WAITING_PRECAPTURE = 2

        /**
         *
         */
        const val STATE_WAITING_NON_PRECAPTURE = 3

        /**
         *
         */
        const val STATE_PICTURE_TAKEN = 4
    }
}