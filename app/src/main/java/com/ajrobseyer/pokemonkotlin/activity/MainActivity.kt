package com.ajrobseyer.pokemonkotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.fragment.HeaderFragment
import com.ajrobseyer.pokemonkotlin.fragment.HeaderFragmentCommunication
import com.ajrobseyer.pokemonkotlin.fragment.PokemonFragment
import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import com.ajrobseyer.pokemonkotlin.model.entity.PokemonEntity
import com.ajrobseyer.pokemonkotlin.model.servicemodel.PokemonResponseServiceModel
import com.ajrobseyer.pokemonkotlin.model.viewmodel.PokemonViewModel
import com.ajrobseyer.pokemonkotlin.util.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), HeaderFragmentCommunication {

    private lateinit var headerFragment: HeaderFragment
    private lateinit var pokemonFragment: PokemonFragment
    private lateinit var pokemonViewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonViewModel = run {
            ViewModelProviders.of(this).get(PokemonViewModel::class.java)
        }

        val apiService =
            RestClient.getRestClient()

        apiService.get20Pokemon("0", getString(R.string.query_limit))
            .enqueue(object : Callback<PokemonResponseServiceModel> {
                override fun onResponse(
                    call: Call<PokemonResponseServiceModel>?,
                    response: Response<PokemonResponseServiceModel>?
                ) {
                    response?.body().let {
                        val equalPosition = it!!.nextPage.indexOf("=")
                        val ampersandPosition = it!!.nextPage.indexOf("&")
                        val upperLimit: Int =
                            it!!.nextPage.substring(equalPosition + 1, ampersandPosition).toInt()
                        val lowerLimit: Int = upperLimit - 19
                        val range = "$lowerLimit a $upperLimit"

                        populateOrUpdateDB(it)

                        headerFragment = HeaderFragment.newInstance(
                            this@MainActivity,
                            range,
                            it.count.toString(),
                            it.results as ArrayList<PokemonBasicInfo>
                        )
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.headContainer, headerFragment)
                            .commit()
                        pokemonFragment = PokemonFragment.newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.pokemonContainer, pokemonFragment)
                            .commit()
                    }
                }

                override fun onFailure(call: Call<PokemonResponseServiceModel>?, t: Throwable?) {
                    t?.printStackTrace()
                }
            })

    }

    private fun populateOrUpdateDB(it: PokemonResponseServiceModel) {
        val pokemons = it.results as ArrayList<PokemonBasicInfo>
        for (pokemon in pokemons) {
            val name = pokemon.name
            val splittedUrl = pokemon.url.split("/")
            val id = splittedUrl[6].toInt()
            pokemonViewModel.savePokemon(PokemonEntity(id,name))
        }

    }

    override fun dataExchange(info: ArrayList<PokemonBasicInfo>) {
        pokemonFragment.onSendPokemonData(
            info
        )
    }


}
