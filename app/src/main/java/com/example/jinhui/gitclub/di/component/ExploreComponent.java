package com.example.jinhui.gitclub.di.component;

import com.example.jinhui.gitclub.di.DiView;
import com.example.jinhui.gitclub.di.module.PresenterModule;
import com.example.jinhui.gitclub.presentation.contract.ExploreContract;
import com.example.jinhui.gitclub.presentation.view.fragment.explore.ExploreFragment;

import dagger.Component;




@DiView
@Component(modules = {PresenterModule.class}, dependencies = {AppComponent.class})
public interface ExploreComponent {
    void inject(ExploreFragment fragment);
    ExploreContract.Presenter PRESENTER();
}
