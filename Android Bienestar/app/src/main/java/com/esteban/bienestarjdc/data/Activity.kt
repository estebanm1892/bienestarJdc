package com.esteban.bienestarjdc.data

data class Activity(
    val area_id: Int,
    val id: Int,
    val name: String,
    val description: String,
    val initial_hour: String,
    val final_hour: String,
    val days: List<Day>,
    val virutal_resource: List<VirtualResource>
)