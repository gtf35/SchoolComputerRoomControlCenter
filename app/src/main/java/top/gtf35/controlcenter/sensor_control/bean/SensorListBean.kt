package top.gtf35.controlcenter.sensor_control.bean

import top.gtf35.controlcenter.common.bean.DSItem

data class SensorListBean(
    val errno:Int,
    val data: Array<DSItem>,
    val error: String
)
