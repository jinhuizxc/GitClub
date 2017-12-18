package com.example.jinhui.gitclub.di.component;

import com.example.jinhui.gitclub.di.DiView;
import com.example.jinhui.gitclub.di.module.PresenterModule;
import com.example.jinhui.gitclub.presentation.contract.SearchContract;
import com.example.jinhui.gitclub.presentation.view.fragment.search.SearchFragment;

import dagger.Component;

@DiView
@Component(modules = {PresenterModule.class}, dependencies = {AppComponent.class})
public interface SearchComponent {
    void inject(SearchFragment searchFragment);
    SearchContract.Presenter PRESENTER();
}
