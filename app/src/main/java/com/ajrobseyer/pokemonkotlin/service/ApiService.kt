package com.ajrobseyer.pokemonkotlin.service

import com.ajrobseyer.pokemonkotlin.model.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("pokemon/")
    fun get20Pokemon(): Call<PokemonResponse>
}