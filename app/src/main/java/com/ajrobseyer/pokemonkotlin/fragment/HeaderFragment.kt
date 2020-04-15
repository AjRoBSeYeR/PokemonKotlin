package com.ajrobseyer.pokemonkotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.model.PokemonBasicInfo
import com.ajrobseyer.pokemonkotlin.model.servicemodel.PokemonResponseServiceModel
import com.ajrobseyer.pokemonkotlin.util.DialogManager
import com.ajrobseyer.pokemonkotlin.util.RestClient
import kotlinx.android.synthetic.main.fragment_header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HeaderFragment(private val headerFragmentCommunication: HeaderFragmentCommunication) :
    Fragment() {
    private var countIni: String = "0"
    private var countTotal: String = "0"
    private lateinit var pokemonList: ArrayList<PokemonBasicInfo>
    private var offset: String = ""
    private var dialog: SweetAlertDialog? = null
    val manager = DialogManager()

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
                    putParcelableArrayList("lista", pokemonList)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            countIni = it.getString("countIni").toString()
            countTotal = it.getString("countTotal").toString()
            pokemonList = it.getParcelableArrayList<PokemonBasicInfo>("lista")!!
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

        if (context != null)
            dialog = manager.getLoadingDialog(context!!, false)

        tvCounter.text = String.format("%1\$s de %2\$s", countIni, countTotal)
        btnPrev.visibility = View.INVISIBLE

        btnPrev.setOnClickListener { fetchPage(false) }
        btnNext.setOnClickListener { fetchPage(true) }

        headerFragmentCommunication.dataInterchage(pokemonList)
    }

    private fun fetchPage(isNext: Boolean) {
        val parts = countIni.split(" ")
        offset =
            if (isNext) parts[2]
            else (parts[2].toInt() - 40).toString()
        val apiService =
            RestClient.getRestClient()

        apiService.get20Pokemon(offset, getString(R.string.query_limit))
            .enqueue(object : Callback<PokemonResponseServiceModel> {
                override fun onResponse(
                    call: Call<PokemonResponseServiceModel>?,
                    response: Response<PokemonResponseServiceModel>?
                ) {
                    response?.body().let {
                        pokemonList = it!!.results as ArrayList<PokemonBasicInfo>
                        val minValue =
                            if (isNext) parts[0].toInt() + 20
                            else parts[0].toInt() - 20
                        val maxValue =
                            if (isNext) parts[2].toInt() + 20
                            else parts[2].toInt() - 20

                        countIni = "$minValue a $maxValue"

                        tvCounter.text =
                            String.format(
                                getString(R.string.header_text),
                                minValue,
                                maxValue,
                                countTotal
                            )

                        btnPrev.visibility =
                            if (minValue == 1) View.INVISIBLE
                            else View.VISIBLE

                        btnNext.visibility =
                            if (minValue == countTotal.toInt() - 20) View.INVISIBLE
                            else View.VISIBLE

                        headerFragmentCommunication.dataInterchage(pokemonList)
                    }
                }

                override fun onFailure(
                    call: Call<PokemonResponseServiceModel>?,
                    t: Throwable?
                ) {
                    t?.printStackTrace()
                }
            })
    }
}

interface HeaderFragmentCommunication {
    fun dataInterchage(info: ArrayList<PokemonBasicInfo>)
}