package com.dave.soapapp.helper;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class SoapCall {

    private static final String NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/IntegrationTest"; // for wsdl it may be package name i.e http://package_name
    private static final String URL = "http://demo2.nav.kobby.co.ke:2047/BC200/WS/MOBILETEST/Codeunit/IntegrationTest?wsdl";
    // you can use IP address instead of localhost
    private static final String METHOD_NAME = "FnRegistrationSignup";
//    private static final String SOAP_ACTION = "urn:" + METHOD_NAME;
    private static final String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/IntegrationTest:" + METHOD_NAME;

    public String signUp(DbRequest dbRequest) throws SoapFault {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("lTRName", dbRequest.getLTRName());// Parameter for Method
        request.addProperty("lTRMail", dbRequest.getLTRMail());// Parameter for Method
        request.addProperty("countryRegionCode", dbRequest.getCountryRegionCode());// Parameter for Method
        request.addProperty("postalAddress", dbRequest.getPostalAddress());// Parameter for Method
        request.addProperty("postCode", dbRequest.getPostCode());// Parameter for Method
        request.addProperty("businessRegNo", dbRequest.getBusinessRegNo());// Parameter for Method
        request.addProperty("city", dbRequest.getCity());// Parameter for Method
        request.addProperty("myPassword", dbRequest.getMyPassword());// Parameter for Method
        request.addProperty("verificationToken", dbRequest.getVerificationToken());// Parameter for Method
        request.addProperty("myAction", dbRequest.getMyAction());// Parameter for Method

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;// **If your Webservice in .net otherwise remove it**
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {

            List<HeaderProperty> llstHeadersProperty = new ArrayList<>();
            llstHeadersProperty.add(new HeaderProperty("Authorization", "Basic " +
                    org.kobjects.base64.Base64.encode("MOBILE:Mobile@1".getBytes())));
            androidHttpTransport.call(SOAP_ACTION, envelope, llstHeadersProperty);

            // Method
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(envelope.getResponse());

    }


}

