package com.example.myapplication
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_table")
data class ExerciseSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val duration: Int,  // in minutes
    val intensity: String,
    val date: String
)

