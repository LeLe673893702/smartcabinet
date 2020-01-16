package com.llc.smartcabinet.page.face


import android.content.Intent
import android.graphics.Color
import android.hardware.Camera
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import com.llc.smartcabinet.App
import com.llc.smartcabinet.R
import com.llc.smartcabinet.base.BaseFragment
import com.llc.smartcabinet.constants.Constants
import com.llc.smartcabinet.constants.YSHParams
import com.llc.smartcabinet.data.DataManager
import com.llc.smartcabinet.data.enumtype.OperationWay
import com.llc.smartcabinet.page.opendoor.OpenDoorSucceedActivity
import com.llc.smartcabinet.page.opendoor.OpenDorrFailedActivity
import com.llc.smartcabinet.page.order.FinishOrderSuccedActivity
import com.llc.smartcabinet.utils.CameraPreviewManager
import com.llc.smartcabinet.view.RectangleView
import com.llc.smartterminal.model.enumtype.CameraType
import com.orhanobut.logger.Logger
import com.rgbsdk.yshface.YSHFacePass
import com.rgbsdk.yshface.core.ResultModel
import com.rgbsdk.yshface.core.YSHConfig
import kotlinx.android.synthetic.main.fragment_face.*

/**
 * 人脸采集页面
 */
class FaceFragment : BaseFragment<FacePresenter>(), FaceContract.View {
    private var rgbData: ByteArray? = null
    private var irData: ByteArray? = null
    private val mMutex = Any()
    private val faceFrames:MutableList<RectangleView> by lazy {
        mutableListOf<RectangleView>()
    }

    private val mPreviews by lazy {
        arrayOf(CameraPreviewManager(), CameraPreviewManager())
    }

    private val config:YSHConfig by lazy {
        App.config
    }


    private val scaleWidth by lazy {
        tpvRgbCamera.width.toFloat() / YSHParams.PREVIEW_HEIGHT
    }
    private val scaleHeight by lazy {
        tpvRgbCamera.height.toFloat() / YSHParams.PREVIEW_WIDTH
    }

    @OperationWay
    private val operationWay : Int by lazy {
        arguments?.getInt(Constants.OPERATION_TYPE) ?: 0
    }

    companion object {
        fun newInstance(@OperationWay operationWay: Int) :FaceFragment {
            val faceFragment = FaceFragment()
            faceFragment.arguments = Bundle().apply {
                putInt(Constants.OPERATION_TYPE, operationWay)
            }
            return faceFragment
        }
    }

    override fun initView() {
        setCallback()
    }

    private fun setCallback() {
        YSHFacePass.setCallback({ model ->

            activity?.runOnUiThread {
                tpvRgbCamera?.let {
                    faceFrames.forEach {
                        flFaceFrame?.removeView(it)
                    }
                    faceFrames.clear()
                    if (validFace(model)) {
                        drawFaceRectangle(model)
                    }
                }
            }
        }) { model ->
            tpvRgbCamera?.let {
                Logger.e("faceId:${model.faceId}")
                if (model.code == 2 && validFace(model)) {
                    if (operationWay == OperationWay.FINISH_ORDER) {
                        mPresenter?.finishOrder(model.faceId)
                    } else {
                        mPresenter?.openDoor(model.faceId)
                    }
                }
            }
//            Logger.e("recoize${model.faceId}glasses${model.glasses}")
        }
    }

    /**
     * 判断人脸是否符合要求
     */
    private fun validFace(model: ResultModel) : Boolean {
        if (model.trackFaceInfos?.isEmpty() == true) {
            tvFaceWarning.text = getString(R.string.not_detect_face)
            return false
        }
        model.trackFaceInfos?.forEach {faceInfo->
            faceInfo.rect?.run {
                if (bottom*scaleHeight > tpvRgbCamera.bottom ||
                    top*scaleHeight < tpvRgbCamera.top ||
                    right*scaleWidth > tpvRgbCamera.right ||
                    left*scaleWidth < tpvRgbCamera.left  ) {
                    tvFaceWarning.text = "人脸超出屏幕"
                    return false
                }
                tvFaceWarning.text = ""
            }
        }
        return true
    }

