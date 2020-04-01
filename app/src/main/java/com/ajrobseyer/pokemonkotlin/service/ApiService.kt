package com.ajrobseyer.pokemonkotlin.service

import com.ajrobseyer.pokemonkotlin.model.servicemodel.PokemonResponseServiceModel
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    fun get20Pokemon(
        @Query("offset") offset: String,
        @Query("limit") limit: String
    ): Call<PokemonResponseServiceModel>

    @GET("pokemon/{pokemonName}")
    fun getPokemonData(@Path("pokemonName") pokemonName: String): Call<JsonObject>
}