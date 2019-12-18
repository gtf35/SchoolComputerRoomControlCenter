package top.gtf35.controlcenter.ui.sensor_control

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import top.gtf35.controlcenter.R
import top.gtf35.controlcenter.common.bean.DSItem
import top.gtf35.controlcenter.common.utils.ResourcesUtils
import top.gtf35.controlcenter.common.utils.ScreenUtils

class SensorInfoAdapterItem: ItemViewDelegate<DSItem, SensorInfoAdapterItem.ViewHolder>() {

    private lateinit var mContext: Context;


    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        mContext = context
        val layoutID = if (ScreenUtils.isWideScreenMode(mContext)) R.layout.item_sensor_land else R.layout.item_sensor_port
        val root = LayoutInflater.from(context).inflate(layoutID , parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: DSItem) {
        // 标题，好像，，也可也叫传感器名称
        holder.sensorTitle.text = item.id
        // 标签，有可能是空的
        val tags = item.tags;
        if (tags != null && tags.size != 0){
            for (tag in tags){
                holder.sensorTag.text = holder.sensorTag.text.toString() + " " + tag
            }
        } else {
            holder.sensorTag.text = ResourcesUtils.getString(mContext, R.string.sensor_tag_null_tip)
        }
        // 更新时间，有可能是空的
        val refreashTime = item.updateTime
        if (TextUtils.isEmpty(refreashTime)){
            holder.sensorRefreashTime.text = ResourcesUtils.getString(mContext, R.string.sensor_refreash_time_null_tip)
        } else {
            holder.sensorRefreashTime.text = refreashTime
        }
        // 传感器数值，有可能是空的
        val value = item.currentValue
        if (value != null && TextUtils.isEmpty(value.toString())){
            holder.sensorValue.text = value.toString()
        } else {
            holder.sensorValue.text = "--"
        }

        //传感器单位
        holder.sensorUnit.text = item.unit

    }

    class ViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem){
        val sensorTitle: TextView = viewItem.findViewById(R.id.tv_sensor_title)
        val sensorTag: TextView = viewItem.findViewById(R.id.tv_sensor_tag)
        val sensorRefreashTime: TextView = viewItem.findViewById(R.id.tv_sensor_refreash_time)
        val sensorValue: TextView = viewItem.findViewById(R.id.tv_sensor_value)
        val sensorUnit: TextView = viewItem.findViewById(R.id.tv_sensor_unit)
    }
}