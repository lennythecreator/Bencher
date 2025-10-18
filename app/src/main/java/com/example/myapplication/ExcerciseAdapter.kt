package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    private var sessions: List<ExerciseSession> = emptyList()

    fun setData(newSessions: List<ExerciseSession>) {
        sessions = newSessions
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(session: ExerciseSession) {
            itemView.findViewById<TextView>(R.id.tvType).text = session.type
            itemView.findViewById<TextView>(R.id.tvDuration).text = "${session.duration} mins"
            itemView.findViewById<TextView>(R.id.tvIntensity).text = "Intensity: ${session.intensity}"
            itemView.findViewById<TextView>(R.id.tvDate).text = session.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessions[position])
    }

    override fun getItemCount(): Int = sessions.size
}