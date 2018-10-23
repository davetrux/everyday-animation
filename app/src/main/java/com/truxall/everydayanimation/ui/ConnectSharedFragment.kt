package com.truxall.everydayanimation.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.truxall.everydayanimation.R

class ConnectSharedFragment : Fragment() {

    private lateinit var viewModel: ConnectSharedViewModel
    private var containingView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect_shared, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConnectSharedViewModel::class.java)


    }


    companion object {
        fun newInstance() = ConnectSharedFragment()
    }
}
