package com.ajrobseyer.pokemonkotlin.model.pokemondetails

import android.os.Parcel
import android.os.Parcelable

data class PokemonMove(val name: String?): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonMove> {
        override fun createFromParcel(parcel: Parcel): PokemonMove {
            return PokemonMove(parcel)
        }

        override fun newArray(size: Int): Array<PokemonMove?> {
            return arrayOfNulls(size)
        }
    }
}