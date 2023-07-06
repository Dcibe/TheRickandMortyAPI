package com.example.therickandmortyapi.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.therickandmortyapi.R

class PersonajesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.lblName)
    val species: TextView = itemView.findViewById(R.id.lbspecies)
    val img: ImageView = itemView.findViewById(R.id.imgLogo)
}

