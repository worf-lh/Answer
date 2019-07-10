package com.potato.answer;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.multidex.MultiDexApplication;

import com.potato.answer.pref.C;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * @author lihao
 * @date 2019/7/10 15:50
 * @desc todo
 */
public class AppContext extends MultiDexApplication {

    private static AppContext sContext = null;

    public static long sStartTime = System.currentTimeMillis();

    public static int H, W;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        //初始化全局异常捕获
        Thread.setDefaultUncaughtExceptionHandler(new AppException(this));
        //初始化内存泄漏检测器
        LeakCanary.install(this);
        //初始化AppManager
        AppManager.init(this);
        initUmeng(this);
        getScreen(this);
    }

    private void initUmeng(Context context) {
        UMConfigure.init(this, C.UMENG_APP_KEY, C.UMENG_APP_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, null);
        // 禁止默认的页面统计方式，这样将不会再自动统计Activity
        MobclickAgent.openActivityDurationTrack(false);
    }

    public static AppContext context() {
        return sContext;
    }

    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H = dm.heightPixels;
        W = dm.widthPixels;
    }

}
