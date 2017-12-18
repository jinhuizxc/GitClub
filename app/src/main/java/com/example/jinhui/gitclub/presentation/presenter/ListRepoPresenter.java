package com.example.jinhui.gitclub.presentation.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.common.base.BasePresenter;
import com.example.jinhui.gitclub.common.base.BaseView;
import com.example.jinhui.gitclub.common.base.DefaultSubscriber;
import com.example.jinhui.gitclub.common.config.Constant;
import com.example.jinhui.gitclub.common.utils.Utils;
import com.example.jinhui.gitclub.model.entity.RepositoryInfo;
import com.example.jinhui.gitclub.model.net.DataSource.RepositoryDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.UserDataSource;
import com.example.jinhui.gitclub.presentation.contract.ShowError;
import com.tellh.nolistadapter.adapter.RecyclerViewAdapter;

import java.util.List;

import rx.functions.Action0;

import static com.tellh.nolistadapter.adapter.FooterLoadMoreAdapterWrapper.UpdateType;

/**
 * Created by tlh on 2016/9/11 :)
 */
public class ListRepoPresenter extends BasePresenter<ListRepoPresenter.ListRepoView> implements IRepoListPresenter {
    private final UserDataSource mUserDataSource;
    private final IRepoListPresenter repoListPresenter;

    private String user;

    private ListStarredRepoRequest listStarredRepoRequest;
    private DialogManager dialogManager;
    private boolean isFlying;


    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public void checkState(int position, RecyclerViewAdapter adapter) {
        repoListPresenter.checkState(position, adapter);
    }

    @Override
    public void starRepo(int position, RecyclerViewAdapter adapter, boolean toggle) {
        repoListPresenter.starRepo(position, adapter, toggle);
    }

    @Override
    public void watchRepo(int position, RecyclerViewAdapter adapter, boolean toggle) {
        repoListPresenter.watchRepo(position, adapter, toggle);
    }

    @Override
    public void forkRepo(int position, RecyclerViewAdapter adapter) {
        repoListPresenter.forkRepo(position, adapter);
    }

    @Override
    public void getRepoInfo(String owner, String name, OnGetRepoCallback callback) {
        repoListPresenter.getRepoInfo(owner, name, callback);
    }

    private class ListStarredRepoRequest {
        int page;
        Constant.SortType.SortType_Repo sortType;

        public ListStarredRepoRequest(int page, Constant.SortType.SortType_Repo sortType) {
            this.page = page;
            this.sortType = sortType;
        }
    }

    public ListRepoPresenter(UserDataSource userDataSource, RepositoryDataSource repositoryDataSource) {
        this.mUserDataSource = userDataSource;
        repoListPresenter = new RepoListPresenter(this, repositoryDataSource);
    }

    public void listStarredRepo(final int page) {
        if (isFlying) {
            getView().showOnError(getUpdateType(page), Utils.getString(R.string.reqest_flying));
            return;
        }
        if (listStarredRepoRequest == null)
            listStarredRepoRequest = new ListStarredRepoRequest(1, Constant.SortType.SortType_Repo.CREATED);
        isFlying = true;
        listStarredRepoRequest.page = page;
        addSubscription(
                mUserDataSource.listStarredRepo(user, listStarredRepoRequest.sortType,
                        listStarredRepoRequest.page)
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
                                isFlying = false;
                            }
                        })
                        .subscribe(new DefaultSubscriber<List<RepositoryInfo>>() {
                            @Override
                            public void onNext(List<RepositoryInfo> repositoryInfos) {
                                getView().onGetRepoList(repositoryInfos, getUpdateType(page));
                                getView().showOnSuccess();
                            }

                            @Override
                            protected void onError(String errorStr) {
                                getView().showOnError(getUpdateType(page), errorStr);
                            }
                        })
        );
    }

    public void listWatchingRepo(final int page) {
        if (user == null) {
            return;
        }
        if (isFlying) {
            getView().showOnError(getUpdateType(page), Utils.getString(R.string.reqest_flying));
            return;
        }
        isFlying = true;
        addSubscription(
                mUserDataSource.listWatchingRepo(user, page)
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
                                isFlying = false;
                            }
                        })
                        .subscribe(new DefaultSubscriber<List<RepositoryInfo>>() {
                            @Override
                            public void onNext(List<RepositoryInfo> repositoryInfos) {
                                getView().onGetRepoList(repositoryInfos, getUpdateType(page));
                                getView().showOnSuccess();
                            }

                            @Override
                            protected void onError(String errorStr) {
                                getView().showOnError(getUpdateType(page), errorStr);
                            }
                        })
        );
    }

    public void listOwnRepo(final int page) {
        if (isFlying) {
            getView().showOnError(getUpdateType(page), Utils.getString(R.string.reqest_flying));
            return;
        }
        isFlying = true;
        addSubscription(
                mUserDataSource.listOwnRepo(user, page)
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
                                isFlying = false;
                            }
                        })
                        .subscribe(new DefaultSubscriber<List<RepositoryInfo>>() {
                            @Override
                            public void onNext(List<RepositoryInfo> repositoryInfos) {
                                getView().onGetRepoList(repositoryInfos, getUpdateType(page));
                                getView().showOnSuccess();
                            }

                            @Override
                            protected void onError(String errorStr) {
                                getView().showOnError(getUpdateType(page), errorStr);
                            }
                        })
        );
    }

    @Override
    public void attachView(ListRepoView view) {
        super.attachView(view);
        dialogManager = new DialogManager();
    }

    public MaterialDialog getDialogSortRepo() {
        return dialogManager.dialogSortRepo;
    }

    private class DialogManager {
        private MaterialDialog dialogSortRepo;
        Context viewContext = getView().getViewContext();

        public DialogManager() {
            dialogSortRepo = new MaterialDialog.Builder(viewContext)
                    .title(R.string.title_sort_repo)
                    .items(Constant.SortType.SortType_Repo.getDisplayStringListForStarredRepo())
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            listStarredRepoRequest.sortType = (Constant.SortType.SortType_Repo) Constant.SortType.SortType_Repo.lookup(text.toString());
//                            listStarredRepo(1);
                            getView().initData(null);
                            return true;
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                            materialDialog.dismiss();
                        }
                    })
                    .positiveText("OK")
                    .negativeText("Cancel")
                    .negativeColorRes(R.color.red)
                    .build();
            dialogSortRepo.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    dialogSortRepo.setSelectedIndex(listStarredRepoRequest.sortType.ordinal());
                }
            });
        }
    }

    public interface ListRepoView extends BaseView, ShowError {
        void onGetRepoList(List<RepositoryInfo> list, @UpdateType int updateType);
    }
}
