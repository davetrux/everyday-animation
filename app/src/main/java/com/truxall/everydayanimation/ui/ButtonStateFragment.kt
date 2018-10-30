package com.truxall.everydayanimation.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.truxall.everydayanimation.R
import com.truxall.everydayanimation.ui.controls.CircularProgressButton

class ButtonStateFragment : Fragment() {

    private lateinit var morphButton: CircularProgressButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.button_state_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.morphButton = view.findViewById(R.id.morph_button)
        this.morphButton.clearAnimation()
        this.morphButton.setOnClickListener {
            //Toast.makeText(view.context, "Tapped", Toast.LENGTH_SHORT).show()
            this.morphButton.startAnimation()
//            val runnable = {
//                morphButton.stopAnimation()

//                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
//                        context,
//                        Pair<View, String>(morphButton, "transition"))

//            }

            // Handler().postDelayed(runnable, 3000)
            with(Handler()) {
                postDelayed({ morphButton.setProgress(20) }, 100)
                postDelayed({ morphButton.setProgress(50) }, 200)
                postDelayed({ morphButton.setProgress(40) }, 300)
                postDelayed({ morphButton.setProgress(100) }, 400)
                postDelayed({ morphButton.resetProgress() }, 500)
            }
        }
    }
}
