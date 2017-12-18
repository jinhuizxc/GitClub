package com.example.jinhui.gitclub.presentation.view.fragment.news;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.common.base.LazyFragment;
import com.example.jinhui.gitclub.common.config.ExtraKey;
import com.example.jinhui.gitclub.common.utils.Utils;
import com.example.jinhui.gitclub.di.component.ComponentHolder;
import com.example.jinhui.gitclub.model.entity.Event;
import com.example.jinhui.gitclub.presentation.contract.NewsContract;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.ErrorViewBinder;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.LoadMoreFooterViewBinder;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.NewsListItemViewBinder;
import com.example.jinhui.gitclub.presentation.view.fragment.login.LoginFragment;
import com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper;
import com.tellh.nolistadapter.adapter.RecyclerViewAdapter;
import com.tellh.nolistadapter.viewbinder.utils.EasyEmptyRecyclerViewBinder;

import java.util.List;

import javax.inject.Inject;



import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.LOADING;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.OnReachFooterListener;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.PULL_TO_LOAD_MORE;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.REFRESH;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.UpdateType;

public class NewsFragment extends LazyFragment
        implements NewsContract.View, SwipeRefreshLayout.OnRefreshListener,
        LoginFragment.LoginCallback, OnReachFooterListener, ErrorViewBinder.OnReLoadCallback {
    @Inject
    NewsContract.Presenter presenter;
    private FooterLoadMoreAdapterWrapper loadMoreWrapper;
    private SwipeRefreshLayout refreshLayout;

    private LoginFragment loginFragment;
    private RecyclerView recyclerView;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        refreshLayout.setRefreshing(true);
        presenter.listNews(1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_news;
    }

    @Override
    public void initView() {
        initDagger();

        //find view
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.list);
        refreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refreshLayout);

        //swipe refresh layout
        refreshLayout.setProgressViewOffset(false, -100, 230);
        refreshLayout.setColorSchemeResources(R.color.blue, R.color.brown, R.color.purple, R.color.green);
        refreshLayout.setOnRefreshListener(this);

        //adapter
        loadMoreWrapper = (FooterLoadMoreAdapterWrapper) RecyclerViewAdapter.builder()
                .addItemType(new NewsListItemViewBinder())
                .setLoadMoreFooter(new LoadMoreFooterViewBinder(), recyclerView, this)
                .setErrorView(new ErrorViewBinder(this))
                .setEmptyView(new EasyEmptyRecyclerViewBinder(R.layout.empty_view))
                .build();

        //recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(loadMoreWrapper);
    }

    private void initDagger() {
        ComponentHolder.getNewsComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void OnGetNews(List<Event> newsList, @UpdateType int updateType) {
        loadMoreWrapper.OnGetData(newsList, updateType);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.listNews(1);
        loadMoreWrapper.hideErrorView(recyclerView);
    }

    @Override
    public void onToLoadMore(int curPage) {
        loadMoreWrapper.setFooterStatus(LOADING);
        presenter.listNews(curPage + 1);
    }

    @Override
    public void showOnError(@UpdateType int updateType, String s) {
        showOnError(s);
        if (updateType == REFRESH)
            refreshLayout.setRefreshing(false);
        else
            loadMoreWrapper.setFooterStatus(PULL_TO_LOAD_MORE);

        if (updateType == REFRESH && !s.equals(Utils.getString(R.string.reqest_flying))) {
            loadMoreWrapper.showErrorView(recyclerView);
        }

    }


    @Override
    public void showLoginDialog() {
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            loginFragment.setCallback(this);
        }
        if (loginFragment.getDialog() == null)
            loginFragment.show(getFragmentManager(), ExtraKey.TAG_LOGIN_FRAGMENT);
        else loginFragment.getDialog().show();
    }

    @Override
    public void onSuccessToLogin() {
        //dismiss the login dialog
        loginFragment.setDismissable(true);
        loginFragment.dismiss();
        //hide error view
        loadMoreWrapper.hideErrorView(recyclerView);
        //load data
        refreshLayout.setRefreshing(true);
        presenter.listNews(1);
    }

    @Override
    public void onDismissLogin() {
        loginFragment = null;
    }

    @Override //Click ErrorView to reload data.
    public void reload() {
        refreshLayout.setRefreshing(true);
        loadMoreWrapper.hideErrorView(recyclerView);
        presenter.listNews(1);
    }
}
