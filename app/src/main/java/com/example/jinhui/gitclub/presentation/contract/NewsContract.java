package com.example.jinhui.gitclub.presentation.contract;

import com.example.jinhui.gitclub.common.base.BaseView;
import com.example.jinhui.gitclub.common.base.MvpPresenter;
import com.example.jinhui.gitclub.model.entity.Event;
import com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.UpdateType;

import java.util.List;



public interface NewsContract {
    interface View extends BaseView,ShowError {
        void OnGetNews(List<Event> newsList, @UpdateType int updateType);

        void showLoginDialog();
    }

    interface Presenter extends MvpPresenter<View> {
        void listNews(int page);
    }
}