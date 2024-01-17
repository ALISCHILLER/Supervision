package com.msa.supervisor.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.zar.core.tools.extensions.toSolarDate
import java.time.LocalDateTime

/**
 * create by Ali Soleymani.
 */


//-------------------------------------------------------------------------------------------------- getTextByValue
fun getTextByValue(title: String?, value: Any?, splitter: String): String {
    return value?.let {
        when (it) {
            is String -> "$title $splitter $it"
            is Long -> "$title $splitter $it"
            is Int -> "$it $splitter $title"
            is LocalDateTime -> "$title$splitter${it.toSolarDate()?.getSolarDate()}"
            else -> ""
        }
    } ?: run { "" }
}
//-------------------------------------------------------------------------------------------------- getTextByValue


//-------------------------------------------------------------------------------------------------- setTitleAndValue
@BindingAdapter("setTitle", "setValue", "setSplitter")
fun TextView.setTitleAndValue(title: String?, value: Any?, splitter: String) {
    text = getTextByValue(title, value, splitter)
}
//-------------------------------------------------------------------------------------------------- setTitleAndValue


//-------------------------------------------------------------------------------------------------- setValue
@BindingAdapter("setValue")
fun TextView.setValue(value: Any?){
    value?.let {
        when(it){
            is Int -> text = "$it"
            is String -> text = it
        }
    } ?: run { text = "" }
}
//-------------------------------------------------------------------------------------------------- setValue

