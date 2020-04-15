package com.ajrobseyer.pokemonkotlin.model.pokemondetails

import android.os.Parcel
import android.os.Parcelable

data class PokemonStatInfo(val stat: PokemonStat?): Parcelable {

    constructor(parcel: Parcel) : this(parcel.readParcelable<PokemonStat>(PokemonStat::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(stat, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonStatInfo> {
        override fun createFromParcel(parcel: Parcel): PokemonStatInfo {
            return PokemonStatInfo(parcel)
        }

        override fun newArray(size: Int): Array<PokemonStatInfo?> {
            return arrayOfNulls(size)
        }
    }
}