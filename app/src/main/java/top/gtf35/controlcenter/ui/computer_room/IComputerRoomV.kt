package top.gtf35.controlcenter.ui.computer_room

import top.gtf35.controlcenter.common.bean.DeviceItem

interface IComputerRoomV {

    fun onDevicesListLoadSuccess(deviceItems: Array<DeviceItem>)

    fun onDevicesListLoadFail(msg: String)

}