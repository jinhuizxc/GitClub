package com.example.jinhui.gitclub.presentation.contract;

import com.example.jinhui.gitclub.common.base.BaseView;
import com.example.jinhui.gitclub.common.base.MvpPresenter;
import com.example.jinhui.gitclub.model.entity.Branch;

import java.util.List;


import tellh.com.recyclertreeview_lib.TreeNode;

public interface RepoSourceContract {

    interface View extends BaseView {
        void onGetBranchList(List<Branch> branches);

        void onGetSourceTree(List<TreeNode> treeNodes);

        void onGetReadMe(String html_url);
    }

    interface Presenter extends MvpPresenter<View> {

        void initSourceTree(String owner, String repo);

        void getSourceTree(String owner, String repo, Branch branch);

        void getReadMe(String owner, String repo);
    }
}