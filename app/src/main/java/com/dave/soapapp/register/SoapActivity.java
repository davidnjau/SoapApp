package com.dave.soapapp.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dave.soapapp.helper.DbRequest;
import com.dave.soapapp.R;
import com.dave.soapapp.helper.DbValueObject;
import com.dave.soapapp.helper.FormatClass;
import com.dave.soapapp.network_request.RetrofitCalls;

import java.util.Arrays;
import java.util.List;

public class SoapActivity extends AppCompatActivity {

    Button button;

    private RetrofitCalls retrofitCalls = new RetrofitCalls();

    EditText lTRName, lTRMail, countryRegionCode,
            postalAddress, postCode, businessRegNo,
            city, myPassword, verificationToken;

    private FormatClass formatClass = new FormatClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap);

        button = findViewById(R.id.button);

        lTRName = findViewById(R.id.lTRName);
        lTRMail = findViewById(R.id.lTRMail);
        countryRegionCode = findViewById(R.id.countryRegionCode);
        postalAddress = findViewById(R.id.postalAddress);
        postCode = findViewById(R.id.postCode);
        businessRegNo = findViewById(R.id.businessRegNo);
        city = findViewById(R.id.city);
        myPassword = findViewById(R.id.myPassword);
        verificationToken = findViewById(R.id.verificationToken);

        button.setOnClickListener(v -> {



            retrofitCalls.verifyUser(this, new DbValueObject("t", "r"));

            String username = lTRName.getText().toString();
            String emailAddress = lTRMail.getText().toString();
            String countryCode = countryRegionCode.getText().toString();
            String postalAddressText = postalAddress.getText().toString();
            String postCodeTxt = postCode.getText().toString();
            String businessReg = businessRegNo.getText().toString();
            String cityTxt = city.getText().toString();
            String password = myPassword.getText().toString();
            String verifyToken = verificationToken.getText().toString();

            List<EditText> editTextList = Arrays.asList(lTRName, lTRMail, countryRegionCode,
                    postalAddress, postCode, businessRegNo,
                    city, myPassword, verificationToken);

            boolean isFilled = formatClass.checkEditText(editTextList);

            if (isFilled){
                DbRequest dbRequest = new DbRequest(
                        username,
                        emailAddress,
                        countryCode,
                        postalAddressText,
                        postCodeTxt,
                        businessReg,
                        cityTxt,
                        password,
                        verifyToken,
                        "insert"
                );
//                retrofitCalls.registerUser(this, dbRequest);
            }



//                new CallWebService().execute(textBox.getText().toString());
        });

    }

//    @SuppressLint("StaticFieldLeak")
//    class CallWebService extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPostExecute(String s) {
//
//            text.setText("Resposnse = " + s);
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.O)
//        @Override
//        protected String doInBackground(String... params) {
//            String result = "";
//            try {
//                String  isRegister = new SoapCall().signUp();
//
//                if (isRegister.equals("true")){
//                    result = "The user has been registered.";
//                }else {
//                    result = "Please try again later.";
//                }
//
//            } catch (SoapFault e) {
//
//                String faultString = e.faultstring;
//                result = faultString;
//                Log.e("******", String.valueOf(faultString));
//
//            }catch (Exception e){
//                result = "There was an issue processing your request.";
//                Log.e("******1", String.valueOf(e));
//                e.printStackTrace();
//            }
//
//            return result;
//        }
//    }
//

}