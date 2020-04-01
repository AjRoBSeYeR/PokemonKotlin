package com.ajrobseyer.pokemonkotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.adapter.GlideApp
import com.ajrobseyer.pokemonkotlin.fragment.HeaderFragment
import com.ajrobseyer.pokemonkotlin.fragment.HeaderFragmentCommunication
import com.ajrobseyer.pokemonkotlin.fragment.PokemonFragment
import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import com.ajrobseyer.pokemonkotlin.model.servicemodel.PokemonResponseServiceModel
import com.ajrobseyer.pokemonkotlin.util.RestClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.grid_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), HeaderFragmentCommunication {

    private lateinit var headerFragment: HeaderFragment
    private lateinit var pokemonFragment: PokemonFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService =
            RestClient.getRestClient()

        apiService.get20Pokemon().enqueue(object : Callback<PokemonResponseServiceModel> {
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

                    headerFragment = HeaderFragment.newInstance(
                        this@MainActivity,
                        range,
                        it.count.toString(),
                        it.results as ArrayList<PokemonBasicInfo>
                    )
                    supportFragmentManager.beginTransaction().replace(R.id.headContainer, headerFragment)
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

    override fun dataInterchage(info: ArrayList<PokemonBasicInfo>) {
        pokemonFragment.onSendPokemonData(
          info
        )
    }



}
