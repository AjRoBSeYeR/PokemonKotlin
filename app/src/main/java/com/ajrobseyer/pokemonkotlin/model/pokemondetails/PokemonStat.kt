package com.ajrobseyer.pokemonkotlin.model.pokemondetails

import android.os.Parcel
import android.os.Parcelable

data class PokemonStat(val name: String?): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonStat> {
        override fun createFromParcel(parcel: Parcel): PokemonStat {
            return PokemonStat(parcel)
        }

        override fun newArray(size: Int): Array<PokemonStat?> {
            return arrayOfNulls(size)
        }
    }
}