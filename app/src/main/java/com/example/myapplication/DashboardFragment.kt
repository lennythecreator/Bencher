package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {
    private lateinit var db: ExerciseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = ExerciseDatabase.getDatabase(requireActivity().application)

        val tvTotalSessions = view.findViewById<TextView>(R.id.tvTotalSessions)
        val tvAverageDuration = view.findViewById<TextView>(R.id.tvAverageDuration)
        val tvTotalMinutes = view.findViewById<TextView>(R.id.tvTotalMinutes)
        val tvMostCommonType = view.findViewById<TextView>(R.id.tvMostCommonType)

        db.exerciseDao().getAllSessions().observe(viewLifecycleOwner) { sessions ->
            if (sessions.isNotEmpty()) {
                // Total sessions
                tvTotalSessions.text = sessions.size.toString()

                // Average duration
                val avgDuration = sessions.map { it.duration }.average()
                tvAverageDuration.text = "%.1f mins".format(avgDuration)

                // Total minutes
                val totalMinutes = sessions.sumOf { it.duration }
                tvTotalMinutes.text = "$totalMinutes mins"

                // Most common exercise type
                val mostCommon = sessions.groupingBy { it.type }
                    .eachCount()
                    .maxByOrNull { it.value }?.key ?: "N/A"
                tvMostCommonType.text = mostCommon
            } else {
                tvTotalSessions.text = "0"
                tvAverageDuration.text = "0 mins"
                tvTotalMinutes.text = "0 mins"
                tvMostCommonType.text = "N/A"
            }
        }
    }
}