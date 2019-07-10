package com.potato.answer.mvp;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import com.potato.answer.AppException;

import java.lang.ref.WeakReference;

/**
 * @author lihao
 * @date 2019/7/10 14:12
 * @desc todo
 */
public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private WeakReference<V> viewRef;

    /**
     * Presenter与View建立连接
     */
    @UiThread
    @Override
    public void start() {

    }

    @UiThread
    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
    }

    @UiThread
    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    /**
     * 每次调用业务请求的时候 即：getView().showXxx();时
     * 请先调用方法检查是否与View建立连接，没有则可能会空指针异常
     */
    @UiThread
    public final boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @UiThread
    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @UiThread
    @Override
    public void destroy() {
        if (isViewAttached()) {
            detachView();
        }
    }

    protected String handleException(Throwable throwable) {
        return AppException.getExceptionMessage(throwable);
    }

}
