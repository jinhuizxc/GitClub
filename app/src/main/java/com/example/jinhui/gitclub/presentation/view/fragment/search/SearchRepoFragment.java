package com.example.jinhui.gitclub.presentation.view.fragment.search;

import android.support.v7.widget.RecyclerView;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.model.entity.RepositoryInfo;
import com.example.jinhui.gitclub.presentation.contract.SearchContract;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.ErrorViewBinder;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.LoadMoreFooterViewBinder;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.RepoListItemViewBinder;
import com.example.jinhui.gitclub.presentation.view.fragment.ListFragment;
import com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper;
import com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.UpdateType;
import com.tellh.nolistadapter.adapter.RecyclerViewAdapter;
import com.tellh.nolistadapter.viewbinder.utils.EasyEmptyRecyclerViewBinder;

import java.util.List;


import static com.example.jinhui.gitclub.presentation.contract.SearchContract.REPO;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.LOADING;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.PULL_TO_LOAD_MORE;


public class SearchRepoFragment extends ListFragment
        implements SearchContract.OnGetReposListener, FooterLoadMoreAdapterWrapper.OnReachFooterListener {
    private SearchContract.OnListFragmentInteractListener mListener;
    private FooterLoadMoreAdapterWrapper loadMoreWrapper;

    public static SearchRepoFragment newInstance() {
        return new SearchRepoFragment();
    }

    @Override
    protected RecyclerView.Adapter getListAdapter() {
        assert mListener != null;
        loadMoreWrapper = (FooterLoadMoreAdapterWrapper) RecyclerViewAdapter.builder()
                .addItemType(new RepoListItemViewBinder(mListener.getPresenter()))
                .setLoadMoreFooter(new LoadMoreFooterViewBinder(), recyclerView, this)
                .setErrorView(new ErrorViewBinder(this))
                .setEmptyView(new EasyEmptyRecyclerViewBinder(R.layout.empty_view))
                .build();
        return loadMoreWrapper;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_items_list;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onGetRepos(int total_count, List<RepositoryInfo> items, @UpdateType int updateType) {
        loadMoreWrapper.OnGetData(items, updateType);
    }

    @Override
    public void onToLoadMore(int curPage) {
        mListener.onFetchPage(REPO, curPage + 1);
    }

    void setListFragmentInteractListener(SearchContract.OnListFragmentInteractListener listener) {
        mListener = listener;
    }

    @Override
    public void onRefresh() {
        mListener.onFetchPage(REPO, 1);
        loadMoreWrapper.hideErrorView(recyclerView);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (loadMoreWrapper.getFooterStatus() == LOADING)
            loadMoreWrapper.setFooterStatus(PULL_TO_LOAD_MORE);
    }
}
