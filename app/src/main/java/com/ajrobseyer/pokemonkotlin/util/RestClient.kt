package com.ajrobseyer.pokemonkotlin.util

import com.ajrobseyer.pokemonkotlin.service.ApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier

class RestClient {
    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"

        fun getRestClient(): ApiService {
            val retrofit:Retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder()
                            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                            .disableHtmlEscaping().create()
                    )
                )
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }

    }
}