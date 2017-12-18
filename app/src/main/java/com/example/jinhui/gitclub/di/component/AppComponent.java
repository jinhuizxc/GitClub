package com.example.jinhui.gitclub.di.component;

import android.content.Context;

import com.example.jinhui.gitclub.di.module.ContextModule;
import com.example.jinhui.gitclub.di.module.NetModule;
import com.example.jinhui.gitclub.model.net.DataSource.ArsenalDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.ExploreDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.GankDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.RepositoryDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.UserDataSource;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {NetModule.class, ContextModule.class})
public interface AppComponent {
    Context CONTEXT();

    ExploreDataSource exploreDataSource();

    RepositoryDataSource repositoryDataSource();

    UserDataSource userDataSource();

    GankDataSource gankDataSource();

    ArsenalDataSource arsenalDataSource();
}