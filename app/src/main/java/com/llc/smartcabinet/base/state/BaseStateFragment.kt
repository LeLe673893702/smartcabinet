package com.llc.smartcabinet.base.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nukc.stateview.StateView
import com.llc.smartcabinet.base.BaseFragment
import com.llc.smartcabinet.base.BasePresenter

/**
 *
 * @what
 * @author newler
 * @date 2019/12/13
 *
 */
abstract class BaseStateFragment<T : BasePresenter> : BaseFragment<T>() {
    private lateinit var stateView:StateView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val child: View = inflater.inflate(getLayoutId(),container, false)

        stateView = StateView.inject(child)
        return child
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