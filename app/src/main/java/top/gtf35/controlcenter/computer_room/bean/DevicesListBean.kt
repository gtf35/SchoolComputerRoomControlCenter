package top.gtf35.controlcenter.computer_room.bean

import top.gtf35.controlcenter.common.bean.DeviceItem

data class DevicesListBean(
    val errno:Int,
    val data: DevicesDataListBean,
    val error: String
)

data class DevicesDataListBean(
    val per_page: Int,
    val devices: Array<DeviceItem>,
    val total_count: Int,
    val page: Int

)