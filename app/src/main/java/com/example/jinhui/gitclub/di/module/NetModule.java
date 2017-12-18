package com.example.jinhui.gitclub.di.module;

import android.content.Context;

import com.example.jinhui.gitclub.model.net.DataSource.ArsenalDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.ExploreDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.GankDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.RepositoryDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.UserDataSource;
import com.example.jinhui.gitclub.model.net.client.ArsenalRetrofit;
import com.example.jinhui.gitclub.model.net.client.CacheOkHttpClient;
import com.example.jinhui.gitclub.model.net.client.GankRetrofit;
import com.example.jinhui.gitclub.model.net.client.GithubAuthRetrofit;
import com.example.jinhui.gitclub.model.net.client.GithubCommonRetrofit;
import com.example.jinhui.gitclub.model.net.client.GithubExploreRetrofit;
import com.example.jinhui.gitclub.model.net.client.GithubOkHttpClient;
import com.example.jinhui.gitclub.model.net.service.ArsenalService;
import com.example.jinhui.gitclub.model.net.service.ExploreService;
import com.example.jinhui.gitclub.model.net.service.GankService;
import com.example.jinhui.gitclub.model.net.service.RepositoryService;
import com.example.jinhui.gitclub.model.net.service.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class NetModule {
    @Provides
    @Singleton
    public GithubCommonRetrofit provideGithubCommonRetrofit(GithubOkHttpClient client) {
        return new GithubCommonRetrofit(client);
    }

    @Provides
    public GithubAuthRetrofit provideGithubAuthRetrofit() {
        return new GithubAuthRetrofit();
    }

    @Provides
    public GithubExploreRetrofit provideGithubExploreRetrofit(CacheOkHttpClient client) {
        return new GithubExploreRetrofit(client);
    }

    @Provides
    public GankRetrofit provideGankExploreRetrofit(CacheOkHttpClient client) {
        return new GankRetrofit(client);
    }

    @Provides
    public ArsenalRetrofit provideArsenalRetrofit(CacheOkHttpClient client) {
        return new ArsenalRetrofit(client);
    }

    @Provides
    public ExploreService provideExploreService(GithubExploreRetrofit githubExploreRetrofit) {
        return githubExploreRetrofit.build().create(ExploreService.class);
    }

    @Provides
    public GankService provideGankService(GankRetrofit gankRetrofit) {
        return gankRetrofit.build().create(GankService.class);
    }

    @Provides
    public ArsenalService provideArsenalService(ArsenalRetrofit arsenalRetrofit) {
        return arsenalRetrofit.build().create(ArsenalService.class);
    }

    @Provides
    public RepositoryService provideRepositoryService(GithubCommonRetrofit githubCommonRetrofit) {
        return githubCommonRetrofit.build().create(RepositoryService.class);
    }

    @Provides
    public UserService provideUserService(GithubCommonRetrofit githubCommonRetrofit) {
        return githubCommonRetrofit.build().create(UserService.class);
    }

    @Provides
    @Singleton
    public UserDataSource provideUserDataSource(GithubAuthRetrofit authRetrofit, UserService userApi, Context ctx) {
        return new UserDataSource(authRetrofit, userApi, ctx);
    }

    @Provides
    @Singleton
    public RepositoryDataSource provideRepositoryDataSource(RepositoryService repositoryApi) {
        return new RepositoryDataSource(repositoryApi);
    }

    @Provides
    @Singleton
    public ExploreDataSource provideExploreDataSource(ExploreService exploreApi) {
        return new ExploreDataSource(exploreApi);
    }

    @Provides
    @Singleton
    public GankDataSource provideGankDataSource(GankService gankService, RepositoryService repositoryService) {
        return new GankDataSource(gankService, repositoryService);
    }

    @Provides
    @Singleton
    public ArsenalDataSource provideArsenalDataSource(ArsenalService arsenalService, RepositoryService repositoryService) {
        return new ArsenalDataSource(arsenalService, repositoryService);
    }
}