package com.llc.smartterminal.utils

import android.annotation.SuppressLint
import android.widget.Toast
import com.llc.smartcabinet.App

/**
 *
 * @what
 * @author newler
 * @date 2019/12/13
 *
 */
class ToastUtils {
    private var mToast: Toast? = null

    companion object {

        val instance: ToastUtils by lazy {
            ToastUtils()
        }


    }

    fun showToast(text: String?) {
        if (mToast == null) {
            mToast = Toast.makeText(App.Companion.getContext(), text, Toast.LENGTH_SHORT)
        } else {
            mToast?.setText(text)
            mToast?.duration = Toast.LENGTH_SHORT
        }
        mToast?.show()
    }

    fun showToast(resID: Int) {
        showToast(App.getContext().resources.getString(resID))
    }
}