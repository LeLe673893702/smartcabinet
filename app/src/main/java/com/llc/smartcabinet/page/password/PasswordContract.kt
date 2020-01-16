package com.llc.smartcabinet.page.password

import com.llc.smartcabinet.base.BasePresenter
import com.llc.smartcabinet.base.BaseView
/**
 *
 * @what
 * @author newler
 * @date 2019/12/29
 *
 */
interface PasswordContract {
    interface View : BaseView {
        fun navToFinishOrderPage()
        fun navToOpenDoor(succeed: Boolean)
    }

    interface Presenter: BasePresenter {
        fun openDoor(phone: String, password: String)
        fun finishOrder(phone: String, password: String)
    }
}