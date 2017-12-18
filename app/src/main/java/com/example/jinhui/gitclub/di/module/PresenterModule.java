package com.example.jinhui.gitclub.di.module;

import android.content.Context;

import com.example.jinhui.gitclub.di.DiView;
import com.example.jinhui.gitclub.model.net.DataSource.ArsenalDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.ExploreDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.GankDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.RepositoryDataSource;
import com.example.jinhui.gitclub.model.net.DataSource.UserDataSource;
import com.example.jinhui.gitclub.presentation.contract.ExploreContract;
import com.example.jinhui.gitclub.presentation.contract.LoginContract;
import com.example.jinhui.gitclub.presentation.contract.NewsContract;
import com.example.jinhui.gitclub.presentation.contract.PersonalPageContract;
import com.example.jinhui.gitclub.presentation.contract.RepoPageContract;
import com.example.jinhui.gitclub.presentation.contract.RepoSourceContract;
import com.example.jinhui.gitclub.presentation.contract.SearchContract;
import com.example.jinhui.gitclub.presentation.presenter.ExplorePresenter;
import com.example.jinhui.gitclub.presentation.presenter.ListRepoPresenter;
import com.example.jinhui.gitclub.presentation.presenter.ListUserPresenter;
import com.example.jinhui.gitclub.presentation.presenter.LoginPresenter;
import com.example.jinhui.gitclub.presentation.presenter.NewsPresenter;
import com.example.jinhui.gitclub.presentation.presenter.PersonalPagePresenter;
import com.example.jinhui.gitclub.presentation.presenter.RepoPagePresenter;
import com.example.jinhui.gitclub.presentation.presenter.RepoSourcePresenter;
import com.example.jinhui.gitclub.presentation.presenter.SearchPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class PresenterModule {
    @Provides
    public LoginContract.Presenter provideLoginPresenter(UserDataSource dataSource) {
        return new LoginPresenter(dataSource);
    }

    @Provides
    @DiView
    public SearchContract.Presenter provideSearchPresenter(RepositoryDataSource repositoryDataSource, UserDataSource userDataSource) {
        return new SearchPresenter(repositoryDataSource, userDataSource);
    }

    @Provides
    @DiView
    public ExploreContract.Presenter provideExplorePresenter(ExploreDataSource exploreDataSource, RepositoryDataSource repositoryDataSource,
                                                             GankDataSource gankDataSource, ArsenalDataSource arsenalDataSource) {
        return new ExplorePresenter(exploreDataSource, repositoryDataSource, gankDataSource, arsenalDataSource);
    }

    @Provides
    public NewsContract.Presenter provideNewsPresenter(UserDataSource userDataSource, Context context) {
        return new NewsPresenter(userDataSource, context);
    }

    @Provides
    public PersonalPageContract.Presenter provideHomePagePresenter(UserDataSource userDataSource, Context ctx) {
        return new PersonalPagePresenter(userDataSource, ctx);
    }

    @Provides
    public ListRepoPresenter provideListRepoPresenter(UserDataSource userDataSource, RepositoryDataSource repositoryDataSource) {
        return new ListRepoPresenter(userDataSource, repositoryDataSource);
    }

    @Provides
    public ListUserPresenter provideListUserPresenter(RepositoryDataSource repositoryDataSource, UserDataSource userDataSource) {
        return new ListUserPresenter(repositoryDataSource, userDataSource);
    }

    @Provides
    public RepoPageContract.Presenter provideRepoPagePresenter(RepositoryDataSource repositoryDataSource) {
        return new RepoPagePresenter(repositoryDataSource);
    }

    @Provides
    public RepoSourceContract.Presenter provideRepoSourcePresenter(RepositoryDataSource repositoryDataSource) {
        return new RepoSourcePresenter(repositoryDataSource);
    }
}