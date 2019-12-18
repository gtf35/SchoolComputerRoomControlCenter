package top.gtf35.controlcenter.common.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

public class ResourcesUtils {
    public static Resources getResources(Context context) {
        return context.getApplicationContext().getResources();
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static String getString(Context context, @StringRes int resId) {
        return getResources(context).getString(resId);
    }

    /**
     * 获取颜色
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static int getColor(Context context, @ColorRes int resId) {
        return ContextCompat.getColor(context, resId);
    }

    /**
     * 获取颜色
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static ColorStateList getColorStateList(Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getResources(context).getColorStateList(resId, context.getTheme());
        }

        return getResources(context).getColorStateList(resId);
    }

    /**
     * 获取Drawable
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        return  ContextCompat.getDrawable(context,resId);
    }

    /**
     * 获取尺寸资源
     *
     * @param context 上下文
     * @param resId   资源ID
     * @return px
     */
    public static float getDimen(Context context, @DimenRes int resId) {
        return getResources(context).getDimension(resId);
    }
}