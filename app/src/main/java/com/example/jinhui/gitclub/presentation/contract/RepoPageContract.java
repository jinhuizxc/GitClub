package com.example.jinhui.gitclub.presentation.contract;


import com.example.jinhui.gitclub.common.base.BaseView;
import com.example.jinhui.gitclub.common.base.MvpPresenter;
import com.example.jinhui.gitclub.model.entity.RepositoryInfo;

public interface RepoPageContract {

    interface View extends BaseView {
        void onGetRepositoryInfo(RepositoryInfo repositoryInfo);

        void onCheckStarred(Boolean result);

        void onCheckWatch(Boolean result);

        void onGetReadMe(String html_url);
    }

    interface Presenter extends MvpPresenter<View> {

        void getRepoInfo(String owner, String repo);

        void toFork(String owner, String repo);

        void toStar(String owner, String repo, boolean checked);

        void toWatch(String owner, String repo, boolean checked);

        void checkStarred(String owner, String repo);

        void checkWatch(String owner, String repo);

        void getReadMe(String owner, String repo);
    }
}