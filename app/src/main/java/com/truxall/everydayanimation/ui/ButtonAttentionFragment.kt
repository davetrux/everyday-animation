package com.truxall.everydayanimation.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.ui.controls.ProgressButton

class ButtonAttentionFragment: Fragment() {

    private lateinit var morphButton: ProgressButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.button_attention_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.morphButton = view.findViewById(R.id.progress_button)
        this.morphButton.clearAnimation()
        this.morphButton.setOnClickListener {
            this.morphButton.startAnimation()
            val runnable = {
                //this.morphButton.stopAnimation()
                Toast.makeText(view.context, "Done", Toast.LENGTH_SHORT).show()
                // this.morphButton.revertAnimation()
            }
            Handler().postDelayed(runnable, 5000)
        }
    }
}