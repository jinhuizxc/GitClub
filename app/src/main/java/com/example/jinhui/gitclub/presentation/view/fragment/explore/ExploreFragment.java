package com.example.jinhui.gitclub.presentation.view.fragment.explore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.common.base.LazyFragment;
import com.example.jinhui.gitclub.common.utils.Utils;
import com.example.jinhui.gitclub.di.component.ComponentHolder;
import com.example.jinhui.gitclub.model.entity.RepositoryInfo;
import com.example.jinhui.gitclub.model.entity.ShowCase;
import com.example.jinhui.gitclub.model.entity.ShowCaseInfo;
import com.example.jinhui.gitclub.model.entity.Trending;
import com.example.jinhui.gitclub.presentation.contract.ExploreContract;
import com.example.jinhui.gitclub.presentation.contract.bus.RxBus;
import com.example.jinhui.gitclub.presentation.contract.bus.event.GetShowcaseDetailEvent;
import com.example.jinhui.gitclub.presentation.contract.bus.event.OnBackPressEvent;
import com.example.jinhui.gitclub.presentation.contract.bus.event.OnClickOutsideToHideEvent;
import com.example.jinhui.gitclub.presentation.view.adapter.CommonViewPagerAdapter;
import com.example.jinhui.gitclub.presentation.widget.FabAnimationHelper;
import com.example.jinhui.gitclub.presentation.widget.OnPageChangeListenerAdapter;
import com.example.jinhui.gitclub.presentation.widget.ShowcaseListBottomSheetDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.UpdateType;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;


import static com.example.jinhui.gitclub.presentation.contract.ExploreContract.ARSENAL;
import static com.example.jinhui.gitclub.presentation.contract.ExploreContract.GANK_IO;
import static com.example.jinhui.gitclub.presentation.contract.ExploreContract.SHOWCASES;
import static com.example.jinhui.gitclub.presentation.contract.ExploreContract.TRENDING;
import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.REFRESH;


public class ExploreFragment extends LazyFragment
        implements ExploreContract.View, ExploreContract.OnListFragmentInteractListener {
    @Inject
    ExploreContract.Presenter presenter;
    private ViewPager mViewPager;
    private ShowCaseListFragment showCaseListFragment;
    private TrendingListFragment trendingListFragment;
    private RepositoryInfoListFragment gankDataListFragment;
    private RepositoryInfoListFragment arsenalDataListFragment;
    private FloatingActionButton fabLang;
    private FloatingActionButton fabSince;
    private FloatingActionsMenu fabMenu;
    private ShowcaseListBottomSheetDialog showcaseListDialog;
    private ImageView ivHeader;
    private CollapsingToolbarLayout collapsingLayout;

    private boolean hasInitGankPage = false;
    private boolean hasInitArsenalPage = false;

    public ExploreFragment() {
        // Required empty public constructor
    }

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        presenter.listTrending();
        presenter.listShowCase();
        trendingListFragment.showLoading();
        showCaseListFragment.showLoading();
        addSubscription(RxBus.getDefault().toObservable(GetShowcaseDetailEvent.class)
                .subscribe(new Action1<GetShowcaseDetailEvent>() {
                    @Override
                    public void call(GetShowcaseDetailEvent getShowcaseDetailEvent) {
                        presenter.getShowcaseDetail(getShowcaseDetailEvent.showCase);
                    }
                }));
    }

    private void initDagger() {
        ComponentHolder.getExploreComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_explore;
    }

    @Override
    public void initView() {
        initDagger();

        //find view
        Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) mRootView.findViewById(R.id.tab);
        collapsingLayout = (CollapsingToolbarLayout) mRootView.findViewById(R.id.collapsing_toolbar);
        fabLang = (FloatingActionButton) mRootView.findViewById(R.id.fab_language);
        fabSince = (FloatingActionButton) mRootView.findViewById(R.id.fab_since);
        fabMenu = (FloatingActionsMenu) mRootView.findViewById(R.id.fab);
        ivHeader = (ImageView) mRootView.findViewById(R.id.iv_header);

