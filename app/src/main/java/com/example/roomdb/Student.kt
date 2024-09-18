package com.example.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "s_t")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "f_n")
    val firstName: String,
    @ColumnInfo(name = "l_n")
    val lastName: String,
    @ColumnInfo(name = "r_n")
    val rollNumber: Int,

    )
