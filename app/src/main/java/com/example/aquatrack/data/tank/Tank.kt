package com.example.aquatrack.data.tank

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tanks")
data class Tank(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,

    val length: Int,
    val width: Int,
    val height: Int,

    val gallons: Int
) {

}