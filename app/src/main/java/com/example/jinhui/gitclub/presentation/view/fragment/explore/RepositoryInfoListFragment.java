package com.example.jinhui.gitclub.presentation.view.fragment.explore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.common.utils.Utils;
import com.example.jinhui.gitclub.model.entity.RepositoryInfo;
import com.example.jinhui.gitclub.presentation.contract.ExploreContract;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.ErrorViewBinder;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.LoadMoreFooterViewBinder;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.RepoListItemViewBinder;
import com.example.jinhui.gitclub.presentation.view.fragment.ListFragment;
import com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper;
import com.tellh.nolistadapter.adapter.RecyclerViewAdapter;
import com.tellh.nolistadapter.viewbinder.utils.EasyEmptyRecyclerViewBinder;

import java.util.List;



import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.OnReachFooterListener;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.PULL_TO_LOAD_MORE;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.REFRESH;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.UpdateType;


/**
 * Created by tlh on 2016/9/5 :)
 * TO list Repository in Github, but the data source is Gank or Arsenal Android.
 */
public class RepositoryInfoListFragment extends ListFragment implements ExploreContract.OnGetGankDataListener,
        OnReachFooterListener {
    private ExploreContract.OnListFragmentInteractListener mListener;
    private FooterLoadMoreAdapterWrapper adapter;
    private int listType;
    private int pageSize;

    public static RepositoryInfoListFragment newInstance(@ExploreContract.ListType int listType,
                                                         int pageSize) {
        Bundle args = new Bundle();
        args.putInt("ListType", listType);
        args.putInt("PageSize", pageSize);
        RepositoryInfoListFragment fragment = new RepositoryInfoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listType = getArguments().getInt("ListType");
        pageSize = getArguments().getInt("PageSize");
    }

    @Override
    protected RecyclerView.Adapter getListAdapter() {
        adapter = (FooterLoadMoreAdapterWrapper) RecyclerViewAdapter.builder()
                .addItemType(new RepoListItemViewBinder(mListener.getPresenter()))
                .setLoadMoreFooter(new LoadMoreFooterViewBinder(pageSize), recyclerView, this)
                .setErrorView(new ErrorViewBinder(this))
                .setEmptyView(new EasyEmptyRecyclerViewBinder(R.layout.empty_view))
                .build();
        return adapter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_items_list;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mListener.onFetchData(listType, 1);
    }

    void setListFragmentInteractListener(ExploreContract.OnListFragmentInteractListener listener) {
        mListener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onGet(List<RepositoryInfo> repositoryList, @UpdateType int updateType) {
        adapter.OnGetData(repositoryList, updateType);
        hideLoading();
    }

    @Override
    public void onToLoadMore(int curPage) {
        mListener.onFetchData(listType, curPage + 1);
    }

    public void showOnError(String msg, @UpdateType int updateType) {
        if (updateType == REFRESH)
            showErrorView();
        else
            adapter.setFooterStatus(PULL_TO_LOAD_MORE);

        if (updateType == REFRESH && !msg.equals(Utils.getString(R.string.reqest_flying))) {
            adapter.showErrorView(recyclerView);
        }
    }

}
