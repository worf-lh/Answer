package com.potato.answer.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 管理所有Fragment生命周期，如：统计、恢复
 *
 * @author lihao
 * @date 2019/7/10 14:02
 * @desc todo
 */
public class BaseSuperFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //状态恢复
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //状态保存
    }

}
