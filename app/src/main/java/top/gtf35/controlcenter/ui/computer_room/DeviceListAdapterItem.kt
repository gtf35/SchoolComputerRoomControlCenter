package top.gtf35.controlcenter.ui.computer_room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.google.android.material.chip.Chip
import top.gtf35.controlcenter.App
import top.gtf35.controlcenter.R
import top.gtf35.controlcenter.common.utils.ResourcesUtils
import top.gtf35.controlcenter.computer_room.bean.DeviceItem

class DeviceListAdapterItem: ItemViewDelegate<DeviceItem, DeviceListAdapterItem.ViewHolder>(){

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        val root = LayoutInflater.from(context).inflate(R.layout.item_computer_room, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: DeviceItem) {
        holder.deviceName.text = item.title
        holder.deviceID.text = item.id
        holder.deviceOnline.text = if (item.isOnline) "在线" else "离线"
        holder.deviceType.text = item.protocol
        // 根据在线情况设置 chiper icon
        holder.deviceOnline.chipIcon = ResourcesUtils.getDrawable(App.getAppContext(),
            if (item.isOnline) R.drawable.ic_check_circle_white else R.drawable.ic_error_white)
        // 根据在线情况设置 chiper 颜色
        holder.deviceOnline.setChipBackgroundColorResource(if (item.isOnline) R.color.colorAccent else R.color.colorError)
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val deviceName: TextView = itemView.findViewById(R.id.tv_device_name)
        val deviceID: TextView = itemView.findViewById(R.id.tv_device_id)
        val deviceOnline: Chip = itemView.findViewById(R.id.chip_online)
        val deviceType: TextView = itemView.findViewById(R.id.tv_device_type)
    }
}