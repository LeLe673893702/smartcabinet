package com.llc.smartcabinet.page.face

import com.llc.smartcabinet.App
import com.llc.smartcabinet.R
import com.llc.smartcabinet.base.BasePresenter
import com.llc.smartcabinet.constants.Constants
import com.llc.smartcabinet.data.service.DataService
import com.llc.smartcabinet.utils.SPUtil
import com.llc.smartterminal.utils.RxUtils
import com.llc.smartterminal.utils.ToastUtils
import com.orhanobut.logger.Logger
import com.rgbsdk.yshface.YSHFacePass
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *
 * @what
 * @author newler
 * @date 2019/12/29
 *
 */
class FacePresenter(private val dataService: DataService,
                    private val view:FaceContract.View) :
    BasePresenter, FaceContract.Presenter {
    private val compsiteDisposable by lazy {
        CompositeDisposable()
    }


    override fun start() {
    }


    override fun finishOrder(faceId: String) {
        dataService.finishOrderByFace(SPUtil.getInstance().getString(Constants.TERMINAL_ID), faceId)
            .compose(RxUtils.schedulersTransformer())
            .`as`(view.autoDispose())
            .subscribe({
                view.navToFinishOrderPage()
            } , {
                ToastUtils.instance.showToast(R.string.finish_order_failed)
                it.printStackTrace()
            })

    }

    override fun openDoor(faceId: String) {
        dataService.getUserOrderByFace(SPUtil.getInstance().getString(Constants.TERMINAL_ID), faceId)
            .compose(RxUtils.schedulersTransformer())
            .`as`(view.autoDispose())
            .subscribe({
                view.navToOpenDoor(true)
            }, {
                view.navToOpenDoor(true)
            })
    }

    /**
     * 设置人脸采集参数
     */
    private fun setFaceFilterParams() {
        // 第一个是识别-1，第二个是采集
        YSHFacePass.setQualityFilterLevel(-1, 1)
        App.config.filterMakeTmplIsSame = true
        YSHFacePass.setConfig(App.config)
    }


    override fun onDestroy() {
        Logger.e("onDestroy")
        compsiteDisposable.dispose()
    }

}