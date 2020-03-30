package com.ajrobseyer.pokemonkotlin.model.servicemodel

import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PokemonResponseServiceModel : Serializable {
    val count:Int=0
    @SerializedName("next")
    val nextPage:String=""
    val previuos: String = ""
    val results: List<PokemonBasicInfo>? = null


}