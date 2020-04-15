package com.ajrobseyer.pokemonkotlin.model.pokemondetails

import android.os.Parcel
import android.os.Parcelable

data class PokemonAbility(val name: String?): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonAbility> {
        override fun createFromParcel(parcel: Parcel): PokemonAbility {
            return PokemonAbility(
                parcel
            )
        }

        override fun newArray(size: Int): Array<PokemonAbility?> {
            return arrayOfNulls(size)
        }
    }
}