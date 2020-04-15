package com.ajrobseyer.pokemonkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import com.ajrobseyer.pokemonkotlin.util.DialogManager
import com.ajrobseyer.pokemonkotlin.util.RestClient
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.grid_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewHolder(itemView: View?) {
    var lblPokemonName: TextView? = null
    var ivPokemonImage: ImageView? = null

    init {
        this.lblPokemonName = itemView?.pokemonName
        this.ivPokemonImage = itemView?.pokemonImage
    }
}

class PokemonAdapter(val context: Context, val pokemonList: ArrayList<PokemonBasicInfo>?) :
    BaseAdapter() {

    var url: String = ""
    private lateinit var dialog: SweetAlertDialog

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        val manager = DialogManager()

        dialog = manager.getLoadingDialog(context, false)!!
        dialog.show()
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.grid_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val pokemon = this.pokemonList!![position]

        viewHolder.lblPokemonName?.text = pokemon.name

        val apiService =
            RestClient.getRestClient()

        // controlador de pokemon que no traen datos con el nombre
        val pokemonId: String
        if (pokemon.name == "spearow") {
            pokemonId = "21"
        } else {
            pokemonId = if (pokemon.name != null) pokemon.name!!
            else ""
        }

        apiService.getPokemonData(pokemonId).enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>?,
                response: Response<JsonObject>?
            ) {
                response?.body().let {
                    val sprites = it!!.getAsJsonObject("sprites")
                    url = sprites.getAsJsonPrimitive("front_default").asString
                    Glide
                        .with(context)
                        .load(url)
                        .into(viewHolder.ivPokemonImage!!)
                }
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })

        dialog.dismissWithAnimation()

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