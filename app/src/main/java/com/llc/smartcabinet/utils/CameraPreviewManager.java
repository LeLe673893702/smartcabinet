package com.llc.smartcabinet.utils;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.TextureView;

import com.llc.smartcabinet.view.AutoFitTextureView;
import com.rgbsdk.yshface.callback.CameraDataCallback;

import java.io.IOException;

/**
 * Time: 2019/1/24
 * Author: v_chaixiaogang
 * Description:
 */
public class CameraPreviewManager implements TextureView.SurfaceTextureListener {

    private static final String TAG = "camera_preview";


    AutoFitTextureView mTextureView;
    private SurfaceTexture mSurfaceTexture;

    public static final int CAMERA_FACING_FRONT = 1;

    private int previewWidth;
    private int previewHeight;

    private Camera mCamera;
    private int displayOrientation = 0;
    private int cameraId = 0;
    // 镜像处理
    private boolean mirror = false;
    private Boolean isOpen = false;
    private boolean hasError = false;

    private CameraDataCallback mCameraDataCallback;

    public int getDisplayOrientation() {
        return displayOrientation;
    }

    public void setDisplayOrientation(int displayOrientation) {
        this.displayOrientation = displayOrientation;
    }

    public void setCameraId(int cameraId) {this.cameraId = cameraId;}
    public int getCameraId(){return this.cameraId;}

    public void setMirror(boolean mirror) {this.mirror = mirror;}
    public boolean getMirror() {return this.mirror;}


    public CameraPreviewManager(){
        selfTestThread();
    }
    /**
     * 开启预览
     */
    public void startPreview(AutoFitTextureView textureView, int width,
                             int height, CameraDataCallback cameraDataCallback) {
        this.mCameraDataCallback = cameraDataCallback;
        mTextureView = textureView;
        this.previewWidth = width;
        this.previewHeight = height;
        mSurfaceTexture = mTextureView.getSurfaceTexture();
        mTextureView.setSurfaceTextureListener(this);
        openCamera();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture texture, int i, int i1) {
        mSurfaceTexture = texture;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int i, int i1) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture texture) {
    }

    /**
     * 关闭预览
     */
    public void stopPreview() {
        if (mCamera != null) {
            try {
                mCamera.setPreviewTexture(null);
                mTextureView = null;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;

                isOpen = false;
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * 开启摄像头
     */
    private void openCamera() {
        try {
            if (mCamera == null) {
                mCamera = Camera.open(cameraId);
            }

            // 摄像头图像预览角度
            mCamera.setDisplayOrientation(displayOrientation);
            if (displayOrientation == 90 || displayOrientation == 270) {
                if (mirror) {
                    mTextureView.setRotationY(180);
                } else {
                    mTextureView.setRotationY(0);
                }
                // 旋转90度或者270，需要调整宽高
                mTextureView.setAspectRatio(previewHeight, previewWidth);
            } else {
                if (mirror) {
                    mTextureView.setRotationY(180);
                } else {
                    mTextureView.setRotationY(0);
                }
                mTextureView.setAspectRatio(previewWidth, previewHeight);
            }

            Camera.Parameters params = mCamera.getParameters();
            params.setPreviewSize(previewWidth, previewHeight);
            mCamera.setParameters(params);
            try {
                mCamera.setPreviewTexture(mSurfaceTexture);
                mCamera.startPreview();
                mCamera.setPreviewCallback((bytes, camera) -> {
                    if (mCameraDataCallback != null) {
                        mCameraDataCallback.onGetCameraData(bytes, camera,
                                previewWidth, previewHeight);
                    }
                });

                mCamera.setErrorCallback((i, camera) -> hasError = true);
                isOpen = true;
                hasError = false;

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    private void selfTestThread(){
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (isOpen && hasError) {
                            if(mCamera != null)  {
                                mCamera.release();
                                mCamera = null;
                            }
                            Thread.sleep(10000);
                            openCamera();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    //循环暂停时间
                    try {
                        Thread.sleep(500);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
