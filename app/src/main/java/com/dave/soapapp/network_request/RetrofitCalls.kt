package com.dave.soapapp.network_request

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.dave.soapapp.MainActivity
import com.dave.soapapp.helper.DbRequest
import com.dave.soapapp.helper.DbValueObject
import com.dave.soapapp.helper.SoapCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.ksoap2.SoapFault


class RetrofitCalls {

    fun registerUser(context: Context, dbRequest: DbRequest){
        CoroutineScope(Dispatchers.Main).launch{
            registerUserBac(context, dbRequest)
        }
    }
    private suspend fun registerUserBac(context: Context, dbRequest: DbRequest){

        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait..")
        progressDialog.setMessage("Registration in progress..")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        CoroutineScope(Dispatchers.IO).launch {

            CoroutineScope(Dispatchers.Main).launch {
                progressDialog.dismiss() }

            var result = ""
            kotlin.runCatching {

                try {
                    val isRegister = SoapCall().signUp(dbRequest)
                     if (isRegister == "true") {
                         result ="The user has been registered. Please wait we validate the details."

                         val dbValueObject = DbValueObject(dbRequest.lTRName, dbRequest.myPassword)
                        verifyUser(context, dbValueObject)
                    } else {
                         result =  "Please try again later."
                    }
                } catch (e: SoapFault) {
                    val faultString = e.faultstring
                    result = faultString
                    Log.e("******", faultString.toString())
                } catch (e: Exception) {
                    result = "There was an issue processing your request."
                    Log.e("******1", e.toString())
                    e.printStackTrace()
                }

                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                }

            }

        }


    }

    fun verifyUser(context: Context, dbValueObject: DbValueObject){

        CoroutineScope(Dispatchers.Main).launch {

            verifyUserBac(context,dbValueObject)

        }

    }
    private suspend fun verifyUserBac(context: Context, dbValueObject: DbValueObject) {


        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait..")
        progressDialog.setMessage("Authentication in progress..")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        var messageToast = ""
        val job = Job()
        CoroutineScope(Dispatchers.IO + job).launch {

            val baseUrl = "http://demo2.nav.kobby.co.ke:2048/BC200/ODataV4/"

            val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)

            try {
                val apiInterface = apiService.verifyUser("MOBILETEST")

                val isSuccessful = apiInterface.isSuccessful
                if (isSuccessful){

                    val responseBody = apiInterface.body()

                    Log.e("----- ", responseBody.toString())
                    if (responseBody != null){

                        val userList = responseBody.value
                        for (users in userList){

                            val userName = users.LTR_Name
                            val password = users.MyPassword

                            val givenUsername = dbValueObject.LTR_Name
                            val givenPassword = dbValueObject.MyPassword

                            if (userName == givenUsername && password == givenPassword){

                                CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)

                                break

                            }else{

                                CoroutineScope(Dispatchers.Main).launch {
                                    Toast.makeText(context, "User could not be validated", Toast.LENGTH_SHORT).show()
                                    progressDialog.dismiss()
                                }

                            }

                        }

                    }else{
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(context, "User could not be validated", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    }
                }
            }catch (e: Exception){
                Log.e("----", e.toString())
            }


        }.join()

    }

}

