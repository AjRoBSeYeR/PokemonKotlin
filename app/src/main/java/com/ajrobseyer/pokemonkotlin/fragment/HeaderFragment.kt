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
    private lateinit var dialog: SweetAlertDialog
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

        dialog = manager.getLoadingDialog(context!!, false)!!

        btnPrev.visibility = View.INVISIBLE

        btnPrev.setOnClickListener {
            val parts = countIni.split(" ")
            offset = (parts[2].toInt() - 40).toString()
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
                            countIni = "${(parts[0].toInt() - 20)} a ${parts[2].toInt() - 20}"
                            tvCounter.text = "$countIni de $countTotal"
                            val parts = countIni.split(" ")
                            if (parts[0].toInt() == 1) {
                                btnPrev.visibility = View.INVISIBLE
                            } else {
                                btnPrev.visibility = View.VISIBLE
                            }
                            headerFragmentCommunication.dataExchange(pokemonList)
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

        btnNext.setOnClickListener {
            val parts = countIni.split(" ")
            offset = parts[2]
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
                            countIni = "${(parts[0].toInt() + 20)} a ${parts[2].toInt() + 20}"
                            tvCounter.text = "$countIni de $countTotal"
                            val parts = countIni.split(" ")
                            btnPrev.visibility = View.VISIBLE
                            if (parts[0].toInt() == countTotal.toInt() - 20) {
                                btnNext.visibility = View.INVISIBLE
                            } else {
                                btnNext.visibility = View.VISIBLE
                            }
                            headerFragmentCommunication.dataExchange(pokemonList)
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

        tvCounter.text = "$countIni de $countTotal"

        headerFragmentCommunication.dataExchange(
            pokemonList
        )

    }

}

interface HeaderFragmentCommunication {
    fun dataExchange(info: ArrayList<PokemonBasicInfo>)
}
