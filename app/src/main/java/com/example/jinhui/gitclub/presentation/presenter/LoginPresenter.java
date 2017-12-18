package com.example.jinhui.gitclub.presentation.presenter;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.common.base.BasePresenter;
import com.example.jinhui.gitclub.common.base.DefaultSubscriber;
import com.example.jinhui.gitclub.common.utils.RxJavaUtils;
import com.example.jinhui.gitclub.common.utils.Utils;
import com.example.jinhui.gitclub.model.net.DataSource.UserDataSource;
import com.example.jinhui.gitclub.presentation.contract.LoginContract;

import rx.functions.Action1;


public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private UserDataSource mDataSource;

    public LoginPresenter(UserDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public void login(String username, String password) {
        if (!checkNetwork())
            return;
        addSubscription(mDataSource.login(username, password)
                .compose(RxJavaUtils.<Boolean>setLoadingListener(getView()))
                .subscribe(new DefaultSubscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean success) {
                        if (success)
                            getView().showOnSuccess();
                        else getView().showOnError(Utils.getString(R.string.error_login));
                    }

                    @Override
                    protected void onError(String errorStr) {
                        getView().showOnError(Utils.getString(R.string.error_login) + "Check your name and password.");
                    }
                }));
    }

    @Override
    public void signOut() {
        mDataSource.signOut()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean success) {
                        if (success)
                            getView().showOnSuccess();
                        else getView().showOnError(Utils.getString(R.string.error_sign_out));
                    }
                });
    }

    @Override
    public String getLoginUserName() {
        return mDataSource.getLoginUserName();
    }

}