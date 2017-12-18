package com.example.jinhui.gitclub.di.component;

import com.example.jinhui.gitclub.di.DiView;
import com.example.jinhui.gitclub.di.module.PresenterModule;
import com.example.jinhui.gitclub.presentation.view.activity.user_personal_page.PersonalHomePageActivity;
import com.example.jinhui.gitclub.presentation.view.fragment.home.HomePageFragment;

import dagger.Component;


@DiView
@Component(modules = {PresenterModule.class}, dependencies = {AppComponent.class})
public interface HomePageComponent {
    void inject(HomePageFragment fragment);

    void inject(PersonalHomePageActivity activity);
}
