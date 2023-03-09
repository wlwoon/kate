package com.wlwoon.kate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class KateFragment: Fragment(R.layout.fragment_kate) {

    fun newInstance(): KateFragment{
        val args = Bundle()
        val fragment = KateFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}