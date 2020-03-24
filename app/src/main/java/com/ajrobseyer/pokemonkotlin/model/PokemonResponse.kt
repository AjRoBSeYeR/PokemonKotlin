package com.ajrobseyer.pokemonkotlin.model

import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import com.google.gson.annotations.SerializedName

class PokemonResponse {
    val count:Int=0
    @SerializedName("next")
    val nextPage:String=""
    val previuos: String = ""
    val results: List<PokemonBasicInfo>? = null
}