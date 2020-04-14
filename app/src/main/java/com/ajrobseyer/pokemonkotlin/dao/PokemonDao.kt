package com.ajrobseyer.pokemonkotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajrobseyer.pokemonkotlin.model.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM pokemon")
    fun getPokemon(): LiveData<List<PokemonEntity>>
}