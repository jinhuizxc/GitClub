package com.example.jinhui.gitclub.common.config;


import com.example.jinhui.gitclub.BuildConfig;

public class GithubConfig {

    // client id/secret
    public static final String CLIENT_ID = BuildConfig.GITHUB_CLIENT_ID;
    public static final String CLIENT_SECRET = BuildConfig.GITHUB_CLIENT_SECRET;

    // scopes
    public static final String[] SCOPES = {"user", "repo"};

    public static final String NOTE = "GitClub";
}
