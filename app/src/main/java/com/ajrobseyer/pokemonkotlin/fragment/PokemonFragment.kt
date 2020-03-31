package com.ajrobseyer.pokemonkotlin.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.adapter.PokemonAdapter
import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pokemon.*
import org.jetbrains.anko.support.v4.toast

// TODO: Rename parameter arguments, choose names that match

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
//private var viewCreated = false
var adapter: PokemonAdapter? = null
//var pokemonList: ArrayList<PokemonBasicInfo>? = null


class PokemonFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        // Inflate the layout for this fragment
        println("primero")
        return inflater.inflate(R.layout.fragment_pokemon, container, false)

    }

    /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         viewCreated = true
         onSendPokemonData(pokemonList)
     }*/

    fun onSendPokemonData(pokemonInfo: ArrayList<PokemonBasicInfo>?) {
        // pokemonList = pokemonInfo
        // if (viewCreated) {
        println("segundo")
        adapter = PokemonAdapter(pokemonInfo)
        pokemonGrid.adapter = adapter
        // }

    }


}

