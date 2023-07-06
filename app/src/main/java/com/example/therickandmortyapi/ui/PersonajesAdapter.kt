package com.example.therickandmortyapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.therickandmortyapi.R
import com.example.therickandmortyapi.model.Personajes_model

// Adaptador para el RecyclerView que muestra la lista de personajes
class PersonajesAdapter : RecyclerView.Adapter<PersonajesViewHolder>() {
    private val personajesList = ArrayList<Personajes_model>()

    // Función de clic en el elemento de la lista
    private var onItemClickListener: ((Personajes_model) -> Unit)? = null

    // Método que se llama cuando se necesita crear una nueva vista para un elemento de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajesViewHolder {
        // Crear el diseño del elemento de la lista utilizando un LayoutInflater
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pesonajes_item, parent, false)
        // Crear y devolver un nuevo ViewHolder para la vista
        return PersonajesViewHolder(view)
    }

    // Método que devuelve la cantidad de elementos en la lista
    override fun getItemCount(): Int {
        return personajesList.size
    }

    // Método que se llama cuando se necesita vincular los datos de un personaje con la vista correspondiente
    override fun onBindViewHolder(holder: PersonajesViewHolder, position: Int) {
        // Obtener el personaje en la posición dada
        val personaje = personajesList[position]

        // Establecer los datos del personaje en la vista del ViewHolder
        holder.name.text = personaje.name
        holder.species.text = personaje.species

        // Cargar la imagen del personaje utilizando la biblioteca Glide
        Glide.with(holder.itemView.context)
            .load(personaje.image)
            .placeholder(R.drawable.api)
            .into(holder.img)

        // Configurar el clic en la vista del elemento
        holder.itemView.setOnClickListener {
            // Invocar la función de clic y pasar el personaje seleccionado como argumento
            onItemClickListener?.invoke(personaje)
        }
    }

    // Método para establecer el listener para el clic en el elemento
    fun setOnItemClickListener(listener: (Personajes_model) -> Unit) {
        onItemClickListener = listener
    }

    // Método para actualizar la lista de personajes con una nueva lista
    fun update(lista: List<Personajes_model>) {
        personajesList.clear()
        personajesList.addAll(lista)
        // Notificar al adaptador que los datos han cambiado para que actualice la vista
        notifyDataSetChanged()
    }

    // Método para obtener un personaje en una posición específica
    fun getItem(position: Int): Personajes_model {
        return personajesList[position]
    }
}
