package top.gtf35.controlcenter.ui.computer_room

import top.gtf35.controlcenter.computer_room.bean.DeviceItem
import top.gtf35.controlcenter.computer_room.bean.DevicesListBean

interface IComputerRoomV {

    fun onDevicesListLoadSuccess(deviceItems: Array<DeviceItem>)

    fun onDevicesListLoadFail(msg: String)

}