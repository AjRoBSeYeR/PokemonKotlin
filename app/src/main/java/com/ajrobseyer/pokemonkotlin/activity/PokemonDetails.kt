package com.ajrobseyer.pokemonkotlin.activity

import android.os.Bundle
import android.widget.ExpandableListAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.adapter.GlideApp
import com.ajrobseyer.pokemonkotlin.adapter.PokemonDetailsAdapter
import com.ajrobseyer.pokemonkotlin.util.RestClient
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetails : AppCompatActivity() {

    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: MutableList<String>? = null

    val data: HashMap<String, List<String>> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        var pokemonName = ""

        tvName.text = intent.getStringExtra("pkName")

        // controlador de pokemon que no traen datos con el nombre
        if (tvName.text == "spearow") {
            pokemonName = "21"
        } else {
            pokemonName = intent.getStringExtra("pkName")
        }

        //llamada a servicio que eliminaremos al implementar SQLite
        val apiService =
            RestClient.getRestClient()

        apiService.getPokemonData(pokemonName).enqueue(object :
            Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>?,
                response: Response<JsonObject>?
            ) {
                response?.body().let {
                    val sprites = it!!.getAsJsonObject("sprites")
                    val url = sprites.getAsJsonPrimitive("front_default").asString

                    titleList = mutableListOf()
                    GlideApp
                        .with(applicationContext)
                        .load(url)
                        .into(pokemonPhoto)

                    for (s in it.keySet()) {
                        when (s) {
                            "abilities" -> titleList?.add("Habilidades")
                            "moves" -> titleList?.add("Movimientos")
                            "species" -> titleList?.add("Especies")
                            "stats" -> titleList?.add("Estadísticas")
                        }

                    }
                    fillExpandableList()
                }
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }

    private fun fillExpandableList() {
        val listData = data
        adapter = PokemonDetailsAdapter(this, titleList as ArrayList<String>, listData)
        expandableList!!.setAdapter(adapter)

        expandableList!!.setOnGroupExpandListener { groupPosition ->
            Toast.makeText(
                applicationContext,
                (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                Toast.LENGTH_SHORT
            ).show()
        }

        expandableList!!.setOnGroupCollapseListener { groupPosition ->
            Toast.makeText(
                applicationContext,
                (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                Toast.LENGTH_SHORT
            ).show()
        }

        expandableList!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            Toast.makeText(
                applicationContext,
                "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(
                    childPosition
                ),
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }
}