//        init toolbar
        setHasOptionsMenu(true);
        toolbar.setTitle(R.string.explore);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        collapsingLayout.setTitle(Utils.getString(R.string.explore));

        //init viewpager and tab
        setupViewPager();
        tabLayout.setupWithViewPager(mViewPager);

        //fab
        initFab();
    }

    private void initFab() {
        fabLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getDialogLang().show();
                fabMenu.collapse();
            }
        });
        fabSince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getDialogSince().show();
                fabMenu.collapse();
            }
        });

        addSubscription(RxBus.getDefault().toObservable(OnBackPressEvent.class).subscribe(new Action1<OnBackPressEvent>() {
            @Override
            public void call(OnBackPressEvent event) {
                if (fabMenu == null || !fabMenu.isExpanded())
                    return;
                fabMenu.collapse();
                event.hasConsume = true;
            }
        }));

        addSubscription(RxBus.getDefault().toObservable(OnClickOutsideToHideEvent.class).subscribe(new Action1<OnClickOutsideToHideEvent>() {
            @Override
            public void call(OnClickOutsideToHideEvent event) {
                if (fabMenu == null || !fabMenu.isExpanded())
                    return;
                fabMenu.collapse();
                event.consume = true;
            }
        }));
    }

    private void setupViewPager() {
        CommonViewPagerAdapter viewPagerAdapter = new CommonViewPagerAdapter(getFragmentManager());
        trendingListFragment = TrendingListFragment.newInstance();
        trendingListFragment.setListFragmentInteractListener(this);
        viewPagerAdapter.addFragment("Trending", trendingListFragment);
        showCaseListFragment = ShowCaseListFragment.newInstance();
        showCaseListFragment.setListFragmentInteractListener(this);
        viewPagerAdapter.addFragment("ShowCases", showCaseListFragment);
        gankDataListFragment = RepositoryInfoListFragment.newInstance(GANK_IO, 5);
        gankDataListFragment.setListFragmentInteractListener(this);
        viewPagerAdapter.addFragment("Gank.IO", gankDataListFragment);
        arsenalDataListFragment = RepositoryInfoListFragment.newInstance(ARSENAL, 5);
        arsenalDataListFragment.setListFragmentInteractListener(this);
        viewPagerAdapter.addFragment("Arsenal", arsenalDataListFragment);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    collapsingLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_purple));
                    Utils.setImageWithFade(ivHeader, R.drawable.train);
                    FabAnimationHelper.show(fabMenu, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            fabMenu.setVisibility(View.VISIBLE);
                        }
                    });
                } else if (position == 1) {
                    collapsingLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange_red));
                    Utils.setImageWithFade(ivHeader, R.drawable.sun);
                    FabAnimationHelper.hide(fabMenu, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            fabMenu.setVisibility(View.INVISIBLE);
                        }
                    });
                } else if (position == 2) {
                    if (!hasInitGankPage) {
                        presenter.listGankData(1);
                        gankDataListFragment.showLoading();
                        hasInitGankPage = true;
                    }
                    collapsingLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_blue));
                    Utils.setImageWithFade(ivHeader, R.drawable.sky);
                    FabAnimationHelper.hide(fabMenu, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            fabMenu.setVisibility(View.INVISIBLE);
                        }
                    });
                } else if (position == 3) {
                    if (!hasInitArsenalPage) {
                        presenter.listArsenalData(1);
                        arsenalDataListFragment.showLoading();
                        hasInitArsenalPage = true;
                    }
                    collapsingLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple));
                    Utils.setImageWithFade(ivHeader, R.drawable.arsenal);
                    FabAnimationHelper.hide(fabMenu, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            fabMenu.setVisibility(View.INVISIBLE);
                        }
                    });
                }

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.detachView();
        showCaseListFragment = null;
        trendingListFragment = null;
        gankDataListFragment = null;
        arsenalDataListFragment = null;
    }

    @Override
    public void showOnError(String msg, @ExploreContract.ListType int type) {
        showOnError(msg);
        switch (type) {
            case GANK_IO:
                gankDataListFragment.hideLoading();
                break;
            case SHOWCASES:
                showCaseListFragment.hideLoading();
                showCaseListFragment.showErrorView();
                break;
            case TRENDING:
                trendingListFragment.hideLoading();
                trendingListFragment.showErrorView();
                break;
            case ARSENAL:
                arsenalDataListFragment.hideLoading();
                break;
        }
    }

    @Override
    public void onGetTrending(List<Trending> trendings) {
        trendingListFragment.onGet(trendings);
    }

    @Override
    public void onGetShowcases(List<ShowCase> showCases) {
        showCaseListFragment.onGet(showCases);
    }

    @Override
    public void onGetGankData(List<RepositoryInfo> repositoryList, @UpdateType int updateType) {
        gankDataListFragment.onGet(repositoryList, updateType);
        if (updateType == REFRESH)
            gankDataListFragment.hideLoading();
    }

    @Override
    public void onGetShowcasesDetail(ShowCaseInfo showCaseInfo) {
        if (showcaseListDialog == null)
            showcaseListDialog = new ShowcaseListBottomSheetDialog(getContext(), presenter);
        showcaseListDialog.refreshAndShow(showCaseInfo);
    }

    @Override
    public void onGetArsenalData(List<RepositoryInfo> repositoryList, int updateType) {
        arsenalDataListFragment.onGet(repositoryList, updateType);
        if (updateType == REFRESH)
            arsenalDataListFragment.hideLoading();
    }

    @Override
    public void onFetchData(@ExploreContract.ListType int type, int page) {
        switch (type) {
            case SHOWCASES:
                presenter.listShowCase();
                break;
            case TRENDING:
                presenter.listTrending();
                break;
            case GANK_IO:
                presenter.listGankData(page);
                break;
            case ARSENAL:
                presenter.listArsenalData(page);
                break;
        }
    }

    @Override
    public ExploreContract.Presenter getPresenter() {
        return presenter;
    }

    @Override //the first page will not be lazy loaded.
    protected boolean shouldLazyLoad() {
        return false;
    }

    @Override
    public void showOnError(@UpdateType int updateType, String msg) {
        showOnError(msg);
//        gankDataListFragment.showOnError(msg, updateType);
    }
}
