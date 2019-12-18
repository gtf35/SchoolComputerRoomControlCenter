package top.gtf35.controlcenter.sensor_control

import com.chinamobile.iot.onenet.OneNetApi
import com.chinamobile.iot.onenet.OneNetApiCallback
import com.google.gson.Gson
import top.gtf35.controlcenter.common.bean.DeviceItem
import top.gtf35.controlcenter.common.mvp_base.BasePresenter
import top.gtf35.controlcenter.common.utils.LogUtils
import top.gtf35.controlcenter.sensor_control.bean.SensorListBean
import top.gtf35.controlcenter.ui.sensor_control.ISensorControlV
import java.lang.Exception

class SensorControlPresent(val mISensorControlV: ISensorControlV): BasePresenter<ISensorControlV>(),
    OneNetApiCallback {

    /*
    * Onenet API 回调
    * 获取数据成功
    * */
    override fun onSuccess(response: String?) {
        LogUtils.d("getSensorList API 获取成功：$response")
       try {
           val g = Gson()
           // 解析 json
           var sensorListBean = g.fromJson(response, SensorListBean::class.java)
           // 判断错误码
           if (sensorListBean.errno == 0){
               // 没错误
               v.onSensorListLoadSuccess(sensorListBean.data)
           } else {
               onFailed(java.lang.Exception("服务器返回错误码 ${sensorListBean.errno} ${sensorListBean.error}"))
           }
       } catch (e: Exception){
           // 错误统一走 OnFail
           onFailed(e)
       }
    }

    /*
    * Onenet API h回调
    * 获取数据失败
    * */
    override fun onFailed(e: Exception?) {
        if (e == null) return
        e.printStackTrace()
        LogUtils.e("getSensorList API 获取失败：${e.message}")
        // 回调 V 层展示错误
        v.onSensorListLoadFail(e.message.toString())
    }

    /*
    * 关联 mvp 的 v 层
    * */
    override fun setV(): ISensorControlV {
        return mISensorControlV
    }

    /*
    * 获取传感器列表
    * */
    fun getSensorList(deviceItem: DeviceItem){
        OneNetApi.queryMultiDataStreams(deviceItem.id, this);
    }

}