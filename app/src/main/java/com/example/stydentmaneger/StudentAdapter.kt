package com.example.stydentmaneger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private var studentList:List<Student>, private val onItemClick: (Student) -> Unit): RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewMSSV: TextView = itemView.findViewById(R.id.mssv)
        val textViewFullName: TextView = itemView.findViewById(R.id.student_name)
        val textViewEmail: TextView = itemView.findViewById(R.id.email)
    }

    fun setStudents(studentList: List<Student>) {
        this.studentList = studentList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = studentList[position]
        holder.textViewMSSV.text = "${student.mssv}"
        holder.textViewFullName.text = "${student.fullName}"
        holder.textViewEmail.text = "${generateEmail(student.mssv, student.fullName)}"
        holder.itemView.setOnClickListener {
            onItemClick.invoke(student)


        }
    }

    private fun generateEmail(mssv: String, fullName: String): String {
        val fullNameParts = fullName.split(" ")
        val lastName = fullNameParts.lastOrNull() ?: ""

        val middleNameInitials = if (fullNameParts.size > 2) {
            fullNameParts.subList(1, fullNameParts.size - 1)
                .joinToString("") { it.firstOrNull()?.toString() ?: "" }
        } else {
            ""
        }

        val firstNameInitial = fullNameParts.firstOrNull()?.firstOrNull()?.toString() ?: ""

        val last6Digits = mssv.takeIf { it.length >= 6 }?.takeLast(6) ?: ""

        val email = "$lastName.$firstNameInitial$middleNameInitials$last6Digits@sis.hust.edu.vn"


        return email
    }




}