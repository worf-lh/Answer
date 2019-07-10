package com.potato.answer.mvp;

import android.content.Context;

/**
 * The root view interface for every mvp view
 *
 * @author lihao
 * @date 2019/7/10 14:04
 * @desc todo
 */
public interface MvpView {

    void showToast(String message);

    Context provideContext();

}
