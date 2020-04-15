package com.ajrobseyer.pokemonkotlin.activity

import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.adapter.PokemonDetailsAdapter
import com.ajrobseyer.pokemonkotlin.model.pokemondetails.PokemonDetail
import com.ajrobseyer.pokemonkotlin.util.RestClient
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsActivity : AppCompatActivity() {

    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: MutableList<String>? = null

    private val data: HashMap<String, List<String>> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        btnBack.setOnClickListener { super.onBackPressed() }

        val pokemonName: String

        tvName.text = intent.getStringExtra("pkName")

        // controlador de pokemon que no traen datos con el nombre
        if (tvName.text == "spearow") {
            pokemonName = "21"
        } else {
            pokemonName = intent.getStringExtra("pkName")!!
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
                    val pokemonDetail = Gson().fromJson(it!!.toString(), PokemonDetail::class.java)

                    Glide
                        .with(applicationContext)
                        .load(pokemonDetail.sprites?.imageUrl)
                        .into(pokemonPhoto)

                    populateTitleList(pokemonDetail)
                    populateChilds(pokemonDetail)

                    populateExpandableList()
                }
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }

    private fun populateExpandableList() {
        adapter = PokemonDetailsAdapter(this, titleList as ArrayList<String>, data)
        expandableList!!.setAdapter(adapter)
    }

    private fun populateTitleList(detail: PokemonDetail) {
        titleList = ArrayList()

        if (!detail.abilities.isNullOrEmpty())
            titleList?.add(getString(R.string.abilities))

        if (!detail.moves.isNullOrEmpty())
            titleList?.add(getString(R.string.moves))

        if (detail.species != null)
            titleList?.add(getString(R.string.species))

        if (!detail.stats.isNullOrEmpty())
            titleList?.add(getString(R.string.stats))
    }

    private fun populateChilds(detail: PokemonDetail): HashMap<String, List<String>> {
        val abilitiesList: ArrayList<String> = ArrayList()
        val movesList: ArrayList<String> = ArrayList()
        val speciesList: ArrayList<String> = ArrayList()
        val statsList: ArrayList<String> = ArrayList()

        if (!detail.abilities.isNullOrEmpty())
            for (abilityInfo in detail.abilities) {
                if (abilityInfo.ability != null)
                    abilitiesList.add(abilityInfo.ability.name ?: "Unknown ability")//poner en strings.xml
            }
        data[getString(R.string.abilities)] = abilitiesList

        if (!detail.moves.isNullOrEmpty())
            for (moveInfo in detail.moves) {
                if (moveInfo.move != null)
                    movesList.add(moveInfo.move.name ?: "Unknown move")//poner en strings.xml
            }
        data[getString(R.string.moves)] = movesList

        if (!detail.stats.isNullOrEmpty())
            for (statInfo in detail.stats)
                if (statInfo.stat != null)
                    statsList.add(statInfo.stat.name ?: "Unknown stat")//poner en strings.xml

        data[getString(R.string.stats)] = statsList

        if (detail.species != null) {
            speciesList.add(detail.species.name ?: "Unknown Pokemon")//poner en strings.xml
            data[getString(R.string.species)] = speciesList
        }

        return data
    }
}
