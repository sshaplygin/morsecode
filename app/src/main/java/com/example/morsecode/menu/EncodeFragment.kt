package com.example.morsecode.menu

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.morsecode.R
import com.example.morsecode.controllers.Encode

class EncodeFragment: Fragment(R.layout.fragment_encode) {

    private val encodeInputTextRegex: Regex = "^[a-zA-Z0-9]+$".toRegex()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_encode, container, false)
        val editText: EditText = view.findViewById(R.id.encodeInputText)
        val resultText: EditText = view.findViewById(R.id.encodeOutputText)

        val encoder: Encode = Encode();

//        editText.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
//            if (source.matches(encodeInputTextRegex)) {
//                source
//            } else {
//                ""
//            }
//        })

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println(s.toString())

                if (!s.isNullOrEmpty() && !s.matches(encodeInputTextRegex)) {
                    editText.error = "Required english letters and numbers"
                } else {
                    editText.error = null

                    resultText.text = s as Editable?

//                    val res = s.toString()
//
//                    editText.setText(res)
//                    editText.setSelection(res.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
}