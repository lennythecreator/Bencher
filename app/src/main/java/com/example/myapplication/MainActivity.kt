package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ExerciseDatabase
import com.example.myapplication.ExerciseAdapter
import com.example.myapplication.R.id.fabAdd
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var db: ExerciseDatabase
    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = ExerciseDatabase.getDatabase(this)
        adapter = ExerciseAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.rvExercises)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        db.exerciseDao().getAllSessions().observe(this) { sessions ->
            adapter.setData(sessions)
            updateAverage(sessions)
        }

        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        fab.setOnClickListener {
            startActivity(Intent(this, AddExerciseActivity::class.java))
        }
    }

    private fun updateAverage(sessions: List<ExerciseSession>) {
        val avg = if (sessions.isNotEmpty()) {
            sessions.map { it.duration }.average()
        } else {
            0.0
        }
        findViewById<TextView>(R.id.tvAverage).text = "Average Duration: %.1f mins".format(avg)
    }
}