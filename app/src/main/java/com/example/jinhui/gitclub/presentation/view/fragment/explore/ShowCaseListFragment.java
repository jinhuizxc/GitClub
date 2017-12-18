package com.example.jinhui.gitclub.presentation.view.fragment.explore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.model.entity.ShowCase;
import com.example.jinhui.gitclub.presentation.contract.ExploreContract;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.ErrorViewBinder;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.ShowCaseListItemViewBinder;
import com.example.jinhui.gitclub.presentation.view.fragment.ListFragment;
import com.tellh.nolistadapter.adapter.RecyclerViewAdapter;
import com.tellh.nolistadapter.viewbinder.utils.EasyEmptyRecyclerViewBinder;

import java.util.List;

import static com.example.jinhui.gitclub.presentation.contract.ExploreContract.SHOWCASES;


/**
 * Created by tlh on 2016/9/5 :)
 */
public class ShowCaseListFragment extends ListFragment implements ExploreContract.onGetShowcasesListener {
    private ExploreContract.OnListFragmentInteractListener mListener;
    private RecyclerViewAdapter adapter;

    public static ShowCaseListFragment newInstance() {
        return new ShowCaseListFragment();
    }

    @Override
    protected RecyclerView.Adapter getListAdapter() {
        adapter = RecyclerViewAdapter.builder()
                .addItemType(new ShowCaseListItemViewBinder())
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
        mListener.onFetchData(SHOWCASES, 1);
        adapter.hideErrorView(recyclerView);
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
    public void onGet(List<ShowCase> showCases) {
        adapter.refresh(showCases);
        hideLoading();
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }
}
