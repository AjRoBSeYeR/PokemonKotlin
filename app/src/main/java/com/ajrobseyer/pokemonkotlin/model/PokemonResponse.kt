package com.ajrobseyer.pokemonkotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PokemonResponse : Serializable {
    val count:Int=0
    @SerializedName("next")
    val nextPage:String=""
    val previuos: String = ""
    val results: List<PokemonBasicInfo>? = null
}