package com.example.jinhui.gitclub.di.component;

import com.example.jinhui.gitclub.di.DiView;
import com.example.jinhui.gitclub.di.module.PresenterModule;
import com.example.jinhui.gitclub.presentation.view.activity.repo_page.RepoPageActivity;
import com.example.jinhui.gitclub.presentation.view.activity.repo_page.RepoSourceActivity;

import dagger.Component;


@DiView
@Component(modules = {PresenterModule.class}, dependencies = {AppComponent.class})
public interface RepoPageComponent {
    void inject(RepoPageActivity activity);

    void inject(RepoSourceActivity activity);
}