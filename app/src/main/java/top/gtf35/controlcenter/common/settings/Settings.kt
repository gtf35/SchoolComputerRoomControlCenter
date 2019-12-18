package top.gtf35.controlcenter.common.settings

import top.gtf35.controlcenter.App
import top.gtf35.controlcenter.BuildConfig
import top.gtf35.controlcenter.R
import top.gtf35.controlcenter.common.utils.ResourcesUtils

object Settings {
    // 本地日志路径
    public val LOCAL_LOG_PATH = "/sdcard/" +
            ResourcesUtils.getString(App.getAppContext(), R.string.app_name);
    // 启用将日志写入本地？
    public val ENABLE_LOCAL_LOG = !BuildConfig.DEBUG;   // 只有在 release 才需要记录本地日志

}