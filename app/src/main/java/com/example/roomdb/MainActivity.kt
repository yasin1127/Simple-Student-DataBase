package com.example.roomdb

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.roomdb.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var studentDatabase: StudentDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        studentDatabase = StudentDatabase.getData(this)

        binding.btn.setOnClickListener {
            saveData()
        }

        binding.btns.setOnClickListener {
            searchData()
        }

        binding.btnd.setOnClickListener {
            GlobalScope.launch {
                studentDatabase.studentDao().deleteAll()
            }
        }
    }

    private fun searchData() {
       val roll_No= binding.Search.text.toString()
        if(roll_No.isNotEmpty()){
        lateinit var student: Student
        GlobalScope.launch {
            student = studentDatabase.studentDao().findByRollNumber(roll_No.toInt())
            if(studentDatabase.studentDao().isEmpty()){
                Handler(Looper.getMainLooper()).post{
                    Toast.makeText(this@MainActivity, "Database haven't any data", Toast.LENGTH_SHORT).show()
                }
            }else{
                displayData(student)
            }
        }

        }

    }

    private suspend fun displayData(student: Student) {
    withContext(Dispatchers.Main){
        binding.fn.setText(student.firstName.toString())
        binding.fl.setText(student.lastName.toString())
        binding.roll.setText(student.rollNumber.toString())
    }

    }

    private fun saveData() {

        val firstName = binding.fn.text.toString()
        val lastName = binding.fl.text.toString()
        val rollNumber = binding.roll.text.toString()
        if(firstName.isNotEmpty() && lastName.isNotEmpty() && rollNumber.isNotEmpty()){
            val student = Student(0,firstName, lastName, rollNumber.toInt())
            GlobalScope.launch {
                studentDatabase.studentDao().insert(student)
            }
            binding.fn.text.clear()
            binding.fl.text.clear()
            binding.roll.text.clear()
            Toast.makeText(this@MainActivity, "Data saved successfully", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@MainActivity, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }
}