package com.llc.smartcabinet.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.hwangjr.rxbus.RxBus
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class BaseFragment<T : BasePresenter> : Fragment() {
    protected var mPresenter:T? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = getPresenter()

        initView()
        RxBus.get().register(mPresenter)

        registerEvent()
        mPresenter?.let {
            lifecycle.addObserver(it)
            it.start()
        }
    }

    abstract fun initView()

    abstract fun registerEvent()

    abstract fun unRegisterEvent()

    @Nullable
    abstract fun getPresenter() :T

    abstract fun getLayoutId(): Int

    fun <T> autoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this))
    }

    fun <T> disposeOnDestroy(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
    }

    fun attainActivity():FragmentActivity? {
        return  activity
    }

    override fun onDestroyView() {
        getPresenter().run {
            lifecycle.removeObserver(this)
        }
        unRegisterEvent()
        RxBus.get().unregister(mPresenter)
        super.onDestroyView()
    }
}