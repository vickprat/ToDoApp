package com.example.todoapp.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.todoapp.data.local.models.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun getToDoDao(): TodoDao

    companion object {
        var TEST_MODE = false
        private val databaseName = "tododatabase"
        private var todoDatabase: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            if (todoDatabase == null) {
                if (TEST_MODE) {
                    todoDatabase = Room.inMemoryDatabaseBuilder(context, TodoDatabase::class.java).allowMainThreadQueries().build()
                } else {
                    todoDatabase = Room.databaseBuilder(context, TodoDatabase::class.java, TodoDatabase.databaseName).build()
                }
            }
            return todoDatabase!!
        }

        fun close() {
            todoDatabase = null
        }
    }
}