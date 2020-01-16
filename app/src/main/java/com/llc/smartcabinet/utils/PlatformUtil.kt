package com.llc.smartterminal.utils

import com.llc.smartcabinet.BuildConfig
import com.llc.smartterminal.model.enumtype.Platform

/**
 * Title:
 * Description:
 * Copyright © 2001-2018 17173. All rights reserved.
 *
 * @author yuruiyin
 * @version 2018/11/21
 */
class PlatformUtil {

    companion object {

        /**
         * 获取平台、考虑到联调环境有切换环境的需求
         */
        fun getPlatform(): Platform {
            val defaultPlatformPos = BuildConfig.env
            return Platform.getPlatform(defaultPlatformPos)
        }
    }

}