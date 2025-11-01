package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExerciseListFragment : Fragment() {
    private lateinit var db: ExerciseDatabase
    private lateinit var adapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = ExerciseDatabase.getDatabase(requireActivity().application)
        adapter = ExerciseAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvExercises)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        db.exerciseDao().getAllSessions().observe(viewLifecycleOwner) { sessions ->
            adapter.setData(sessions)
            updateAverage(view, sessions)
        }

        val fab = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        fab.setOnClickListener {
            startActivity(Intent(requireContext(), AddExerciseActivity::class.java))
        }
    }

    private fun updateAverage(view: View, sessions: List<ExerciseSession>) {
        val avg = if (sessions.isNotEmpty()) {
            sessions.map { it.duration }.average()
        } else {
            0.0
        }
        view.findViewById<TextView>(R.id.tvAverage).text = "Average Duration: %.1f mins".format(avg)
    }
}