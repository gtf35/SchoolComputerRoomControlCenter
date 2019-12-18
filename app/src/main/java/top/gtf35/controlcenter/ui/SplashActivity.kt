package top.gtf35.controlcenter.ui

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import tech.gujin.toast.ToastUtil
import top.gtf35.controlcenter.R
import top.gtf35.controlcenter.common.utils.LogUtils
import top.gtf35.controlcenter.common.utils.PermissionUtils
import top.gtf35.controlcenter.ui.computer_room.ComputerRoomActivity


/*
* 闪屏页
* gtf35
* 2019/04/29
* gtfdeyouxiang@gmail.com
* 用于以下功能：
* 1：运行时权限申请（参考博客：https://blog.csdn.net/hexingen/article/details/78504814）
* */
class SplashActivity : AppCompatActivity(){

    var mRequestCode: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //申请储存写入权限
        val hasWriteExternalStorage = PermissionUtils.checkWriteExternalStorage(this)
        if (hasWriteExternalStorage) {
            LogUtils.d("已获取储存权限")
            onSuccessGetPremissions()
        } else {
            LogUtils.w("没有储存权限，去申请")
            requestPermission()
        }
    }

    /*
    * 请求权限
    * */
    fun requestPermission(){
        val tip = getString(R.string.request_write_external_tip)
        var dialog = showDialog(tip, object: DialogInterface.OnDismissListener{
            override fun onDismiss(dialog: DialogInterface?) {
                PermissionUtils.requestWriteExternalStorage(this@SplashActivity, mRequestCode)
            }
        })
        dialog.show()
    }

    /**
     * 系统回调
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>, @NonNull grantResults: IntArray
    ) {
        // 遍历申请的权限结果
        for ((index, permissionStr) in  permissions.withIndex()){
            // 通过权限的 index 取出对应的结果
            var deniedPermission = ArrayList<String>();
            var requestResult = grantResults.get(index)
            LogUtils.d("权限 $permissionStr " +
                    if (requestResult == PackageManager.PERMISSION_GRANTED) "已经授予" else "未授予")
            if ( requestResult == PackageManager.PERMISSION_DENIED){
                // 判断是不是勾选了不再提示
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissionStr)){
                    // 跳转设置界面
                    startActivity(PermissionUtils.getAppDetailSettingIntent(this))
                    exit("请手动开启权限" )
                    return
                }
                // 添加到被拒绝的权限列表
                deniedPermission.add(permissionStr)
            }
            // 没有被拒绝的权限
            if (deniedPermission.size == 0){
                // 离开欢迎页
                leaveSplash()
            } else {
                // 继续申请被拒绝的权限
                PermissionUtils.requestPermission(this, requestCode,
                    deniedPermission.toArray(Array<String>(deniedPermission.size){"it = $it"})
                            as Array<out String>)
            }
        }

    }

    //获取到了权限
    private fun onSuccessGetPremissions() {
        leaveSplash()
    }


    fun showDialog(msg: String, onDismissListener: DialogInterface.OnDismissListener): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示")
        builder.setCancelable(false)
        builder.setOnDismissListener(onDismissListener)
        builder.setMessage(msg)
        builder.setNegativeButton("确定",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        return builder.create()
    }

    fun showToast(msg: String, isLong: Boolean) {
        ToastUtil.postShow(msg, isLong)
    }

    fun exit(msg: String) {
        ToastUtil.postShow("很抱歉，程序无法继续运行 $msg")
        finish()
    }

    private fun leaveSplash() {
        startActivity(Intent(this, ComputerRoomActivity::class.java))
        finish()
    }
}
