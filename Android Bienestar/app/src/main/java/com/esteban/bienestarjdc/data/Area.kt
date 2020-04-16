package com.esteban.bienestarjdc.data

data class Area(
    val activities: List<Activity>?,
    val area_image: String,
    val area_presentation: String,
    val id: Int,
    val name: String,
    val objetive: String,
    val programs: String,
    val publications: List<Publication>?,
    val users: List<User>
)