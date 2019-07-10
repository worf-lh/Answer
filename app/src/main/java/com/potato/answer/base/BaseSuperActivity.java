package com.potato.answer.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * 管理所有Activity生命周期，如：统计、恢复
 *
 * @author lihao
 * @date 2019/7/10 10:54
 * @desc todo
 */
public abstract class BaseSuperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    public abstract int getLayoutId();

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //状态恢复
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //状态保存
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCountingPage()) {
            countingPageStart();
        }
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isCountingPage()) {
            countingPageEnd();
        }
        MobclickAgent.onPause(this);
    }

    protected void countingPageStart() {
        MobclickAgent.onPageStart(this.getClass().getName());
    }

    protected void countingPageEnd() {
        MobclickAgent.onPageEnd(this.getClass().getName());
    }

    /**
     * 子类通过重新此方法,选择是否统计Activity的跳转路径,
     * 当Activity由Framgent组成,应该统计Framgent的跳转路径而不是统计Activity
     *
     * @return
     */
    protected boolean isCountingPage() {
        return true;
    }

}
