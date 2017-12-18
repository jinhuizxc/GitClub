package com.example.jinhui.gitclub.model.net.DataSource;

import com.example.jinhui.gitclub.common.config.Constant;
import com.example.jinhui.gitclub.common.utils.RxJavaUtils;
import com.example.jinhui.gitclub.model.entity.ArsenalRepository;
import com.example.jinhui.gitclub.model.entity.RepositoryInfo;
import com.example.jinhui.gitclub.model.net.service.ArsenalService;
import com.example.jinhui.gitclub.model.net.service.RepositoryService;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;


/**
 * Created by tlh on 2016/10/5 :)
 */

public class ArsenalDataSource {
    private ArsenalService arsenalService;
    private RepositoryService repositoryService;

    public ArsenalDataSource(ArsenalService arsenalService, RepositoryService repositoryService) {
        this.arsenalService = arsenalService;
        this.repositoryService = repositoryService;
    }

    private Observable<List<ArsenalRepository>> getData(int page) {
        return arsenalService.list(page, Constant.PER_PAGE_ARSENAL)
                .compose(RxJavaUtils.<List<ArsenalRepository>>applySchedulers());
    }

    private Observable<RepositoryInfo> getRepository(int page) {
        return getData(page)
                .flatMap(new Func1<List<ArsenalRepository>, Observable<RepositoryInfo>>() {
                    @Override
                    public Observable<RepositoryInfo> call(final List<ArsenalRepository> resultsEntities) {
                        List<Observable<RepositoryInfo>> observableList = new ArrayList<>(Constant.PER_PAGE_ARSENAL);
                        for (ArsenalRepository repo : resultsEntities) {
                            observableList.add(repositoryService.getRepoInfo(repo.getOwner(), repo.getName()));
                        }
                        return Observable.mergeDelayError(observableList).compose(RxJavaUtils.<RepositoryInfo>applySchedulers());
                    }
                });
    }

    public Observable<List<RepositoryInfo>> getRepositories(final int page) {
        final List<RepositoryInfo> repositories = new ArrayList<>(Constant.PER_PAGE_ARSENAL);
        return Observable.create(new Observable.OnSubscribe<List<RepositoryInfo>>() {
            @Override
            public void call(final Subscriber<? super List<RepositoryInfo>> subscriber) {
                getRepository(page)
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
                                subscriber.onNext(repositories);
                            }
                        })
                        .subscribe(new Subscriber<RepositoryInfo>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(RepositoryInfo repositoryInfo) {
                                repositories.add(repositoryInfo);
                            }
                        });
            }
        });
    }


}
