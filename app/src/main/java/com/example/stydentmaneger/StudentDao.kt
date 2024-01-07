package com.example.stydentmaneger
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Query("select * from students")
    fun getAllStudents(): List<Student>

    @Insert
    suspend fun insertStudent(student:Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

}