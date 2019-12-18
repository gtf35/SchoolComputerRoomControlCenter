package top.gtf35.controlcenter.ui.sensor_control

import top.gtf35.controlcenter.common.bean.DSItem

interface ISensorControlV {
    fun onSensorListLoadSuccess(sensorItems: Array<DSItem>)

    fun onSensorListLoadFail(msg: String)
}