package com.example.retrodog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DogAdapter(private val images: List<String>) : RecyclerView.Adapter<DogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder { // carga el 'item_dog.xml' en cada item del RecyclerView
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    override fun getItemCount(): Int = images.size // Devuelve tamaño de la lista de las imágenes


    override fun onBindViewHolder(holder: DogViewHolder, position: Int) { // Muestra los datos en una posición específica
        val item = images[position]
        holder.bind(item)
    }
}