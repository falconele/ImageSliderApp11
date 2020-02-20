package com.elementarylogics.imagesliderapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;

import com.elementarylogics.imagesliderapp.R;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

public class DialogFactory {

    public static void showDropDownNotificationError(Activity mContext, String title, String message) {
        Alerter.create(mContext)
                .setTitle(title)
                .setText(message)
                .enableSwipeToDismiss()
                .setBackgroundColorRes(R.color.alert_dialog_error)
                .setIcon(R.drawable.ic_alert)
                .setIconColorFilter(0)
                .setDuration(2000)
                .show();
    }


    public static void showDropDownNotificationSuccess(Activity mContext, String title, String message) {
        Alerter.create(mContext)
                .setTitle(title)
                .setText(message)
                .enableSwipeToDismiss()
                .setBackgroundColorRes(R.color.alert_dialog_success)
                .setIcon(R.drawable.ic_success)
                .setIconColorFilter(0)
                .setDuration(2000)
                .setOnShowListener(new OnShowAlertListener() {
                    @Override
                    public void onShow() {
                    }
                })
                .setOnHideListener(new OnHideAlertListener() {
                    @Override
                    public void onHide() {
                    }
                })
                .show();
    }


    public static void showDropDownNotificationSuccessWithFinish(final Activity mContext, String title, String message) {
        Alerter.create(mContext)
                .setTitle(title)
                .setText(message)
                .enableSwipeToDismiss()
                .setBackgroundColorRes(R.color.alert_dialog_success)
                .setIcon(R.drawable.ic_success)
                .setIconColorFilter(0)
                .setDuration(1000)
                .setOnShowListener(new OnShowAlertListener() {
                    @Override
                    public void onShow() {
                    }
                })
                .setOnHideListener(new OnHideAlertListener() {
                    @Override
                    public void onHide() {
                        mContext.finish();
                    }
                })
                .show();
    }


    public static void dialogSettings(Context ctx, Dialog dialog, View parentView, float h, float w) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthLcl = (int) (displayMetrics.widthPixels * w);
        int heightLcl = (int) (displayMetrics.heightPixels * h);
        FrameLayout.LayoutParams paramsLcl = (FrameLayout.LayoutParams)
                parentView.getLayoutParams();
        paramsLcl.width = widthLcl;
        paramsLcl.height = heightLcl;
        paramsLcl.gravity = Gravity.CENTER;
        parentView.setLayoutParams(paramsLcl);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public  static  void showInfoToUser(Context ctx, String title, String msg) {
        AlertDialog.Builder aler = new AlertDialog.Builder(ctx);
        aler.setTitle(title);
        aler.setMessage(msg);
        aler.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        aler.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        aler.show();
    }
}
