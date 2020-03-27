package com.ajrobseyer.pokemonkotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.fragment.HeaderFragment
import com.ajrobseyer.pokemonkotlin.model.PokemonResponse
import com.ajrobseyer.pokemonkotlin.util.RestClient
import kotlinx.android.synthetic.main.fragment_header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService =
            RestClient.getRestClient()

        apiService.get20Pokemon().enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(
                call: Call<PokemonResponse>?,
                response: Response<PokemonResponse>?
            ) {
                response?.body().let {

                    val fragment = HeaderFragment.newInstance("Pedro")
                    supportFragmentManager.beginTransaction().replace(R.id.headFragment, fragment).commit()
                }
            }

            override fun onFailure(call: Call<PokemonResponse>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })

    }


}
