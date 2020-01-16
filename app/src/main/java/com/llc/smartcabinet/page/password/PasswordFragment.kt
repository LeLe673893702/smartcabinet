package com.llc.smartcabinet.page.password

import android.content.Intent
import android.os.Bundle

import com.llc.smartcabinet.R
import com.llc.smartcabinet.base.BaseFragment
import com.llc.smartcabinet.constants.Constants
import com.llc.smartcabinet.data.DataManager
import com.llc.smartcabinet.data.enumtype.OperationWay
import com.llc.smartcabinet.page.opendoor.OpenDoorSucceedActivity
import com.llc.smartcabinet.page.opendoor.OpenDorrFailedActivity
import com.llc.smartcabinet.page.order.FinishOrderSuccedActivity
import kotlinx.android.synthetic.main.fragment_password.*

class PasswordFragment : BaseFragment<PasswordContract.Presenter>(), PasswordContract.View {
    @OperationWay
    private val operationWay : Int by lazy {
        arguments?.getInt(Constants.OPERATION_TYPE) ?: 0
    }

    companion object {
        @JvmStatic
        fun newInstance(@OperationWay operationWay: Int) : PasswordFragment {
            val passwordFragment = PasswordFragment()
            passwordFragment.arguments = Bundle().apply {
                putInt(Constants.OPERATION_TYPE, operationWay)
            }
            return passwordFragment
        }

    }

    override fun initView() {

    }

    override fun registerEvent() {
        btSure.setOnClickListener {
            if (operationWay == OperationWay.OPEN_DOOR) {
                mPresenter?.openDoor(etPhone.text.toString(), etPassword.text.toString())
            } else {
                mPresenter?.finishOrder(etPhone.text.toString(), etPassword.text.toString())
            }
        }
    }

    override fun unRegisterEvent() {
    }

    override fun getPresenter() = PasswordPresenter(DataManager.getInstance().dataService, this)

    override fun getLayoutId() = R.layout.fragment_password

    override fun navToFinishOrderPage() {
        val intent = Intent(context, FinishOrderSuccedActivity::class.java)
        startActivity(intent)
    }

    override fun navToOpenDoor(succeed: Boolean) {
        val intent =
            if (succeed) {
                Intent(context, OpenDoorSucceedActivity::class.java)
            } else {
                Intent(context, OpenDorrFailedActivity::class.java)
            }
        startActivity(intent)
    }
}
