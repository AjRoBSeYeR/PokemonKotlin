package com.ajrobseyer.pokemonkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ajrobseyer.pokemonkotlin.R
import kotlinx.android.synthetic.main.fragment_header.*
import org.jetbrains.anko.support.v4.toast


class HeaderFragment : Fragment() {
    private var countIni: String = "0"
    private var countTotal: String = "0"


    companion object {
        @JvmStatic
        fun newInstance(ini: String, total: String) =
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
            countIni = it.getString("countIni").toString()
            countTotal = it.getString("countTotal").toString()
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
        }else{
            btnPrev.visibility = View.VISIBLE
        }

        tvCounter.text = "$countIni de $countTotal"

        btnPrev.setOnClickListener {

        }

    }
}
