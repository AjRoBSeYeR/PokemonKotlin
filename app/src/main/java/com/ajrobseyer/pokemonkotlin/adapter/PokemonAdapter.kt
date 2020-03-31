package com.ajrobseyer.pokemonkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import kotlinx.android.synthetic.main.grid_item.view.*

class PokemonAdapter : BaseAdapter {
    private var pokemonList: ArrayList<PokemonBasicInfo>?

    constructor(pokemonList: ArrayList<PokemonBasicInfo>?) : super() {
        this.pokemonList = pokemonList
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val pokemon = this.pokemonList!![position]
        val inflater =
            parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.grid_item, null)
        view.pokemonName.text = pokemon.name
        return view
    }

    override fun getItem(position: Int): Any {
        return pokemonList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return pokemonList!!.size
    }

}