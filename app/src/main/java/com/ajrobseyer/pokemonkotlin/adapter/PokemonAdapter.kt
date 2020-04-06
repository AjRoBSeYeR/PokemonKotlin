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
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.grid_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@GlideModule
class AppGlideModule : AppGlideModule()
class ViewHolder(itemView: View?) {
    var lblPokemonName: TextView? = null
    var ivPokemonImage: ImageView? = null

    init{
        this.lblPokemonName = itemView?.pokemonName
        this.ivPokemonImage = itemView?.pokemonImage
    }
}

class PokemonAdapter : BaseAdapter {
    private var pokemonList: ArrayList<PokemonBasicInfo>?
    var url: String = ""
    var context: Context
    private lateinit var dialog: SweetAlertDialog

    // pongo esta variable porque hay algunos pokemon que no traen los datos con el nombre y aesos les paso el id
    var pokemonName: String = ""

    constructor(context: Context, pokemonList: ArrayList<PokemonBasicInfo>?) : super() {
        this.pokemonList = pokemonList
        this.context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        val manager = DialogManager()

        dialog = manager.getLoadingDialog(context, false)!!
        dialog.show()
        if (convertView == null) {
            val inflater =
                parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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
        if (pokemon.name == "spearow") {
            pokemonName = "21"
        } else {
            pokemonName = pokemon.name
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



    /*fun changeData(list: ArrayList<PokemonBasicInfo>?) {
        if (list != null) {
            pokemonList = list
            notifyDataSetChanged()
        }
    }*/




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