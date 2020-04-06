package com.ajrobseyer.pokemonkotlin.util

import android.content.Context
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog

class DialogManager {

    fun getLoadingDialog(
        context: Context,
        cancelable: Boolean?
    ): SweetAlertDialog? {
        val pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Cargando, por favor espera..."
        pDialog.setCancelable(cancelable!!)
        return pDialog
    }
}