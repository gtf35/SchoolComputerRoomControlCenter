package top.gtf35.controlcenter.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.ActivityCompat;

/*
* 运行时权限申请工具类
* 2019/04/29
* by gtf35
* gtfdeyouxiang@gmail.com
* */
public class PermissionUtils {

    /*
    * 检查权限
    * 第一个参数：Context 例如，Activity对象
    * 第二个参数 : 权限。eg: Manifest.permission.WRITE_EXTERNAL_STORAGE
    * */
    public static boolean checkPermission(Context context, String perm) {
        return ActivityCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 请求权限
     * @param activity ：ActivityCompat 对象
     * @param requestCode ：这次请求权限的唯一标示，code。
     * @param perms  : 一些系列的权限
     */
    public static void requestPermission(Activity activity, int requestCode, String[] perms){
        ActivityCompat.requestPermissions(activity, perms, requestCode);
    }


    /**
     * 检查储存写入权限
     * @param context ：ActivityCompat 对象
     */
    public static boolean checkWriteExternalStorage(Context context){
        return PermissionUtils.checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    /**
     * 请求权限
     * @param activity ：ActivityCompat 对象
     * @param requestCode ：这次请求权限的唯一标示，code
     */
    public static void requestWriteExternalStorage(Activity activity, int requestCode){
        String[] PERMS_WRITE ={Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermission(activity, requestCode, PERMS_WRITE);
    }

    /**
     * 获取应用详情页面intent
     *
     * @return intent
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }
}
