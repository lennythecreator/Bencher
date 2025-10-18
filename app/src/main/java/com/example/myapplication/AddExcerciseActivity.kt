package com.example.myapplication

import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ExerciseDatabase
import com.example.myapplication.ExerciseSession
import com.example.myapplication.R

class AddExerciseActivity : AppCompatActivity() {
    private lateinit var db: ExerciseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercise)
        db = ExerciseDatabase.getDatabase(this)

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val type = findViewById<EditText>(R.id.etType).text.toString()
            val duration = findViewById<EditText>(R.id.etDuration).text.toString().toIntOrNull() ?: 0
            val intensity = findViewById<EditText>(R.id.etIntensity).text.toString()
            val date = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())

            val session = ExerciseSession(type = type, duration = duration, intensity = intensity, date = date)

            Thread {
                db.exerciseDao().insert(session)
                runOnUiThread { finish() }
            }.start()
        }
    }
}
