package com.example.jinhui.gitclub.presentation.presenter;

import com.example.jinhui.gitclub.model.entity.RepositoryInfo;
import com.tellh.nolistadapter.adapter.RecyclerViewAdapter;



/**
 * Created by tlh on 2016/9/1 :)
 */
public interface IRepoListPresenter {
    interface OnGetRepoCallback {
        void onGet(RepositoryInfo repositoryInfo);
    }

    void checkState(int position, RecyclerViewAdapter adapter);

    void starRepo(int position, RecyclerViewAdapter adapter, boolean toggle);

    void watchRepo(int position, RecyclerViewAdapter adapter, boolean toggle);

    void forkRepo(int position, RecyclerViewAdapter adapter);

    void getRepoInfo(String owner, String name, OnGetRepoCallback callback);
}
