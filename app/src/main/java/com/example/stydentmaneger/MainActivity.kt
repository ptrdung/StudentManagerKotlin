package com.example.stydentmaneger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    companion object {
        const val INSERT_STUDENT_ACTIVITY_REQUEST_CODE = 123
        const val DETAIL_ACTIVITY_REQUEST_CODE = 124

    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private lateinit var studentDao: StudentDao
    private lateinit var db: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter(emptyList()){
                selectedStudent ->
            // Xử lý sự kiện khi một sinh viên được chọn
            navigateToDetailActivity(selectedStudent)
        }
        recyclerView.adapter = adapter

        db = Room.databaseBuilder(
            applicationContext,
            StudentDatabase::class.java, "student_db"
        ).build()

        studentDao = db.studentDao()

        lifecycleScope.launch {
            val students = withContext(Dispatchers.IO) {
                studentDao.getAllStudents()
            }
            adapter.setStudents(students)
            adapter.notifyDataSetChanged()
        }

    }

    private fun navigateToDetailActivity(selectedStudent: Student) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("student", selectedStudent)
        startActivityForResult(intent, DETAIL_ACTIVITY_REQUEST_CODE)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_menu -> {
                val intent = Intent(this, InsertStudentActivity::class.java)
                startActivityForResult(intent, INSERT_STUDENT_ACTIVITY_REQUEST_CODE)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == INSERT_STUDENT_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            updateRecyclerView()
        } else if (requestCode == DETAIL_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            updateRecyclerView()


        }
    }
    fun updateRecyclerView() {
        lifecycleScope.launch {
            val students = withContext(Dispatchers.IO) {
                studentDao.getAllStudents()
            }
            adapter.setStudents(students)
            adapter.notifyDataSetChanged()
        }
    }


}