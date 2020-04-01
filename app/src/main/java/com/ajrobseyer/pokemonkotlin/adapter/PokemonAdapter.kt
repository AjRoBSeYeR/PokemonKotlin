package com.ajrobseyer.pokemonkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import com.ajrobseyer.pokemonkotlin.util.RestClient
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.grid_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@GlideModule
class AppGlideModule : AppGlideModule()

class PokemonAdapter : BaseAdapter {
    private var pokemonList: ArrayList<PokemonBasicInfo>?
    var url: String=""
    var context:Context
    // pongo esta variable porque hay algunos pokemon que no traen los datos con el nombre y aesos les paso el id
    var pokemonName:String =""

    constructor(context: Context, pokemonList: ArrayList<PokemonBasicInfo>?) : super() {
        this.pokemonList = pokemonList
        this.context=context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View
        view = if(convertView == null){
            val inflater =
                parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.grid_item, null)
        }else{
            convertView
        }
        val pokemon = this.pokemonList!![position]

        view.pokemonName.text = pokemon.name
        val apiService =
            RestClient.getRestClient()

        // controlador de pokemnos qu eno traen datos con el nombre
        if(pokemon.name=="spearow"){
            pokemonName="21"
        }else{
            pokemonName=pokemon.name
        }

        apiService.getPokemonData(pokemonName).enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>?,
                response: Response<JsonObject>?
            ) {
                response?.body().let {
                    val sprites = it!!.getAsJsonObject("sprites")
                    url = sprites.getAsJsonPrimitive("front_default").asString
                    GlideApp
                        .with(context)
                        .load(url)
                        .into(view.pokemonImage)

                }
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })

        return view
    }

    fun changeData(list:ArrayList<PokemonBasicInfo>?){
        if(list!= null){
            pokemonList = list
            notifyDataSetChanged()
        }
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