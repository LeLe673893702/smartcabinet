package com.llc.smartcabinet.base

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.hwangjr.rxbus.RxBus
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider


abstract class BaseActivity<T : BasePresenter> : AppCompatActivity() {
    protected var mPresenter:T? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mPresenter = getPresenter()

        RxBus.get().register(mPresenter)

        initView()

        registerEvent()
        mPresenter?.run {
            lifecycle.addObserver(this)
            this.start()
        }
    }

    abstract fun initView()

    abstract fun registerEvent()

    abstract fun unRegisterEvent()

    @Nullable
    abstract fun getPresenter(): T

    abstract fun getLayoutId(): Int

    fun <T> autoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this))
    }

    fun <T> disposeOnDestroy(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
    }

    fun attainActivity() = this

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.let {
            lifecycle.removeObserver(it)
        }
        RxBus.get().unregister(mPresenter)
        unRegisterEvent()
    }

}