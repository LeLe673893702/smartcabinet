package com.llc.smartcabinet.data.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.llc.smartcabinet.constants.Constants;
import com.llc.smartcabinet.data.DataManager;
import com.llc.smartcabinet.data.service.ClientWebSocketListener;
import com.llc.smartcabinet.utils.SPUtil;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

/**
 * @author 17173
 * @what
 * @date 2020/1/16
 */
public class ConnectWorker extends Worker {
    public ConnectWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String url = "ws://118.31.103.94:8083/base/terminal/connect?id"+
                SPUtil.getInstance().getString(Constants.TERMINAL_ID);
        Request request = new Request.Builder().url(url).build();
        DataManager.getInstance().getClient().newWebSocket(request, new ClientWebSocketListener());
        return Result.success();
    }
}
