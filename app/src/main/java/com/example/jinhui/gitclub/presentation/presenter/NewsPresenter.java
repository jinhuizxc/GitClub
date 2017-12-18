package com.example.jinhui.gitclub.presentation.presenter;

import android.content.Context;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.common.base.BasePresenter;
import com.example.jinhui.gitclub.common.base.DefaultSubscriber;
import com.example.jinhui.gitclub.common.utils.StringUtils;
import com.example.jinhui.gitclub.common.utils.Utils;
import com.example.jinhui.gitclub.model.entity.Event;
import com.example.jinhui.gitclub.model.entity.UserInfo;
import com.example.jinhui.gitclub.model.net.DataSource.UserDataSource;
import com.example.jinhui.gitclub.model.sharedprefs.AccountPrefs;
import com.example.jinhui.gitclub.presentation.contract.NewsContract;

import java.util.List;

import rx.functions.Action0;


import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.UpdateType;

public class NewsPresenter extends BasePresenter<NewsContract.View> implements NewsContract.Presenter {

    private final UserDataSource mUserDataSource;
    private final Context mCtx;

    private boolean isFlying;

    public NewsPresenter(UserDataSource userDataSource, Context context) {
        mUserDataSource = userDataSource;
        mCtx = context;
    }

    @Override
    public void listNews(final int page) {
        if (isFlying) {
            getView().showOnError(getUpdateType(page), Utils.getString(R.string.reqest_flying));
            return;
        }
        UserInfo user = AccountPrefs.getLoginUser(mCtx);
        if (!checkLogin(user, getUpdateType(page))) return;
        isFlying = true;
        addSubscription(mUserDataSource.listNews(user.getLogin(), page)
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        isFlying = false;
                    }
                })
                .subscribe(new DefaultSubscriber<List<Event>>() {
                    @Override
                    public void onNext(List<Event> events) {
                        getView().OnGetNews(events, getUpdateType(page));
                        getView().showOnSuccess();
                    }

                    @Override
                    protected void onError(String errorStr) {
                        getView().showOnError(getUpdateType(page), StringUtils.append(mCtx.getString(R.string.error_get_news), errorStr));
                    }
                }));
    }

    protected boolean checkLogin(UserInfo user, @UpdateType int updateType) {
        if (user == null) {
            getView().showOnError(updateType, mCtx.getString(R.string.error_not_login));
            getView().showLoginDialog();
            return false;
        }
        return true;
    }
}