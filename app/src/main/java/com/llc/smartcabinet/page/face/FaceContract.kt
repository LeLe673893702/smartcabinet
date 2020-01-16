package com.llc.smartcabinet.page.face

import com.llc.smartcabinet.base.BasePresenter
import com.llc.smartcabinet.base.BaseView
/**
 *
 * @what
 * @author newler
 * @date 2019/12/29
 *
 */
interface FaceContract {
    interface View : BaseView {
        fun navToFinishOrderPage()
        fun navToOpenDoor(succeed: Boolean)
    }

    interface Presenter: BasePresenter {
        fun finishOrder(faceId:String)
        fun openDoor(faceId: String)
    }
}