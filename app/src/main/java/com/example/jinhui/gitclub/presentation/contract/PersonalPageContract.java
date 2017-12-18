package com.example.jinhui.gitclub.presentation.contract;


import com.example.jinhui.gitclub.common.base.BaseView;
import com.example.jinhui.gitclub.common.base.MvpPresenter;
import com.example.jinhui.gitclub.model.entity.UserInfo;

public interface PersonalPageContract {

    interface View extends BaseView {
        void onGetUserInfo(UserInfo userInfo);

        void onCheckFollowing(Boolean isFollowing);
    }

    interface Presenter extends MvpPresenter<View> {

        void checkIfFollowing(String user);

        void getUserInfo(String user);

        void toFollow(String user, boolean toggle);
    }
}