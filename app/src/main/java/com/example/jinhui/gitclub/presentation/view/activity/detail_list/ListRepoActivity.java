package com.example.jinhui.gitclub.presentation.view.activity.detail_list;

import com.example.jinhui.gitclub.di.component.ComponentHolder;
import com.example.jinhui.gitclub.model.entity.RepositoryInfo;
import com.example.jinhui.gitclub.presentation.presenter.ListRepoPresenter;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.RepoListItemViewBinder;
import com.tellh.nolistadapter.viewbinder.base.RecyclerViewBinder;

import java.util.List;

import javax.inject.Inject;



import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.UpdateType;

/**
 * Created by tlh on 2016/9/16 :)
 */
public abstract class ListRepoActivity extends BaseListActivity
        implements ListRepoPresenter.ListRepoView {
    @Inject
    ListRepoPresenter presenter;

    @Override
    protected RecyclerViewBinder getListItemViewBinder() {
        return new RepoListItemViewBinder(presenter);
    }

    @Override
    protected void initDagger() {
        ComponentHolder.getListItemComponent().inject(this);
        presenter.attachView(this);
        presenter.setUser(user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
    }

    @Override
    public void onGetRepoList(List<RepositoryInfo> list, @UpdateType int updateType) {
        loadMoreWrapper.OnGetData(list, updateType);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        loadMoreWrapper.hideErrorView(recyclerView);
    }
}
