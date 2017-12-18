package com.example.jinhui.gitclub.presentation.contract;


import com.example.jinhui.gitclub.common.base.BaseView;
import com.example.jinhui.gitclub.common.base.MvpPresenter;

public interface LoginContract {

    interface View extends BaseView {
    }

    interface Presenter extends MvpPresenter<View> {
        void login(String username, String password);
        void signOut();
        String getLoginUserName();
    }
}