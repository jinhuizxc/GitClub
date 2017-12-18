package com.example.jinhui.gitclub.presentation.contract;


import android.support.annotation.IntDef;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.jinhui.gitclub.common.base.BaseView;
import com.example.jinhui.gitclub.common.base.MvpPresenter;
import com.example.jinhui.gitclub.common.config.Constant;
import com.example.jinhui.gitclub.model.entity.RepositoryInfo;
import com.example.jinhui.gitclub.model.entity.UserEntity;
import com.example.jinhui.gitclub.presentation.presenter.IRepoListPresenter;
import com.example.jinhui.gitclub.presentation.presenter.IUserListPresenter;
import com.example.jinhui.gitclub.presentation.view.fragment.search.ListLoadingListener;
import com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.UpdateType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;



public interface SearchContract {
    int REPO = 0;
    int USER = 1;

    @IntDef({REPO, USER})
    @Retention(RetentionPolicy.SOURCE)
    @interface ListType {
    }

    interface View extends BaseView {
        void onGetRepos(int total_count, List<RepositoryInfo> items, @UpdateType int updateType);

        void onGetUsers(int total_count, List<UserEntity> items, @UpdateType int updateType);

        void showListRefreshLoading(@ListType int listType);

        void showOnError(String msg, @ListType int type, @UpdateType int updateType);
    }

    interface Presenter extends MvpPresenter<View>, IRepoListPresenter, IUserListPresenter {

        void setCurrentSearchEntity(SearchEntity currentSearchEntity);

        SearchEntity getCurrentSearchEntity();

        SearchEntity getRepoSearchEntity();

        SearchEntity getUserSearchEntity();

        void initialSearch();

        void searchCurrent(boolean fromSearchView, int page);

        void searchRepo(int page);

        void searchUser(int page);

        MaterialDialog getDialogLang();

        MaterialDialog getDialogSortRepo();

        MaterialDialog getDialogSortUser();
    }

    interface OnListFragmentInteractListener {
        void onFetchPage(@ListType int type, int page);

        Presenter getPresenter();
    }

    interface OnGetReposListener extends ListLoadingListener {
        void onGetRepos(int total_count, List<RepositoryInfo> items, @UpdateType int updateType);
    }

    interface OnGetUserListener extends ListLoadingListener {

        void onGetUser(int total_count, List<UserEntity> items, @UpdateType int updateType);
    }

    class SearchEntity {
        public SearchEntity(@ListType int type) {
            this.type = type;
        }

        public boolean isFlying;
        @ListType
        public int type;
        public String keyWord;
        public Constant.SortType sortType;
        public String language;
        @UpdateType
        public int updateType;
    }
}