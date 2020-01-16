package com.llc.smartterminal.base.state

import com.llc.smartcabinet.base.BaseView

interface BaseStateView : BaseView {
    fun showLoadFailed()
    fun showContent()
    fun showLoading()
    fun showEmpty()
}