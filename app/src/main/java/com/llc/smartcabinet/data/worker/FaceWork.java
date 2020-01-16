package com.llc.smartcabinet.data.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.llc.smartcabinet.data.model.Face;
import com.llc.smartcabinet.data.service.AppDatabase;
import com.llc.smartcabinet.utils.Base64Util;
import com.llc.smartterminal.utils.RxUtils;
import com.rgbsdk.yshface.YSHFacePass;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 17173
 * @what
 * @date 2020/1/16
 */
public class FaceWork extends Worker {
    public FaceWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        List<Face> faces = AppDatabase.getInstance().userDao().getAllFaces();
        if (!faces.isEmpty()) {
            for (Face face : faces) {
                YSHFacePass.delFace(face.getFaceId(), face.getGroupId());
                YSHFacePass.addFace(face.getFaceId(),
                        face.getGroupId(), Base64Util.base64ToByteArray(face.getFeature()));
            }
        }
        return Result.success();
    }
}
