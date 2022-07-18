package com.dave.soapapp.helper

import android.text.TextUtils
import android.widget.EditText
import okhttp3.Credentials
import okhttp3.Interceptor

class FormatClass {

    fun checkEditText(editTextList: List<EditText>): Boolean{
        var isFilled = true
        for (editText in editTextList){
            val editTextTxt = editText.text.toString()
            if (TextUtils.isEmpty(editTextTxt)){
                isFilled = false
                editText.error = "This field cannot be empty."
            }
        }
        return isFilled
    }

}
