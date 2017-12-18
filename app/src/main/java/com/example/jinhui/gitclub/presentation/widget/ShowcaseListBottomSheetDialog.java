package com.example.jinhui.gitclub.presentation.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.model.entity.ShowCaseInfo;
import com.example.jinhui.gitclub.presentation.presenter.IRepoListPresenter;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.RepoListItemViewBinder;
import com.example.jinhui.gitclub.presentation.view.adapter.viewbinder.ShowCaseHeaderViewBinder;
import com.tellh.nolistadapter.adapter.HeaderAndFooterAdapterWrapper;
import com.tellh.nolistadapter.adapter.RecyclerViewAdapter;



/**
 * Created by tlh on 2016/9/8 :)
 */
public class ShowcaseListBottomSheetDialog extends BottomSheetDialog {
    private IRepoListPresenter presenter;
    private HeaderAndFooterAdapterWrapper adapterWrapper;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private RecyclerView recyclerView;
    private ShowCaseHeaderViewBinder viewBinder;

    public ShowcaseListBottomSheetDialog(@NonNull Context context, IRepoListPresenter presenter) {
        super(context);
        this.presenter = presenter;
        init();
    }

    private void init() {
        //init recycler view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_list, null, false);
        setContentView(view);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewBinder = new ShowCaseHeaderViewBinder();
        adapterWrapper = (HeaderAndFooterAdapterWrapper) RecyclerViewAdapter.builder()
                .addItemType(new RepoListItemViewBinder(presenter))
                .addHeader(viewBinder)
                .build();
        recyclerView.setAdapter(adapterWrapper);

        //set bottom sheet behaviour
        View sheetView = getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        assert sheetView != null;
        bottomSheetBehavior = BottomSheetBehavior.from(sheetView);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    public void refreshAndShow(ShowCaseInfo showCaseInfo) {
        viewBinder.setShowcase(showCaseInfo);
        adapterWrapper.refresh(showCaseInfo.getRepositories());
        show();
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            recyclerView.scrollToPosition(0);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }
        super.onBackPressed();
    }
}
