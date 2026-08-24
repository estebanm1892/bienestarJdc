package com.esteban.bienestarjdc.data

data class AreaActivity(
    val area_id: Int,
    val id: Int,
    val name: String,
    val description: String,
    val initial_hour: String,
    val final_hour: String,
    val days: List<Day>?
)