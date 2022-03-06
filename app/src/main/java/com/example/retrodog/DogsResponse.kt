package com.example.retrodog

import com.google.gson.annotations.SerializedName
/*
* Esta Data Class nos sirve para obtener los datos del JSON (en este caso, es un ArrayList<String>
y un String)
*  */
data class DogsResponse(
    @SerializedName("status") var status: String, // @SeriaLizedName("_Aquí va el nombre identificativo del JSON_") ...
    @SerializedName("message") var images: List<String>
)
