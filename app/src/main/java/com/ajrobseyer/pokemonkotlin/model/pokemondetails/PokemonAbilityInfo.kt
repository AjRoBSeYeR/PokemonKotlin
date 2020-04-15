package com.ajrobseyer.pokemonkotlin.model.pokemondetails

import android.os.Parcel
import android.os.Parcelable

data class PokemonAbilityInfo(val ability: PokemonAbility?): Parcelable {
    constructor(parcel: Parcel) :
            this(parcel.readParcelable<PokemonAbility>(PokemonAbility::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(ability, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonAbilityInfo> {
        override fun createFromParcel(parcel: Parcel): PokemonAbilityInfo {
            return PokemonAbilityInfo(parcel)
        }

        override fun newArray(size: Int): Array<PokemonAbilityInfo?> {
            return arrayOfNulls(size)
        }
    }
}