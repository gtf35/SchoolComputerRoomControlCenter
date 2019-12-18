package top.gtf35.controlcenter.ui.common

import top.gtf35.controlcenter.R

data class EmptyListAdapterItemBean (
    val name: String,
    val logoID: Int = R.drawable.ic_sentiment_neutral_back,
    val isHidden: Boolean = false
)