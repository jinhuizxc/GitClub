package com.example.jinhui.gitclub.model.net.client;

import com.example.jinhui.gitclub.common.config.Constant;

import okhttp3.OkHttpClient;


/**
 * Created by tlh on 2016/8/26 :)
 */
public class GankRetrofit extends BaseRetrofit {
    private CacheOkHttpClient client;

    public GankRetrofit(CacheOkHttpClient client) {
        this.client = client;
    }

    @Override
    protected String getBaseUrl() {
        return Constant.URL_GANK;
    }

    @Override
    protected OkHttpClient getOkHttpClient() {
        return client.build();
    }
}
