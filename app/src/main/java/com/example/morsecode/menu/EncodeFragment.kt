package com.example.morsecode.menu

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.morsecode.R
import com.example.morsecode.controllers.Encode
import com.example.morsecode.controllers.Flashlight
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EncodeFragment: Fragment(R.layout.fragment_encode) {

    private val encodeInputTextRegex: Regex = "^[a-zA-Z0-9 ]+$".toRegex()

    private var isFlashlightOn = false

//    private lateinit var cameraManager: CameraManager
//    private var cameraId: String? = null
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_encode, container, false)
        val editText: EditText = view.findViewById(R.id.encodeInputText)
        val resultText: TextView = view.findViewById(R.id.encodeOutputText)

        val encoder: Encode = Encode(300L);

//        editText.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
//            if (source.matches(encodeInputTextRegex)) {
//                source
//            } else {
//                ""
//            }
//        })

//        var lastText: String = ""

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println(s.toString())

                if (!s.isNullOrEmpty() && !s.matches(encodeInputTextRegex)) {
                    editText.error = "Required english letters and numbers"
//                    editText.setText(lastText)
                } else {
                    editText.error = null

                    resultText.text = encoder.text(s.toString()).joinToString("")
//                    lastText = s.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        val button: Button = view.findViewById(R.id.toggleEncodeButton)
        val cameraManager = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager;
        val cameraId = cameraManager.cameraIdList.getOrNull(0);

        val flashlight = Flashlight(cameraManager)

        button.setOnClickListener {
            if (isFlashlightOn) {
                isFlashlightOn = false
                job?.cancel()

                cameraId?.let {
                    flashlight.toggle(it, false)
                }

                return@setOnClickListener
            }

            isFlashlightOn = true

            job = cameraId?.let {
                lifecycleScope.launch {
                    val chars: Array<Char> = resultText.text.toString().toCharArray().toTypedArray()
                    val durations = encoder.signal(chars)

                    resultText.text = durations.joinToString(" ")

                    flashlight.play(it, durations)
                }
            }
        }

        return view
    }
}