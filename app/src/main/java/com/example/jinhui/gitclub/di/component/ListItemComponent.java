package com.example.jinhui.gitclub.di.component;

import com.example.jinhui.gitclub.di.DiView;
import com.example.jinhui.gitclub.di.module.PresenterModule;
import com.example.jinhui.gitclub.presentation.view.activity.detail_list.ListRepoActivity;
import com.example.jinhui.gitclub.presentation.view.activity.detail_list.ListUserActivity;

import dagger.Component;


@DiView
@Component(modules = {PresenterModule.class}, dependencies = {AppComponent.class})
public interface ListItemComponent {
    void inject(ListUserActivity activity);

    void inject(ListRepoActivity activity);
}