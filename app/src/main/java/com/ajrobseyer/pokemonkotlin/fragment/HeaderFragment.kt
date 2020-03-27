package com.ajrobseyer.pokemonkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ajrobseyer.pokemonkotlin.R
import com.ajrobseyer.pokemonkotlin.model.PokemonResponse
import com.ajrobseyer.pokemonkotlin.util.RestClient
import kotlinx.android.synthetic.main.fragment_header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"


class HeaderFragment : Fragment() {
    private var param1: String? = ""


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            HeaderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
                return HeaderFragment()
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
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
        val apiService =
            RestClient.getRestClient()

        apiService.get20Pokemon().enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(
                call: Call<PokemonResponse>?,
                response: Response<PokemonResponse>?
            ) {
                response?.body().let {
                    var currentCount = 0
                    var totalCount = 0
                    val data: PokemonResponse? =response!!.body()
                    tvCounter.text = "$currentCount / $totalCount"

                   //val fragment = HeaderFragment.newInstance("Pedro")
                   //supportFragmentManager.beginTransaction().replace(R.id.headFragment, fragment).commit()
                }
            }

            override fun onFailure(call: Call<PokemonResponse>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })

    }
}
