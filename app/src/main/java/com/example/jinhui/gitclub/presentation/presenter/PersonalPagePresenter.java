package com.example.jinhui.gitclub.presentation.presenter;

import android.content.Context;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.common.base.BasePresenter;
import com.example.jinhui.gitclub.common.base.DefaultSubscriber;
import com.example.jinhui.gitclub.common.utils.RxJavaUtils;
import com.example.jinhui.gitclub.common.utils.Utils;
import com.example.jinhui.gitclub.common.wrapper.Note;
import com.example.jinhui.gitclub.model.entity.UserInfo;
import com.example.jinhui.gitclub.model.net.DataSource.UserDataSource;
import com.example.jinhui.gitclub.model.sharedprefs.AccountPrefs;
import com.example.jinhui.gitclub.presentation.contract.PersonalPageContract;

import rx.Observable;


public class PersonalPagePresenter extends BasePresenter<PersonalPageContract.View> implements PersonalPageContract.Presenter {

    private final UserDataSource mUserDataSource;
    private final Context mCtx;

    public PersonalPagePresenter(UserDataSource userDataSource, Context ctx) {
        mUserDataSource = userDataSource;
        mCtx = ctx;
    }

    @Override
    public void checkIfFollowing(String user) {
        UserInfo loginUser = AccountPrefs.getLoginUser(mCtx);
        if (loginUser == null) {
            getView().showOnError(Utils.getString(R.string.error_not_login));
            return;
        }
        if (loginUser.getLogin().equals(user)) {
            getView().showOnError(Utils.getString(R.string.error_self_follow));
            return;
        }
        addSubscription(
                mUserDataSource.checkIfFollowing(user)
                        .subscribe(new DefaultSubscriber<Boolean>() {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                getView().onCheckFollowing(aBoolean);
                            }
                        })
        );
    }

    @Override
    public void getUserInfo(final String user) {
        addSubscription(
                mUserDataSource.getUserInfo(user)
                        .compose(RxJavaUtils.<UserInfo>setLoadingListener(getView()))
                        .subscribe(new DefaultSubscriber<UserInfo>() {
                                       @Override
                                       public void onNext(UserInfo userInfo) {
                                           getView().showOnSuccess();
                                           getView().onGetUserInfo(userInfo);
                                       }

                                       @Override
                                       protected void onError(String errorStr) {
                                           getView().showOnError(errorStr);
                                       }
                                   }
                        )
        );
    }

    @Override
    public void toFollow(final String user, final boolean toggle) {
        UserInfo loginUser = AccountPrefs.getLoginUser(mCtx);
        if (loginUser == null) {
            getView().showOnError(Utils.getString(R.string.error_not_login));
            return;
        }
        if (loginUser.getLogin().equals(user)) {
            getView().showOnError(Utils.getString(R.string.error_self_follow));
            return;
        }
        Observable<Boolean> observable;
        final int hint_success;
        final int hint_error;
        if (toggle) {
            observable = mUserDataSource.toFollow(user);
            hint_success = R.string.success_follow_user;
            hint_error = R.string.error_follow_user;
        } else {
            observable = mUserDataSource.toUnFollow(user);
            hint_success = R.string.success_unfollow_user;
            hint_error = R.string.error_unfollow_user;
        }
        addSubscription(observable
                .subscribe(new DefaultSubscriber<Boolean>() {
                               @Override
                               public void onNext(Boolean result) {
                                   if (result) {
                                       Note.show(Utils.getString(hint_success) + user);
                                   } else {
                                       Note.show(Utils.getString(hint_error) + user);
                                   }
                               }

                               @Override
                               protected void onError(String errorStr) {
                                   super.onError(errorStr);
                                   Note.show(Utils.getString(hint_error) + user);
                               }
                           }
                ));
    }
}