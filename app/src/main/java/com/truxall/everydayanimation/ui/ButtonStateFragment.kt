package com.truxall.everydayanimation.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.ui.controls.ProgressButton

class ButtonStateFragment : Fragment() {

    private lateinit var morphButton: ProgressButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.button_state_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.morphButton = view.findViewById(R.id.progress_button)
        this.morphButton.clearAnimation()
        this.morphButton.setOnClickListener {
            this.morphButton.startAnimation()
            val runnable = {
                this.morphButton.stopAnimation()
                Toast.makeText(view.context, "Done", Toast.LENGTH_SHORT).show()
                this.morphButton.revertAnimation()
            }
            Handler().postDelayed(runnable, 5000)
        }
    }
}
