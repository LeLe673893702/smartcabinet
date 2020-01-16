package com.llc.smartcabinet.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


interface BasePresenter: LifecycleObserver {
    fun start()
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()
}