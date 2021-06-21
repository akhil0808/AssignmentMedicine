package com.delight.assignment.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Medicines")
data class Medicine(
    @ColumnInfo(name = "pid")
    val pid: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "company")
    var company: String,
    @ColumnInfo(name = "strength")
    val strength: String,
    @ColumnInfo(name = "strengthtype")
    val strengthtype: String,
    @ColumnInfo(name = "quantity")
    var quantity: Int)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}




