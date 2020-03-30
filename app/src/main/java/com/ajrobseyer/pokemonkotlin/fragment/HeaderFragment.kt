package com.ajrobseyer.pokemonkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ajrobseyer.pokemonkotlin.R
import kotlinx.android.synthetic.main.fragment_header.*


class HeaderFragment : Fragment() {
    private var countIni: String? = "0"
    private var countTotal: String? = "0"


    companion object {
        @JvmStatic
        fun newInstance(ini: String, total:String) =
            HeaderFragment().apply {
                arguments = Bundle().apply {
                    putString("countIni", ini)
                    putString("countTotal", total)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countIni = it.getString("countIni")
            countTotal=it.getString("countTotal")
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

        tvCounter.text = "$countIni de $countTotal"

    }
}
