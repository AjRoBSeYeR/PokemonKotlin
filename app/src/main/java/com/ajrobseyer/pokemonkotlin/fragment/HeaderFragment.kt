package com.ajrobseyer.pokemonkotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import kotlinx.android.synthetic.main.fragment_header.*


class HeaderFragment(private val headerFragmentCommunication: HeaderFragmentCommunication) :
    Fragment() {
    private var countIni: String = "0"
    private var countTotal: String = "0"
    private lateinit var pokemonList : ArrayList<PokemonBasicInfo>

    companion object {
        @JvmStatic
        fun newInstance(
            origin: HeaderFragmentCommunication,
            ini: String,
            total: String,
            pokemonList: ArrayList<PokemonBasicInfo>
        ) =
            HeaderFragment(origin).apply {
                arguments = Bundle().apply {
                    putString("countIni", ini)
                    putString("countTotal", total)
                    putSerializable("lista", pokemonList)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countIni = it.getString("countIni").toString()
            countTotal = it.getString("countTotal").toString()
            pokemonList = it.getSerializable("lista") as ArrayList<PokemonBasicInfo>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_header, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spacePosition = countIni.indexOf(" ")
        val upperLimit = countIni.substring(0, spacePosition)
        if (upperLimit.toInt() == 1) {
            btnPrev.visibility = View.INVISIBLE
        } else {
            btnPrev.visibility = View.VISIBLE
            btnPrev.setOnClickListener {
                //TODO logica para el botón
            }
        }

        if (upperLimit.toInt() == countTotal.toInt() - 19) {
            btnNext.visibility = View.INVISIBLE
        } else {
            btnNext.visibility = View.VISIBLE
            btnNext.setOnClickListener {

            }
        }

        tvCounter.text = "$countIni de $countTotal"

        headerFragmentCommunication.dataInterchage(
            pokemonList
        )

    }

}

interface HeaderFragmentCommunication {
    fun dataInterchage(info: ArrayList<PokemonBasicInfo>)
}
