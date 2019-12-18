package top.gtf35.controlcenter.computer_room

import top.gtf35.controlcenter.common.mvp_base.BasePresenter
import com.chinamobile.iot.onenet.OneNetApiCallback
import com.chinamobile.iot.onenet.OneNetApi
import com.google.gson.Gson
import top.gtf35.controlcenter.common.utils.LogUtils
import top.gtf35.controlcenter.computer_room.bean.DevicesListBean
import top.gtf35.controlcenter.ui.computer_room.IComputerRoomV


class ComputerRoomPresent(val mIComputerRoomV: IComputerRoomV): BasePresenter<IComputerRoomV>(){
    override fun setV(): IComputerRoomV {
        return this.mIComputerRoomV
    }

    /**
    * 获取设备列表（一个数据中心定义为一个设备）
    * */
    public fun getDevices(){
        val urlParams = HashMap<String, String>()
        urlParams.put("page", "1")
        urlParams.put("per_page", "10")

        OneNetApi.fuzzyQueryDevices(urlParams, object : OneNetApiCallback {
            override fun onSuccess(response: String) {
                LogUtils.d("GetDevices API Success:" + response)
                try {
                    val g = Gson()
                    // 解析 json
                    val devicesListBean = g.fromJson(response, DevicesListBean::class.java)
                    // 判断错误码
                    if (devicesListBean.errno == 0) {
                        // 没错误
                        mIComputerRoomV.onDevicesListLoadSuccess(devicesListBean.data.devices)
                    } else {
                        onFailed(java.lang.Exception("服务器返回错误码 ${devicesListBean.errno} ${devicesListBean.error}"))
                    }

                } catch (e: Exception){
                    // 错误统一走 OnFail
                    onFailed(e)
                }

            }

            override fun onFailed(e: Exception) {
                e.printStackTrace()
                LogUtils.e("GetDevices API Failed:" + e.message.toString())
                v.onDevicesListLoadFail(e.message.toString())
            }
        })
    }
}