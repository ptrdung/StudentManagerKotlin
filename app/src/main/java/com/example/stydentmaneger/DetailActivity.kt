package com.example.stydentmaneger

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var db: StudentDatabase
    private lateinit var studentDao: StudentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        db = Room.databaseBuilder(
            applicationContext,
            StudentDatabase::class.java, "student_db"
        ).build()

        studentDao = db.studentDao()
        val student = intent.getSerializableExtra("student") as? Student
        if (student != null) {
            findViewById<TextView>(R.id.detail_mssv).text = student.mssv
            findViewById<TextView>(R.id.detail_fullname).text = student.fullName
            findViewById<TextView>(R.id.detail_date).text= student.dateOfBirth
            findViewById<TextView>(R.id.detail_country).text = student.country

        }
        findViewById<Button>(R.id.btn_delete).setOnClickListener(){
            lifecycleScope.launch {

                val students = withContext(Dispatchers.IO) {
                    if (student != null) {
                        studentDao.deleteStudent(student)
                    }

                }
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
        findViewById<Button>(R.id.btn_update).setOnClickListener(){
            lifecycleScope.launch {

                val students = withContext(Dispatchers.IO) {
                    if (student != null) {
                        student.mssv = findViewById<EditText>(R.id.detail_mssv).text.toString()
                        student.fullName = findViewById<EditText>(R.id.detail_fullname).text.toString()
                        student.dateOfBirth = findViewById<EditText>(R.id.detail_date).text.toString()
                        student.country = findViewById<EditText>(R.id.detail_country).text.toString()


                        studentDao.updateStudent(student)
                    }

                }
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

    }
}