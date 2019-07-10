package com.potato.answer;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.potato.answer.presentation.CrashActivity;
import com.potato.answer.util.DensityUtil;

/**
 * app路由，界面跳转帮助类，所有的界面跳转通过此类进行跳转,包括组件交互
 *
 * @author lihao
 * @date 2019/7/10 14:28
 * @desc todo
 */
public class AppRouter {

    /**
     * 获取全局加载dialog
     */
    public static Dialog getLoadingDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.AlertDialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        dialog.setContentView(view, new ViewGroup.LayoutParams(DensityUtil.dp2px(context, 96), DensityUtil.dp2px(context, 96)));
        return dialog;
    }

    /**
     * 显示崩溃提示
     * @param context
     * @param message
     * @param errorInfo
     */
    public static void showCrashActivity(Context context, String message, final String errorInfo) {
        context.startActivity(CrashActivity.newIntent(context, message, errorInfo));
    }

}
