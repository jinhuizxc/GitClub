package com.example.jinhui.gitclub.model.net.service;

import com.example.jinhui.gitclub.model.entity.ShowCase;
import com.example.jinhui.gitclub.model.entity.ShowCaseInfo;
import com.example.jinhui.gitclub.model.entity.Trending;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Created by tlh on 2016/8/26 :)
 */
public interface ExploreService {
    @GET("trending")
    Observable<List<Trending>> listTrending();

    @GET("trending")
    Observable<List<Trending>> listTrending(@Query("language") String language);

    @GET("trending")
    Observable<List<Trending>> listTrending(@QueryMap Map<String, String> params);//key: language & since

    @GET("showcases")
    Observable<List<ShowCase>> listShowCase();

    @GET("showcases/{slug}")
    Observable<ShowCaseInfo> getShowCaseDetail(@Path("slug") String slug);

}
