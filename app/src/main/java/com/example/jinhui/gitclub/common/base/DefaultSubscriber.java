package com.example.jinhui.gitclub.common.base;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.common.utils.LogUtils;
import com.example.jinhui.gitclub.common.utils.Utils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;


/**
 * Created by tlh on 2016/8/27 :)
 */
public abstract class DefaultSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
//        LogUtils.d("RxCompleted");
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            onError(Utils.getString(R.string.error_time_out));
        } else if (e instanceof HttpException) {
            onError(Utils.getString(R.string.error_network));
            HttpException httpException = (HttpException) e;
            LogUtils.e(String.valueOf(httpException.code()));
            LogUtils.e(httpException.message());
            if (httpException.response() != null && httpException.response().errorBody() != null) {
                try {
                    LogUtils.e(httpException.response().message());
                    String bodyStr = httpException.response().errorBody().string();
                    LogUtils.e(bodyStr);
                } catch (IOException e1) {
                    LogUtils.e(e1.getMessage());
                }
            }
        } else {
            onError("");
        }
    }

    protected void onError(String errorStr) {
        LogUtils.e(errorStr);
    }

}
