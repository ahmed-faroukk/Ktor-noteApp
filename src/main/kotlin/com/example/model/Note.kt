package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id : String ,
    val title : String ,
    val desc : String
)
val noteStorage = mutableListOf<Note>()
