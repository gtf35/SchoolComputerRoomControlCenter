package top.gtf35.controlcenter.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftInputMethodUtils {

    public static void hideSoftInputFromWindow(Activity activity, EditText editText){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //隐藏软键盘
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void showSoftInputFromInputMethod(Activity activity, EditText editText){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //显示软键盘
        imm.showSoftInputFromInputMethod(editText.getWindowToken(), 0);
    }

    public static void toggleSoftInputFromWindow(Activity activity, EditText editText){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //切换软键盘的显示与隐藏
        imm.toggleSoftInputFromWindow(editText.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
