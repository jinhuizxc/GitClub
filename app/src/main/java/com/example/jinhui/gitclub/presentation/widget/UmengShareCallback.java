package com.example.jinhui.gitclub.presentation.widget;

import com.example.jinhui.gitclub.R;
import com.example.jinhui.gitclub.common.utils.Utils;
import com.example.jinhui.gitclub.common.wrapper.Note;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;



/**
 * Created by tlh on 2016/9/26 :)
 */

public class UmengShareCallback implements UMShareListener {
    @Override
    public void onResult(SHARE_MEDIA platform) {
        Note.show(Utils.getString(R.string.success_share) + platform);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Note.show(Utils.getString(R.string.error_share) + share_media);
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Note.show(Utils.getString(R.string.cancel_share));
    }
}
