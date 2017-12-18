package com.example.jinhui.gitclub.presentation.contract.bus;

import com.example.jinhui.gitclub.model.entity.ShowCase;
import com.example.jinhui.gitclub.presentation.contract.bus.event.GetShowcaseDetailEvent;
import com.example.jinhui.gitclub.presentation.contract.bus.event.LaunchActivityEvent;
import com.example.jinhui.gitclub.presentation.contract.bus.event.OnBackPressEvent;
import com.example.jinhui.gitclub.presentation.contract.bus.event.OnClickOutsideToHideEvent;
import com.example.jinhui.gitclub.presentation.contract.bus.event.QuickReturnEvent;

import java.util.Map;

/**
 * Created by tlh on 2016/9/3 :)
 */
public class RxBusPostman {
    public static void postQuickReturnEvent(boolean show) {
        RxBus.getDefault().post(new QuickReturnEvent(!show));
    }

    public static void postOnBackPressEvent(OnBackPressEvent event) {
        RxBus.getDefault().post(event);
    }

    public static void postOnClickScreenEvent(OnClickOutsideToHideEvent event) {
        RxBus.getDefault().post(event);
    }

    public static void postGetShowcaseDetailEvent(ShowCase showCase) {
        RxBus.getDefault().post(new GetShowcaseDetailEvent(showCase));
    }

    public static void postLaunchActivityEvent(Map<String, String> params, @LaunchActivityEvent.TargetActivity int targetActivity) {
        RxBus.getDefault().post(new LaunchActivityEvent(params, targetActivity));
    }
}
