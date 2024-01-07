package com.example.stydentmaneger

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "students")
data class Student (
    @PrimaryKey var mssv: String,
    var fullName : String,
    var dateOfBirth: String,
    var country : String
): Serializable
