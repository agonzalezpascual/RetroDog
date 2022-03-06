package com.example.retrodog

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.retrodog.databinding.ItemDogBinding
import com.squareup.picasso.Picasso
/*
* Clase que utiliza el paquete 'Picasso' para convertir las URL en imagenes y cargarlas en 'item_dog.xml'
*  */
class DogViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemDogBinding.bind(view)
    fun bind(image:String){
        Picasso.get().load(image).into(binding.ivDog)
    }
}