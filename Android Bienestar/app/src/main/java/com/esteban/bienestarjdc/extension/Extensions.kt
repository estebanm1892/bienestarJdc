package com.esteban.bienestarjdc.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(res: Int): View {
    return LayoutInflater.from(context).inflate(res, null, false)
}