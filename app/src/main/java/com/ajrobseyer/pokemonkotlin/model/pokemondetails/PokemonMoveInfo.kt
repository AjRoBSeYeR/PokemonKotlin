package com.ajrobseyer.pokemonkotlin.model.pokemondetails

import android.os.Parcel
import android.os.Parcelable

data class PokemonMoveInfo(val move: PokemonMove?): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readParcelable<PokemonMove>(PokemonMove::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(move, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonMoveInfo> {
        override fun createFromParcel(parcel: Parcel): PokemonMoveInfo {
            return PokemonMoveInfo(parcel)
        }

        override fun newArray(size: Int): Array<PokemonMoveInfo?> {
            return arrayOfNulls(size)
        }
    }
}