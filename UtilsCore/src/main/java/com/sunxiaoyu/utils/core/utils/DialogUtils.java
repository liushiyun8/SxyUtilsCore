package com.sunxiaoyu.utils.core.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;


/**
 * DialogUtils
 * Created by Sunxy on 2018/1/11.
 */
public class DialogUtils {

    private static AlertDialog dialog;

    public static boolean isShow(){
        return dialog != null && dialog.isShowing();
    }

    public static void dismiss(){
        if (isShow()){
            dialog.dismiss();
        }
        dialog = null;
    }

    /**
     * 显示加载Dialog
     * @param context   上下文
     * @param msg       内容
     */
    public static void showLoadDialog(Context context, String msg){
        showLoadDialog(context, msg, false);
    }

    /**
     * 显示加载Dialog
     * @param context   上下文
     * @param msg       内容
     * @param canCancel 是否可以点击其他地方取消
     */
    public static void showLoadDialog(Context context, String msg, boolean canCancel){
        dismiss();
        dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.setCancelable(canCancel);
        dialog.setCanceledOnTouchOutside(canCancel);
        dialog.show();
    }

    /**
     * 显示让用户选择的Dialog
     * @param context           上下文
     * @param msg               显示内容
     * @param sureStr           确定按钮文案
     * @param sureListener      确定按钮点击监听
     * @param cancelStr         取消按钮文案
     * @param cancelListener    取消文案监听
     */
    public static void showSelectDialog(Context context, String msg,
                                        String sureStr, DialogInterface.OnClickListener sureListener,
                                        String cancelStr, DialogInterface.OnClickListener cancelListener){
        showSelectDialog(context, msg, sureStr, sureListener, cancelStr, cancelListener, false, false);
    }

    /**
     * 显示让用户选择的Dialog
     * @param context           上下文
     * @param msg               显示内容
     * @param sureStr           确定按钮文案
     * @param sureListener      确定按钮点击监听
     * @param cancelStr         取消按钮文案
     * @param cancelListener    取消文案监听
     * @param canCancel         不点击按钮是否可以取消
     * @param isOne             是否是一个按钮（是否不显示取消按钮）
     */
    public static void showSelectDialog(Context context, String msg,
                                        String sureStr, DialogInterface.OnClickListener sureListener,
                                        String cancelStr, DialogInterface.OnClickListener cancelListener, boolean canCancel, boolean isOne){

        dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("提示").setMessage(msg);
        builder.setPositiveButton(StringUtils.isNull(sureStr) ? "确定" : sureStr,
                sureListener != null ? sureListener : new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        if (!isOne){
            builder.setNegativeButton(StringUtils.isNull(cancelStr) ? "取消" : cancelStr,
                    cancelListener != null ? cancelListener : new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        dialog = builder.create();
        dialog.setCancelable(canCancel);
        dialog.setCanceledOnTouchOutside(canCancel);
        dialog.show();
    }

    /**
     * 显示可编辑的Dialog
     * @param context   上下文
     * @param title     title
     * @param listener  确定监听器
     */
    public static void showEditDialog(Context context, String title, final EditListener listener){
        dismiss();

        final EditText et = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null){
                            String input = et.getText().toString();
                            listener.onInputText(et, input);
                        }else{
                            SystemUtils.hideSoftKeyboard(et);
                            dismiss();
                        }
                    }
                })
                .setNegativeButton("取消", null);

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    /**
     * 显示多选的Dialog
     * @param context   上下文
     * @param title     title
     * @param items     选项
     * @param listener  监听器
     */
    public static void showMoreSelectDialog(Context context, String title, String[] items, DialogInterface.OnClickListener listener){
        dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title)
                .setSingleChoiceItems(items, -1, listener);
        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    public interface EditListener{
        void onInputText(EditText editText, String str);
    }

}
