package com.challenge.mercadolibre.core.utilities

import android.app.Dialog
import android.app.SearchableInfo
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.SearchView
import com.challenge.mercadolibre.databinding.DialogSearchBinding

class SearchDialog(
    private val actualQuery: String,
    private val searchableInfo: SearchableInfo,
    context: Context
) :
    Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogSearchBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        val lp = WindowManager.LayoutParams()
        val window = window
        lp.copyFrom(window!!.attributes)
        //This makes the dialog take up the full width
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = lp

        binding.svQuery.setSearchableInfo(searchableInfo)
        binding.svQuery.setQuery(actualQuery, false)
    }


}