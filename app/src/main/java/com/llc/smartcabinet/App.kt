package com.llc.smartcabinet

import android.app.Application
import android.content.Context
import com.llc.smartcabinet.constants.Constants
import com.llc.smartcabinet.data.DataManager
import com.llc.smartcabinet.utils.MacUtil
import com.llc.smartcabinet.utils.SPUtil
import com.llc.smartterminal.utils.PlatformUtil
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.rgbsdk.yshface.YSHFaceInstance
import com.rgbsdk.yshface.YSHFacePass
import com.rgbsdk.yshface.core.YSHConfig

/**
 *
 * @what
 * @author newler
 * @date 2019/12/13
 *
 */
class App : Application() {
    companion object {
        private lateinit var sApplication: Application
        val config: YSHConfig by lazy {
            YSHFacePass.getConfig()
        }

        @JvmStatic
        fun getContext(): Context {
            return sApplication.baseContext
        }
    }
    override fun onCreate() {
        super.onCreate()
        sApplication = this
        initLogger()
        initDataManager()
        initFace()
        initMAC()
    }

    private fun initLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter(){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private fun initDataManager() {
        DataManager.init(PlatformUtil.getPlatform())
    }

    private fun initMAC() {
        val mac = MacUtil.getMacFromHardware()
        Logger.e(mac)
        SPUtil.getInstance().put(Constants.TERMINAL_ID, mac)
    }

    private fun initFace() {
        /*facesdk 第1步
        * 人脸识别sdk鉴权、初始化
        * @param maxCamNums 计划支持多路摄像头，比如1个板子支持1进或1出，填1，支持1进1出，
        * 填2;目前只支持1进或1出
        * @param maxFaceNums 最多可检测视频帧的人脸数
        * @return
        * */
        val nRet: Int = YSHFaceInstance.auth(
            getContext(),
            "rgbsdk_test_1",
            "E10ADC3949BA59ABBE56E057F20F883E"
        )
        if (nRet == 1) {
            Logger.i("人脸算法授权成功")
            YSHFacePass.setConfig(config)
            YSHFaceInstance.init(getContext(),
                2, 5, 2, 0,
                0,  false
            ) {
                if(it == 1) {
                    Logger.i("人脸算法初始化成功")
                } else {
                    Logger.e("人脸算法初始化失败")
                }
            }

            YSHFaceInstance.setDebug(true)

        } else {
            Logger.e("人脸算法授权失败 Error Code = $nRet")
        }
    }

}