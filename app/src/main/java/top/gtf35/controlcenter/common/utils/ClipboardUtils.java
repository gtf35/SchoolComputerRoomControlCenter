package top.gtf35.controlcenter.common.utils;

/*
        作者：姚镜堂
        来源：CSDN
        原文：https://blog.csdn.net/qq_16628781/article/details/60769930
        版权声明：本文为博主原创文章，转载请附上博文链接！*/

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * desc:剪贴板相关工具类
 * <p>
 * author:kuyu.yaojt (tanksu)
 * <p>
 * email:yaojt@kuyumall.com
 * <p>
 * date:17/3/7
 */

public class ClipboardUtils {

    /**
     * 构造方法
     */
    private ClipboardUtils() {
        throw new UnsupportedOperationException("the constructor is private, can't be init.");
    }

    /**
     * 复制文本到剪贴板
     *
     * @param text 文本
     */
    public static void copyText(Context context, CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText("text", text));
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public static CharSequence getText(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(context);
        }
        return null;
    }

    /**
     * 复制uri到剪贴板
     *
     * @param uri uri
     */
    public static void copyUri(Context context, Uri uri) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newUri(context.getContentResolver(), "uri", uri));
    }

    /**
     * 获取剪贴板的uri
     *
     * @return 剪贴板的uri
     */
    public static Uri getUri(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getUri();
        }
        return null;
    }

    /**
     * 复制意图到剪贴板
     *
     * @param intent 意图
     */
    public static void copyIntent(Context context, Intent intent) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newIntent("intent", intent));
    }

    /**
     * 获取剪贴板的意图
     *
     * @return 剪贴板的意图
     */
    public static Intent getIntent(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }

}
