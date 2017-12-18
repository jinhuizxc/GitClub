package com.example.jinhui.gitclub.model.net.client;

import com.example.jinhui.gitclub.common.config.Constant;

import okhttp3.OkHttpClient;


/**
 * Created by tlh on 2016/8/26 :)
 */
public class GithubExploreRetrofit extends BaseRetrofit {
    private CacheOkHttpClient client;

    public GithubExploreRetrofit(CacheOkHttpClient client) {
        this.client = client;
    }

    @Override
    protected String getBaseUrl() {
        return Constant.URL_EXPLORE;
    }

    @Override
    protected OkHttpClient getOkHttpClient() {
        return client.build();
    }
}
