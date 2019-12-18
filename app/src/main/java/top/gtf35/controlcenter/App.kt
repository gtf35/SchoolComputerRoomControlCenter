package top.gtf35.controlcenter

import android.app.Application
import android.content.Context
import com.chinamobile.iot.onenet.OneNetApi
import com.chinamobile.iot.onenet.http.Config
import tech.gujin.toast.ToastUtil
import java.util.concurrent.TimeUnit
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class App: Application() {

    init {
        sInstance = this;
    }

    companion object {
        private var sInstance: App ?= null;

        @JvmStatic // Java 的一些老的类库要用
        fun getAppContext(): Context{
            return sInstance!!.mAppContext;
        }
    }

    private lateinit var mAppContext: Context;

    override fun onCreate() {
        super.onCreate()
         mAppContext = applicationContext;

        // 初始化 ToastUtils
        ToastUtil.initialize(this, ToastUtil.Mode.REPLACEABLE)  // 可替换模式

        // 初始化上拉加载下拉刷新框架
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout ->
            //开始设置全局的基本参数（可以被下面的DefaultRefreshHeaderCreator覆盖）
            layout.setReboundDuration(1000)
            layout.setFooterHeight(100f)
            layout.setDisableContentWhenLoading(false)
            layout.setDisableContentWhenRefresh(true)
            layout.setEnableLoadMore(false)
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
        }

        // 初始化 OneNET
        val config = Config.newBuilder()
            .connectTimeout(60000, TimeUnit.MILLISECONDS) // 连接超时时间
            .readTimeout(60000, TimeUnit.MILLISECONDS)    // 读取数据超时时间
            .writeTimeout(60000, TimeUnit.MILLISECONDS)   // 发送数据超时时间
            .retryCount(2) // 连接失败重试次数
            .build()
        OneNetApi.init(this, true, config)
    }

}