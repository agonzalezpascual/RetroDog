package com.example.retrodog

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrodog.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener{ // 'OnQueryTextListener' = OnClickListener de un Button pero en SearchView

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onQueryTextChange(newText: String?): Boolean { // Método que nos avisa si el usuario ha hecho algún cambio mientras escribe en el SearchView
        return true
    }
    override fun onQueryTextSubmit(query: String?): Boolean { // Método que coge el texto del SearchView una vez dado al 'enter' o 'buscar'
        if(!query.isNullOrEmpty()){
            searchByName(query.toLowerCase())
        }
        return true
    }

    /*
    * Con 'ActivityMainBinding', tenemos la certeza de que existan dichos elementos del XML y poder manejarlos
    * sin que exista la opción de que sean 'null'
    *  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.svDogs.setOnQueryTextListener(this) // Añadimos los dos métodos anteriores aquí en el SearchView
        setContentView(binding.root)
        initRecyclerView()
    }
    // Función para iniciar el RecyclerView (tenemos que crear un adaptador)
    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
    }

    private fun getRetrofit():Retrofit{ // Primera libreria que añadimos del Retrofit
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/") // Añadimos la URL principal para ir añadiendo los Strings a dicha URL
            .addConverterFactory(GsonConverterFactory.create()) // 'GsonConverterFactory' = Retrofit-Converter
            .build()
    }

    //Esta función nos busca la raza del perro y nos devuelve las imágenes
    private fun searchByName(query:String){
        CoroutineScope(Dispatchers.IO).launch { // Creamos un hilo secundario ('Coroutine') para no detener la ejecución de la app
            val call = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            val puppies = call.body()
            runOnUiThread { // Con runOnUiThread, ponemos lo que está entre llaves en el hilo principal
                if(call.isSuccessful){ // Si existe la raza del SearchView, obtendremos una MutableList con todos los String de las imágenes de los perros
                    val images = puppies?.images ?: emptyList() // Si ocurre un fallo en la búsqueda de imágenes, será una lista vacía y no detendrá la ejecución por que sea null
                    dogImages.clear() // Si hay contenido, lo borramos
                    dogImages.addAll(images) // y cargamos la lista de las imagenes (en String)
                    adapter.notifyDataSetChanged()
                }else{
                    showError() // Salta un Toast si ocurre CUALQUIER error
                }
                hideKeyboard()
            }
        }
    }

    // Función para esconder el teclado del móvil tras introducir la raza en el SearchView y apretar 'enter'
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    // Función para mostrar un Toast si ocurre algún error en la app
    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}
