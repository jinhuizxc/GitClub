package com.example.jinhui.gitclub.model.net.client;

import com.example.jinhui.gitclub.common.config.Constant;

import okhttp3.OkHttpClient;


/**
 * Created by tlh on 2016/8/26 :)
 */
public class GithubCommonRetrofit extends BaseRetrofit {
    private GithubOkHttpClient client;

    public GithubCommonRetrofit(GithubOkHttpClient client) {
        this.client = client;
    }

    @Override
    protected String getBaseUrl() {
        return Constant.URL_GITHUB;
    }

    @Override
    protected OkHttpClient getOkHttpClient() {
        return client.build();
    }
}
