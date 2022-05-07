package com.feelingsdiary.feelingsdiaryscripts

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feelingssdiary.R
import java.text.DateFormat

class Adapter (context: Context, entries : SharedPreferences) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    val ourContext = context
    val sharedEntries = entries

    val extractedEntries: Map<String, *> = sharedEntries.all
    var ourEntries : List<String> = extractedEntries.toList().map {"(${it.first}, ${it.second})"}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i("at_adapter", "oncreate")
        return ViewHolder(LayoutInflater.from(ourContext).inflate(R.layout.entry_view, parent, false))
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        var entry = ourEntries[position].replace("(","")
        entry = entry.replace(")", "")
        val splitEntry = entry.split(", ")
        Log.i("at_adapter", entry)
        holder.entrytitle.text = splitEntry[1]
        holder.entry.text = splitEntry[2]
        if (splitEntry.size > 2) {
            Log.i("the_date", splitEntry[0])
            holder.date.text = DateFormat.getDateTimeInstance().format(splitEntry[0].toFloat())
        }
    }

    override fun getItemCount(): Int = ourEntries.size

    class ViewHolder(entryView: View) : RecyclerView.ViewHolder(entryView) {
        val entrytitle : TextView = itemView.findViewById<TextView>(R.id.displayentrytitle)
        val entry : TextView = itemView.findViewById<TextView>(R.id.displayentry)
        val date : TextView = itemView.findViewById<TextView>(R.id.displaytime)
    }
}