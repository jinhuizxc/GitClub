package com.example.jinhui.gitclub.model.net.DataSource;

import android.text.TextUtils;

import com.example.jinhui.gitclub.common.utils.RxJavaUtils;
import com.example.jinhui.gitclub.model.entity.ShowCase;
import com.example.jinhui.gitclub.model.entity.ShowCaseInfo;
import com.example.jinhui.gitclub.model.entity.Trending;
import com.example.jinhui.gitclub.model.net.service.ExploreService;

import java.util.List;
import java.util.Map;

import rx.Observable;


/**
 * Created by tlh on 2016/8/27 :)
 */
public class ExploreDataSource {
    private ExploreService api;

    public ExploreDataSource(ExploreService api) {
        this.api = api;
    }

    public Observable<List<Trending>> listTrending() {
        return api.listTrending().compose(RxJavaUtils.<List<Trending>>applySchedulers());
    }

    public Observable<List<Trending>> listTrending(String language) {
        if (TextUtils.isEmpty(language))
            return listTrending();
        return api.listTrending(language)
                .compose(RxJavaUtils.<List<Trending>>applySchedulers());
    }

    //key: language & since
    public Observable<List<Trending>> listTrending(Map<String, String> params) {
        return api.listTrending(params)
                .compose(RxJavaUtils.<List<Trending>>applySchedulers());
    }

    public Observable<List<ShowCase>> listShowCase() {
        return api.listShowCase()
                .compose(RxJavaUtils.<List<ShowCase>>applySchedulers());
    }

    public Observable<ShowCaseInfo> getShowCaseDetail(String slug) {
        return api.getShowCaseDetail(slug)
                .compose(RxJavaUtils.<ShowCaseInfo>applySchedulers());
    }
}
