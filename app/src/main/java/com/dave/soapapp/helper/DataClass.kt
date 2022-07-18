package com.dave.soapapp.helper

import android.text.TextUtils
import android.widget.EditText
import com.google.gson.annotations.SerializedName

data class DbResult(
    @SerializedName("@odata.context")
    val data: String,
    @SerializedName("value")
    val value: List<DbValueObject>
)
data class DbValueObject(
    val LTR_Name: String,
    val MyPassword: String
)

data class DbRequest(
    val lTRName: String,
    val lTRMail: String,
    val countryRegionCode: String,
    val postalAddress: String,
    val postCode: String,
    val businessRegNo: String,
    val city: String,
    val myPassword: String,
    val verificationToken: String,
    val myAction: String,
)