    /**
     * 绘制人脸框
     */
    private fun drawFaceRectangle(model: ResultModel) {
            model.trackFaceInfos?.forEach {faceInfo->
                val color = Color.BLUE
                faceInfo.rect?.run{
//                    Logger.e("left:$left-top:$top-right:$right-bottom:$bottom")
                    val rectangleView = RectangleView(context,
                        (left*scaleWidth).toInt(),
                        (top*scaleHeight).toInt(),
                        (right*scaleWidth).toInt(),
                        (bottom*scaleHeight).toInt(),
                        color,"",1,true
                    )
                    val layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    flFaceFrame.addView(rectangleView, layoutParams)
                    faceFrames.add(rectangleView)
                }
            }
    }

    override fun registerEvent() {
        btOpenCamera.setOnClickListener {
            openCamera()
            YSHFacePass.start()
        }
    }

    override fun unRegisterEvent() {
        YSHFacePass.stop()
        closeCamera()
    }

    override fun getPresenter() = FacePresenter(DataManager.getInstance().dataService, this)

    override fun getLayoutId() = R.layout.fragment_face

    /**
     * 打开摄像头
     */
    private fun openCamera() {
        setReogizeParam()
        if (Camera.getNumberOfCameras() == 2) {
            try {
                mPreviews[CameraType.RGB].run {
                    cameraId = CameraType.RGB
                    mirror = YSHParams.MIRROR
                    displayOrientation = YSHParams.ORIENTATION
                startPreview(
                    tpvRgbCamera,
                    YSHParams.PREVIEW_WIDTH,
                    YSHParams.PREVIEW_HEIGHT
                ) { data, camera, width, height ->
                    synchronized(mMutex) {
                        rgbData = data
                        checkData(data)
                    }
                }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            try {
                mPreviews[CameraType.IR].run {
                    cameraId = CameraType.IR
                    mirror = YSHParams.MIRROR
                    displayOrientation = YSHParams.ORIENTATION
                    startPreview(
                        tpvIrCamera,
                        YSHParams.PREVIEW_WIDTH,
                        YSHParams.PREVIEW_HEIGHT
                    ) { data, camera, width, height ->
                        synchronized(mMutex) {
                            irData = data
                        }
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }


    /**
     * 输入视频数据
     */
    private fun checkData(data: ByteArray) {
        // 双目摄像头，同时输入两路摄像头数据，RGB和IR数据，model = 1
        if (Camera.getNumberOfCameras() == 2) {
            if (irData != null) {
                YSHFacePass.feedData(
                    data, YSHParams.ORIENTATION, YSHParams.MIRROR,
                    irData, YSHParams.ORIENTATION, YSHParams.MIRROR,
                    CameraType.RGB,
                    YSHParams.PREVIEW_WIDTH, YSHParams.PREVIEW_HEIGHT, 1
                )
                irData = null
            }
        } else {
            // 单目摄像头，同时输入两路摄像头数据，RGB和IR数据，model = 0
            YSHFacePass.feedData(
                data, 0, true, irData, 0, true, 0,
                YSHParams.PREVIEW_WIDTH,
                YSHParams.PREVIEW_HEIGHT, 0
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        closeCamera()
    }

    private fun closeCamera() {
        val numberOfCameras = Camera.getNumberOfCameras()
        for (i in 0 until numberOfCameras) {
            mPreviews[i].stopPreview()
        }
    }

    /**
     * 设置人脸识别参数
     */
    private fun setReogizeParam() {
        YSHFacePass.setQualityFilterLevel(1, -1)
        config.frameWidthNormalization = YSHParams.PREVIEW_WIDTH
        config.recogThreshold = 0.7f
        config.liveThreshold = 0.1f
        config.multiDetection = 2
        config.isRepeatedRecog = true
        config.recRank = 3
        config.filterRecogBlurThreshold = 99
        config.filterRecogQualityThreshold = 5
        YSHFacePass.setConfig(config)
    }

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
