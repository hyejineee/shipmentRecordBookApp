package com.hyejineee.shipmentrecordbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_STRING = "yyyy.MM.dd"

fun String.toDate(formatStr: String? = DATE_FORMAT_STRING): Date? {
    val format = SimpleDateFormat(formatStr)
    return format.parse(this)
}

fun Date.convertString(formatStr: String? = DATE_FORMAT_STRING): String {
    val format = SimpleDateFormat(formatStr)
    return format.format(this)
}

fun <T> MutableLiveData<T>.toLiveDate(): LiveData<T> = this
