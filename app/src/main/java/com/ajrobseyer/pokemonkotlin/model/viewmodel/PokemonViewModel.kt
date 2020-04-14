package com.ajrobseyer.pokemonkotlin.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ajrobseyer.pokemonkotlin.model.entity.PokemonEntity
import com.ajrobseyer.pokemonkotlin.repository.PokemonRepository

class PokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PokemonRepository(application)
    val pokemon = repository.getPokemon()

    fun savePokemon(pokemonEntity: PokemonEntity) {
        repository.insert(pokemonEntity)
    }

}