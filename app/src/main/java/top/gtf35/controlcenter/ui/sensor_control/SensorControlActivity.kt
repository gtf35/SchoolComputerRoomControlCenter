package top.gtf35.controlcenter.ui.sensor_control

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_computer_room.*
import kotlinx.android.synthetic.main.activity_sensor_control.*
import top.gtf35.controlcenter.R
import top.gtf35.controlcenter.common.bean.DSItem
import top.gtf35.controlcenter.common.bean.DeviceItem
import top.gtf35.controlcenter.common.mvp_base.BaseActivity
import top.gtf35.controlcenter.common.utils.*
import top.gtf35.controlcenter.sensor_control.SensorControlPresent
import top.gtf35.controlcenter.ui.common.EmptyListAdapterItem
import top.gtf35.controlcenter.ui.common.EmptyListAdapterItemBean

/*
* 设备传感器列表及控制活动
* gtf35
* 2019/12/18
* */

class SensorControlActivity: BaseActivity<SensorControlPresent>(), ISensorControlV, OnRefreshListener {

    // 设备信息
    private lateinit var mDeviceInfo: DeviceItem;
    // RecycleView 快速适配框架
    private val mAdapter = MultiTypeAdapter()
    // RecycleView 的 item 们
    private val mItems = ArrayList<Any>()

    companion object{
        // kotlin 里 javaClass.simpleName 静态的和正常的获取到的不一样
        val CLASS_ANME = javaClass.simpleName

        /*
        * 封装一个启动并传递数据的方法
        * 因为这个活动启动的时候必须有参数
        * */
        fun start(context: Context, deviceInfo: DeviceItem){
            val intent = Intent(context, SensorControlActivity::class.java)
            intent.putExtra(CLASS_ANME, deviceInfo.toJson())
            context.startActivity(intent)
        }

    }

    /*
    * P 层获取传感器信息成功回调
    * */
    override fun onSensorListLoadSuccess(sensors: Array<DSItem>) {
        for (item in sensors){
            LogUtils.d("load item to rec ==> " + item.toString())
        }
        // 清空旧的列表
        mItems.clear()
        // 添加新数据
        mItems.addAll(sensors)
        // 如果列表为空，加个占位的提示(复用空列表 item）
        if (mItems.isEmpty()){
            showEmptyRecItem("空空如也", R.drawable.ic_sentiment_satisfied_black)
            // 通知适配器应用改变
            mAdapter.notifyDataSetChanged()
        }
        // 通知适配器应用改变
        mAdapter.notifyDataSetChanged()
        // 结束加载动画
        refreshLayout_sensor_control.finishRefresh(true)
    }

    /*
    * P 层获取传感器信息失败回调
    * */
    override fun onSensorListLoadFail(msg: String) {
        refreshLayout_sensor_control.finishRefresh(false)
        showErrorSnackbar("获取传感器信息失败 :(\n$msg")
        // 清空旧的
        mItems.clear()
        // 装入出错的 item to rec (复用空列表 item）
        showEmptyRecItem("获取传感器信息失败", R.drawable.ic_sentiment_dissatisfied_black)
        // 通知适配器应用改变
        mAdapter.notifyDataSetChanged()
    }

    /*
    * 下拉刷新框架刷新回调
    * */
    override fun onRefresh(refreshLayout: RefreshLayout) {
        // 如果现在是空的，没有项目，装入一个加载中的 item 进 rec (复用空列表 item）
        if (mItems.isEmpty()){
            showEmptyRecItem("空空如也", R.drawable.ic_sentiment_neutral_back)
            // 通知适配器应用改变
            mAdapter.notifyDataSetChanged()
        }
        LogUtils.d("开始刷新 devices list")
        p.getSensorList(mDeviceInfo)
    }

    /*
    * V 层基类加载布局回调
    * */
    override fun setLayout(): Int {
        return R.layout.activity_sensor_control
    }

    /*
    * V 层基类主函数回调
    * */
    override fun main() {
        setSupportActionBar(toolbar_sensor_control)
        setTitle(ResourcesUtils.getString(this, R.string.sensor_control_ac_title))
        praseDeviceInfo()
        // 设置 rec 为 瀑布流
        rec_sensor_control.setLayoutManager(
            StaggeredGridLayoutManager(
                if (ScreenUtils.isWideScreenMode(this)) 3 else 1,
                StaggeredGridLayoutManager.VERTICAL
            )
        )

        // 注册正常的 rec 的 item
        mAdapter.register(SensorInfoAdapterItem())
        // 注册列表为空时 rec 的 item
        mAdapter.register(EmptyListAdapterItem())
        // 设置 rec 的数据源
        mAdapter.items = mItems
        // rec 设置 adapter
        rec_sensor_control.adapter = mAdapter
        // 注册下拉刷新
        refreshLayout_sensor_control.setOnRefreshListener(this)
        // 触发刷新获取数据
        refreshLayout_sensor_control.autoRefresh()
        // 进入时是空的，白的不好看
        showEmptyRecItem("Loading...", R.drawable.ic_sentiment_satisfied_black)
        // 通知适配器应用改变
        mAdapter.notifyDataSetChanged()
    }

    /*
    * V 层基类绑定 P 层回调
    * */
    override fun setP(): SensorControlPresent {
        return SensorControlPresent(this)
    }

    /*
    * 获取并解析设备信息
    * */
    private fun praseDeviceInfo(){
        // 提取传递的序列化的设备信息
        val deviceInfoMsg = intent.getStringExtra(CLASS_ANME)
        if (TextUtils.isEmpty(deviceInfoMsg)){
            SnackbarUtils.Long(con_root_sensor_control, "非法启动！缺少序列化参数").danger().show()
            return
        }
        mDeviceInfo = DeviceItem.fromJson(deviceInfoMsg)
    }

    /*
    * 显示提示错误的Snackbar
    * 特点是带一个复制错误的按钮
    * */
    fun showErrorSnackbar(msg: String){
        SnackbarUtils.Custom(con_root_sensor_control, msg, 5 * 1000)
            .danger()
            .actionColor(ResourcesUtils.getColor(this@SensorControlActivity, R.color.colorText))
            .setAction("复制错误信息") { v: View? ->
                v as Button
                ClipboardUtils.copyText(this@SensorControlActivity, msg)
                SnackbarUtils.Short(refreshLayout_conmputer_room, "已经复制到剪贴板").show()
            }
            .show()
    }

    /**
     * 展示空的 rec
     * @msg 占位符下面的文字
     * bugfix 在横屏下因为有 3 列，所以不居中
     * */
    fun showEmptyRecItem(msg: String, picID: Int){
        // 先放入一个大小是 0 的占名额
        if (ScreenUtils.isWideScreenMode(this)) mItems.add(EmptyListAdapterItemBean("null", isHidden = true))
        // 装入空白 item
        mItems.add(EmptyListAdapterItemBean(msg, picID))
        // 再放入一个大小是 0 的占名额，正好 3 个
        if (ScreenUtils.isWideScreenMode(this)) mItems.add(EmptyListAdapterItemBean("null",  isHidden = true))
    }
}
