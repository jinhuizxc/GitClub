package com.example.jinhui.gitclub.common;

import android.app.Application;

import com.example.jinhui.gitclub.common.utils.LogUtils;
import com.example.jinhui.gitclub.di.component.AppComponent;
import com.example.jinhui.gitclub.di.component.ComponentHolder;
import com.example.jinhui.gitclub.di.component.DaggerAppComponent;
import com.example.jinhui.gitclub.di.component.DaggerExploreComponent;
import com.example.jinhui.gitclub.di.component.DaggerHomePageComponent;
import com.example.jinhui.gitclub.di.component.DaggerListItemComponent;
import com.example.jinhui.gitclub.di.component.DaggerLoginComponent;
import com.example.jinhui.gitclub.di.component.DaggerNewsComponent;
import com.example.jinhui.gitclub.di.component.DaggerRepoPageComponent;
import com.example.jinhui.gitclub.di.component.DaggerSearchComponent;
import com.example.jinhui.gitclub.di.module.ContextModule;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;



/**
 * Created by tlh on 2016/8/24 :)
 */
public class AndroidApplication extends Application {
    private static AndroidApplication instance;
    private RefWatcher refWatcher;

    public static AndroidApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initComponents();
        instance = this;
        LogUtils.init();
        Stetho.initializeWithDefaults(this);
        refWatcher = LeakCanary.install(this);
        //ShareSDk
//        PlatformConfig.setWeixin("wx3fd4de2af1ff6e1e", "fc734e6e848cb6c47b06d0c0e9159c99");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    protected void initComponents() {
        AppComponent appComponent = DaggerAppComponent.builder().contextModule(new ContextModule(this)).build();
        ComponentHolder.setContext(this);
        ComponentHolder.setAppComponent(appComponent);
        ComponentHolder.setExploreComponent(DaggerExploreComponent.builder().appComponent(appComponent).build());
        ComponentHolder.setHomePageComponent(DaggerHomePageComponent.builder().appComponent(appComponent).build());
        ComponentHolder.setListItemComponent(DaggerListItemComponent.builder().appComponent(appComponent).build());
        ComponentHolder.setLoginComponent(DaggerLoginComponent.builder().appComponent(appComponent).build());
        ComponentHolder.setNewsComponent(DaggerNewsComponent.builder().appComponent(appComponent).build());
        ComponentHolder.setRepoPageComponent(DaggerRepoPageComponent.builder().appComponent(appComponent).build());
        ComponentHolder.setSearchComponent(DaggerSearchComponent.builder().appComponent(appComponent).build());
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
}