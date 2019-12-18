package top.gtf35.controlcenter.ui.computer_room

import android.view.View
import android.widget.Button
import com.drakeet.multitype.MultiTypeAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_computer_room.*
import top.gtf35.controlcenter.R
import top.gtf35.controlcenter.common.mvp_base.BaseActivity
import top.gtf35.controlcenter.computer_room.ComputerRoomPresent
import top.gtf35.controlcenter.common.bean.DeviceItem
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import top.gtf35.controlcenter.common.utils.*
import top.gtf35.controlcenter.ui.common.EmptyListAdapterItem
import top.gtf35.controlcenter.ui.common.EmptyListAdapterItemBean


/*
* 数据中心列表活动
* gtf35 created at 2019/12/17
* */
class ComputerRoomActivity : BaseActivity<ComputerRoomPresent>(), OnRefreshListener, IComputerRoomV {

    // 关联 mvp 的 p 层
    override fun setP(): ComputerRoomPresent {
        return ComputerRoomPresent(this)
    }

    // RecycleView 快速适配框架
    private val mAdapter = MultiTypeAdapter()
    // RecycleView 的 item 们
    private val mItems = ArrayList<Any>()

    override fun onDevicesListLoadSuccess(deviceItems: Array<DeviceItem>) {
        for (item in deviceItems){
            LogUtils.d("load item to rec ==> " + item.toString())
        }
        // 清空旧的列表
        mItems.clear()
        // 添加新数据
        mItems.addAll(deviceItems)
        // 通知适配器应用改变
        mAdapter.notifyDataSetChanged()
        // 结束加载动画
        refreshLayout_conmputer_room.finishRefresh(true)

    }

    override fun onDevicesListLoadFail(msg: String) {
        refreshLayout_conmputer_room.finishRefresh(false)
        showErrorSnackbar("加载数据中心列表失败 :(\n$msg")
        // 清空旧的
        mItems.clear()
        // 装入出错的 item to rec (复用空列表 item）
        showEmptyRecItem("加载失败", R.drawable.ic_sentiment_dissatisfied_black)
        // 通知适配器应用改变
        mAdapter.notifyDataSetChanged()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        // 如果现在是空的，没有项目，装入一个加载中的 item 进 rec (复用空列表 item）
       if (mItems.isEmpty()){
           showEmptyRecItem("空空如也", R.drawable.ic_sentiment_neutral_back)
           // 通知适配器应用改变
           mAdapter.notifyDataSetChanged()
       }
        LogUtils.d("开始刷新 devices list")
        p.getDevices()
    }

    override fun setLayout(): Int {
        return R.layout.activity_computer_room;
    }

    override fun main() {
        setSupportActionBar(toolbar_computer_room)
        setTitle(ResourcesUtils.getString(this, R.string.main_ac_title))
        // 设置 rec 为 瀑布流
        rec_computer_room.setLayoutManager(
            StaggeredGridLayoutManager(
                if (ScreenUtils.isWideScreenMode(this)) 3 else 1,
                StaggeredGridLayoutManager.VERTICAL
            )
        )

        // 注册正常的 rec 的 item
        mAdapter.register(DeviceListAdapterItem())
        // 注册列表为空时 rec 的 item
        mAdapter.register(EmptyListAdapterItem())
        // 设置 rec 的数据源
        mAdapter.items = mItems
        // rec 设置 adapter
        rec_computer_room.adapter = mAdapter
        // 注册下拉刷新
        refreshLayout_conmputer_room.setOnRefreshListener(this)
        // 触发刷新获取数据
        refreshLayout_conmputer_room.autoRefresh()
        // 进入时是空的，白的不好看
        showEmptyRecItem("Loading...", R.drawable.ic_sentiment_satisfied_black)
        // 通知适配器应用改变
        mAdapter.notifyDataSetChanged()
    }

    /*
    * 显示提示错误的Snackbar
    * 特点是带一个复制错误的按钮
    * */
    fun showErrorSnackbar(msg: String){
        SnackbarUtils.Custom(refreshLayout_conmputer_room, msg, 5 * 1000)
            .danger()
            .actionColor(ResourcesUtils.getColor(this@ComputerRoomActivity, R.color.colorText))
            .setAction("复制错误信息") { v: View? ->
                v as Button
                ClipboardUtils.copyText(this@ComputerRoomActivity, msg)
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
