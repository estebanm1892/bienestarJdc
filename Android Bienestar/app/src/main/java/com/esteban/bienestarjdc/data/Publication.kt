package com.esteban.bienestarjdc.data

data class Publication(
    val area_id: Int?,
    val content: String?,
    val id: Int,
    val image: String,
    val tittle: String,
    val created_at:String,
    val area: Area?
)

