package com.llc.smartcabinet.base.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.llc.smartcabinet.R
import kotlinx.android.synthetic.main.dialog_loading.view.*

/**
 *
 * @what
 * @author newler
 * @date 2019/12/29
 *
 */
class LoadingDialog: DialogFragment() {
    private val loadingText by lazy {
        arguments?.getString(LOADING_TEXT) ?: "正在加载中"
    }
    companion object {
        const val LOADING_TEXT = "loading_text"
        fun newInstance(loadingText: String = "正在加载中") : LoadingDialog {
            val loadingDialog = LoadingDialog()
            val bundle = Bundle()
            bundle.putString(LOADING_TEXT, loadingText)
            loadingDialog.arguments = bundle
            return loadingDialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_loading, container);
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.tvLoadingInfo.text = loadingText
    }
}