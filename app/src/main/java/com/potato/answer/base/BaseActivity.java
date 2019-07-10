package com.potato.answer.base;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.customview.widget.ViewDragHelper;

import com.potato.answer.AppManager;
import com.potato.answer.R;
import com.potato.answer.util.BitmapUtil;

/**
 * 为所有的activity封装功能
 *
 * @author lihao
 * @date 2019/7/10 15:58
 * @desc todo
 */
public abstract class BaseActivity extends BaseSuperActivity {

    /**
     * 窗体第一次获取焦点
     */
    private boolean isFirstFocus = true;
    private Boolean windowIsTranslucent;
    private Drawable mDefaultWindowBackground;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置缓存
        View decorView = getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);

    }

    private Drawable windowBackground = null;

    public Drawable getWindowBackground() {
        if (windowBackground == null) {
            Activity beforeActivity = AppManager.getInstance().beforeActivity(this);
            if (beforeActivity != null) {
                Object tag = beforeActivity.getWindow().getDecorView().getTag();
                if (tag != null && tag instanceof Bitmap) {
                    windowBackground = BitmapUtil.bitmapToDrawable(getResources(), (Bitmap) tag);
                } else {
                    windowBackground = BitmapUtil.bitmapToDrawable(getResources(), getActivityBitmap(beforeActivity));
                }
            }
        }
        return windowBackground;
    }

    public Drawable getDefaultWindowBackground() {
        if (mDefaultWindowBackground == null) {
            int[] attrsArray = {android.R.attr.windowBackground};
            TypedArray typedArray = this.obtainStyledAttributes(attrsArray);
            mDefaultWindowBackground = typedArray.getDrawable(0);
            typedArray.recycle();
        }
        return mDefaultWindowBackground;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFirstFocus) {
            onWindowFocusFirstObtain();
            isFirstFocus = false;
        }
    }

    public static Bitmap getActivityBitmap(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    /**
     * 当窗体第一次获取到焦点会回调该方法
     */
    protected void onWindowFocusFirstObtain() {
    /*    if (isEnableSlideFinish()) {
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Activity beforeActivity = AppManager.getInstance().beforeActivity();
                    if (windowBackground == null) {
                        if (beforeActivity != null) {
                            windowBackground = BitmapUtil.bitmapToDrawable(getResources(), getActivityBitmap(beforeActivity));
                        }
                    }
                }
            }, 1000);
        }*/
    }

    protected boolean getWindowIsTranslucent() {
        if (windowIsTranslucent == null) {
            int[] attrsArray = {android.R.attr.windowIsTranslucent};
            TypedArray typedArray = this.obtainStyledAttributes(attrsArray);
            windowIsTranslucent = typedArray.getBoolean(0, false);
            typedArray.recycle();
        }
        return windowIsTranslucent;
    }

    protected boolean isEnableSlideFinish() {
        return true;
    }

    public void bindOnClickLister(View rootView, View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            View view = rootView.findViewById(id);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void bindOnClickLister(View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void bindOnClickLister(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

}
