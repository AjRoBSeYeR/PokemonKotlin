package com.ajrobseyer.pokemonkotlin.model.pokemondetails

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PokemonSprite(
    @SerializedName("front_default")
    val imageUrl: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonSprite> {
        override fun createFromParcel(parcel: Parcel): PokemonSprite {
            return PokemonSprite(parcel)
        }

        override fun newArray(size: Int): Array<PokemonSprite?> {
            return arrayOfNulls(size)
        }
    }
}