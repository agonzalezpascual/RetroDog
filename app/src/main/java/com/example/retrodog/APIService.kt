package com.example.retrodog

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
/*
* Esta interfaz creara el método para que podamos acceder a nuestra API
*  */
interface APIService {
    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse>
    // Creamos esta función para pasar la raza por url y asi obtener el listado de imagenes de dicha raza
    // y el mensaje de 'status'
    // DATO: añadimos 'suspend' a las funciones que queramos que sean asíncronas, es decir, que no
    // interrumpen la acción principal del Activity
}