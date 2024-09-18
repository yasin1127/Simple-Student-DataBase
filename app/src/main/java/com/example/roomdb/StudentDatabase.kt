package com.example.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase:RoomDatabase() {
    abstract fun studentDao(): Studentdao
    companion object {
        @Volatile
        private var INSTANT:StudentDatabase?=null

        fun getData(context: Context):StudentDatabase{
            val tempInstance = INSTANT
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, StudentDatabase::class.java, "student_database").build()
                INSTANT = instance
                return instance
            }
        }
    }
}