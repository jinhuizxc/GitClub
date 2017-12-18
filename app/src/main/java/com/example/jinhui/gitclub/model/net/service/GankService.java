package com.example.jinhui.gitclub.model.net.service;

import com.example.jinhui.gitclub.model.entity.GankResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by tlh on 2016/10/5 :
 */

public interface GankService {
    @GET("{page_size}/{page}")
    Observable<GankResponse> getData(@Path("page_size") int pageSize, @Path("page") int page);
}
