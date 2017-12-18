package com.example.jinhui.gitclub.di.component;

import com.example.jinhui.gitclub.di.DiView;
import com.example.jinhui.gitclub.di.module.PresenterModule;
import com.example.jinhui.gitclub.presentation.view.fragment.news.NewsFragment;

import dagger.Component;

@DiView
@Component(modules = {PresenterModule.class}, dependencies = {AppComponent.class})
public interface NewsComponent {
    void inject(NewsFragment activity);
}
