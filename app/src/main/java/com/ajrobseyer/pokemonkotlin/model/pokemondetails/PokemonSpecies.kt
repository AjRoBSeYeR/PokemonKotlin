package com.ajrobseyer.pokemonkotlin.model.pokemondetails

import android.os.Parcel
import android.os.Parcelable

data class PokemonSpecies(val name: String?): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonSpecies> {
        override fun createFromParcel(parcel: Parcel): PokemonSpecies {
            return PokemonSpecies(parcel)
        }

        override fun newArray(size: Int): Array<PokemonSpecies?> {
            return arrayOfNulls(size)
        }
    }
}