package com.example.mybmikotlin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DecimalFormat

@Entity("info")
data class Info (
    @PrimaryKey(false)
    val id: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("height")
    val height: Double,
    @ColumnInfo("weight")
    val weight: Double,
    @ColumnInfo("bmi")
    val bmi: Double
) {
    fun getDoubleFormatText(value: Double): String = DecimalFormat("0.##").format(value)
}