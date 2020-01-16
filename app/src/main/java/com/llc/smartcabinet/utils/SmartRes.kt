package com.llc.smartterminal.utils

import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import com.llc.smartcabinet.App


class SmartRes {

    companion object {

        @JvmStatic
        fun getColor(@ColorRes colorRes: Int): Int {
            return if (Build.VERSION.SDK_INT < 23) {
                App.getContext().resources.getColor(colorRes)
            } else {
                App.getContext().resources.getColor(colorRes, null)
            }
        }

        @JvmStatic
        fun getDimens(@DimenRes dimenRes: Int): Float {
            return App.getContext().resources.getDimension(dimenRes)
        }

        @JvmStatic
        fun getDrawable(@DrawableRes drawableRes: Int): Drawable? {
            return AppCompatResources.getDrawable(App.getContext(), drawableRes)
        }

        @JvmStatic
        fun getString(@StringRes stringRes: Int): String {
            return App.getContext().resources.getString(stringRes)
        }

        @JvmStatic
        fun dpToPx(dpValue: Int): Int {
            return (App.getContext().resources.displayMetrics.density * dpValue).toInt()
        }
    }
}