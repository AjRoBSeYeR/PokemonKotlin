package com.ajrobseyer.pokemonkotlin.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.activity.PokemonDetailsActivity
import com.ajrobseyer.pokemonkotlin.adapter.PokemonAdapter
import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import kotlinx.android.synthetic.main.fragment_pokemon.*

class PokemonFragment : Fragment() {

    var pokemonList: MutableLiveData<ArrayList<PokemonBasicInfo>> = MutableLiveData()

    companion object {
        @JvmStatic
        fun newInstance() = PokemonFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonList.observe(this,
            Observer {
                pokemonGrid.adapter = PokemonAdapter(
                    context!!,
                    it
                )
            })

        pokemonGrid.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(context, PokemonDetailsActivity::class.java).apply{
                putExtra("pkName",pokemonList.value?.get(position)?.name.toString() )
            }
            startActivity(intent)
        }
    }

    fun onSendPokemonData(pokemonInfo: ArrayList<PokemonBasicInfo>?) {
        pokemonList.value = pokemonInfo
    }
}

