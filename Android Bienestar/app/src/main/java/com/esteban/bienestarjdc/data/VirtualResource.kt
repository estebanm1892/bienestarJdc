package com.esteban.bienestarjdc.data

data class VirtualResource(
    val id: Int,
    val tittle: String,
    val description: String,
    val embed_video: String,
    val docs: String,
    val image: String,
    val activity_id: Int
)