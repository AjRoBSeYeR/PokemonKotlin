package com.ajrobseyer.pokemonkotlin.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity (
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String
)