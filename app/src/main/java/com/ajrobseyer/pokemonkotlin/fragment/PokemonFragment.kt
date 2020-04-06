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


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var pokemonList: MutableLiveData<ArrayList<PokemonBasicInfo>> =
    MutableLiveData<ArrayList<PokemonBasicInfo>>()


class PokemonFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance() =
            PokemonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        pokemonGrid.setOnItemClickListener { parent, view, position, id ->
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

