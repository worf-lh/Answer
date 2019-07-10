package com.potato.answer.presentation;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.potato.answer.AppManager;
import com.potato.answer.BuildConfig;
import com.potato.answer.util.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 发生异常但出的崩溃提示界面
 *
 * @author lihao
 * @date 2019/7/10 15:33
 * @desc todo
 */
public class CrashActivity extends AppCompatActivity {


    private static final String EXTRA_MESSAGE = "extra_message";

    private static final String EXTRA_ERROR_INFO = "extra_error_info";

    public static Intent newIntent(Context context, String message, String errorInfo) {
        Intent intent = new Intent(context, CrashActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_ERROR_INFO, errorInfo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private String errorInfo;
    /**
     * 窗体是第一次获取焦点
     */
    private boolean isFirstFocus = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFirstFocus) {
            onWindowFocusFirstObtain();
            isFirstFocus = false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        errorInfo = getIntent().getStringExtra(EXTRA_ERROR_INFO);
        /*  if (errorInfo != null && errorInfo.length() > 0) {
            //通过友盟反馈错误信息
            //MobclickAgent.reportError(AppContext.context(), errorInfo);
        }*/
    }

    protected void onWindowFocusFirstObtain() {
        if (BuildConfig.DEBUG) {
            getCrashDialog(getIntent().getStringExtra(EXTRA_MESSAGE), errorInfo).show();
        } else {
            finish();
        }
    }

    public Dialog getCrashDialog(String message, final String errorInfo) {
        System.out.print(message + ";" + errorInfo);
        return new AlertDialog.Builder(this)
                .setTitle("程序遇到错误，已崩溃")
                .setMessage("错误信息：" + message)
                .setPositiveButton("保存并复制", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            // 将文本内容放到系统剪贴板里。
                            cm.setText(errorInfo);
                            File file = writeLog(CrashActivity.this.getFilesDir().getPath() + File.separator + "log", getFileName() + ".txt", errorInfo);
                            if (file == null) {
                                ToastUtil.showToast("保存失败");
                            } else {
                                ToastUtil.showToast("日志已保存至" + file.getPath());
                            }

                            finish();
                        } catch (Exception e) {
                            Log.e("CrashActivity", e.toString());
                        }
                    }
                })
                .setNegativeButton("关闭程序", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            AppManager.getInstance().exit();
                        } catch (Exception e) {
                            Log.e("CrashActivity", e.toString());
                        }
                    }
                })
                .setNeutralButton("复制信息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            // 将文本内容放到系统剪贴板里。
                            cm.setText(errorInfo);
                            ToastUtil.showToast("复制成功，快去爆程序员菊花吧");
                            finish();
                        } catch (Exception e) {
                            Log.e("CrashActivity", e.toString());
                        }
                    }
                })
                .show();
    }

    private String getFileName() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    /**
     * 写日志文件
     */
    @Nullable
    private File writeLog(String filePath, String fileName, String content) {
        if (content == null) content = "";
        File fileDir = new File(filePath);
        if (!fileDir.exists() && !fileDir.mkdirs()) {
            return null;
        }
        File logFile = new File(fileDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(logFile);
            fos.write(content.getBytes());

            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return logFile;
    }

}
