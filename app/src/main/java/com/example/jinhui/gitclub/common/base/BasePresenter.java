package com.example.jinhui.gitclub.common.base;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.common.utils.Utils;
import com.example.jinhui.gitclub.common.wrapper.Note;
import com.example.jinhui.gitclub.di.component.ComponentHolder;
import com.example.jinhui.gitclub.model.sharedprefs.AccountPrefs;
import com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.UpdateType;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.LOAD_MORE;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.REFRESH;

public class BasePresenter<T extends BaseView> implements MvpPresenter<T> {

    private CompositeSubscription subscriptions;
    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        view = null;
        if (subscriptions != null) {
            subscriptions.unsubscribe();
            subscriptions = null;
        }
        Utils.leakWatch(this);
    }

    public T getView() {
        return view;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public boolean checkLogin() {
        if (AccountPrefs.isLogin(ComponentHolder.getContext()))
            return true;
        view.showOnError(Utils.getString(R.string.note_to_login));
        return false;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(BaseView) before" + " requesting data to the Presenter");
        }
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public boolean checkNetwork() {
        if (!Utils.isNetworkAvailable(ComponentHolder.getContext())) {
            Note.show(Utils.getString(R.string.error_no_network));
            return false;
        }
        return true;
    }

    @UpdateType
    protected int getUpdateType(int page) {
        return page == 1 ? REFRESH : LOAD_MORE;
    }
}