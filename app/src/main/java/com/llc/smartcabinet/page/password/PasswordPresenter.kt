package com.llc.smartcabinet.page.password

import com.llc.smartcabinet.constants.Constants
import com.llc.smartcabinet.data.service.DataService
import com.llc.smartcabinet.utils.SPUtil
import com.llc.smartterminal.utils.RxUtils
import com.llc.smartterminal.utils.ToastUtils

/**
 *
 * @what
 * @author newler
 * @date 2020/1/11
 *
 */
class PasswordPresenter(private val dataService: DataService,
                        private val view: PasswordContract.View) : PasswordContract.Presenter {
    override fun openDoor(phone: String, password: String) {
        dataService
            .getUserOrderByPassword(SPUtil.getInstance().getString(Constants.TERMINAL_ID), phone, password)
            .compose(RxUtils.schedulersTransformer())
            .`as`(view.autoDispose())
            .subscribe({
                view.navToOpenDoor(true)
            }, {
                it.printStackTrace()
                ToastUtils.instance.showToast(it.message)
//                view.navToOpenDoor(false)
            })
    }

    override fun finishOrder(phone: String, password: String) {
        dataService
            .finisOrderByPassword(SPUtil.getInstance().getString(Constants.TERMINAL_ID),
                phone, password)
            .compose(RxUtils.schedulersTransformer())
            .`as`(view.autoDispose())
            .subscribe({
                view.navToFinishOrderPage()
            }, {
                it.printStackTrace()
                ToastUtils.instance.showToast(it.message)
            })
    }

    override fun start() {
    }

    override fun onDestroy() {
    }
}