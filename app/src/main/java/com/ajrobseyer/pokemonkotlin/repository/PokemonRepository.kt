package com.ajrobseyer.pokemonkotlin.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ajrobseyer.pokemonkotlin.dao.PokemonDao
import com.ajrobseyer.pokemonkotlin.model.PokemonDatabase
import com.ajrobseyer.pokemonkotlin.model.entity.PokemonEntity
import org.jetbrains.anko.doAsync

class PokemonRepository(application: Application) {
    private val pokemonDao: PokemonDao? = PokemonDatabase.getInstance(application)?.pokemonDao()

    fun getPokemon(): LiveData<List<PokemonEntity>> {
        return pokemonDao?.getPokemon()?: MutableLiveData<List<PokemonEntity>>()
    }

    fun insert(pokemonEntity: PokemonEntity) {
        doAsync {
            pokemonDao?.insert(pokemonEntity)
        }

    }
}