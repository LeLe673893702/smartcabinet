package com.llc.smartcabinet.base.state

import android.view.LayoutInflater
import android.view.View
import com.github.nukc.stateview.StateView
import com.llc.smartcabinet.base.BaseActivity
import com.llc.smartcabinet.base.BasePresenter

/**
 *
 * @what
 * @author newler
 * @date 2019/12/13
 *
 */
abstract class BaseStateActivity<T : BasePresenter> : BaseActivity<T>() {
    private lateinit var stateView:StateView;

    override fun setContentView(layoutResID: Int) {
        val child: View = LayoutInflater.from(this).inflate(layoutResID, null, false)
        stateView = StateView.inject(child)
        super.setContentView(child)
    }

    fun showLoadFailed() {
        stateView.showRetry()
    }
    fun showContent() {
        stateView.showContent()
    }

    fun showLoading() {
        stateView.showLoading()
    }

    fun showEmpty() {
        stateView.showEmpty()
    }

}