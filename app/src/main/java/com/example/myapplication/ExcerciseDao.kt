package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise_table ORDER BY id DESC")
    fun getAllSessions(): LiveData<List<ExerciseSession>>

    @Insert
    fun insert(session: ExerciseSession)
}