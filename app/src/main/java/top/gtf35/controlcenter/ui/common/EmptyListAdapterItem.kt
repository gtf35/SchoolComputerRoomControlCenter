package top.gtf35.controlcenter.ui.common


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import tech.gujin.toast.ToastUtil
import top.gtf35.controlcenter.App
import top.gtf35.controlcenter.R
import top.gtf35.controlcenter.common.utils.ClipboardUtils

class EmptyListAdapterItem: ItemViewDelegate<EmptyListAdapterItemBean, EmptyListAdapterItem.ViewHolder>(){

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        val root = LayoutInflater.from(context).inflate(R.layout.item_empty, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: EmptyListAdapterItemBean) {
        holder.msg.text = item.name
        holder.imgIcon.setImageResource(item.logoID)

        // 有时候文字会过长，超过3行会省略，点击弹 Toast，长按复制
        if (holder.msg.text.lines().size >= 3)holder.msg.setOnClickListener { ToastUtil.postShow(item.name) }
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val msg: TextView = itemView.findViewById(R.id.tv_empty_item_text)
        val imgIcon: ImageView = itemView.findViewById(R.id.iv_empty_item_icon)
    }
}