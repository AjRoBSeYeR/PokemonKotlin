package com.ajrobseyer.pokemonkotlin.model.pokemondetails

import android.os.Parcel
import android.os.Parcelable

data class PokemonDetail(
    val sprites: PokemonSprite?,
    val abilities: ArrayList<PokemonAbilityInfo>?,
    val moves: ArrayList<PokemonMoveInfo>?,
    val stats: ArrayList<PokemonStatInfo>?,
    val species: PokemonSpecies?): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(PokemonSprite::class.java.classLoader),
        parcel.createTypedArrayList(PokemonAbilityInfo),
        parcel.createTypedArrayList(PokemonMoveInfo),
        parcel.createTypedArrayList(PokemonStatInfo),
        parcel.readParcelable(PokemonSpecies::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(sprites, flags)
        parcel.writeTypedList(abilities)
        parcel.writeTypedList(moves)
        parcel.writeTypedList(stats)
        parcel.writeParcelable(species, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonDetail> {
        override fun createFromParcel(parcel: Parcel): PokemonDetail {
            return PokemonDetail(parcel)
        }

        override fun newArray(size: Int): Array<PokemonDetail?> {
            return arrayOfNulls(size)
        }
    }

}