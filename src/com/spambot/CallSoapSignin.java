package com.spambot;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CallSoapSignin {
    private final String SOAP_ACTION = "http://tempuri.org/TotalCAMAmount";
    private final String OPERATION_NAME = "TotalCAMAmount";
    private final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private final String SOAP_ADDRESS = "http://59.176.84.73/UBRSWEBSERVICE/Service.asmx";

    public CallSoapSignin()
    {
    }

    public String Call()
    {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
       /* PropertyInfo pi=new PropertyInfo();
        pi.setName("emailId_");
        pi.setValue(a);
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("userPWD_");
        pi.setValue(b);
        pi.setType(String.class);
        request.addProperty(pi);
*/

       // System.out.println("request="+ request );

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        request = null;

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Object response=null;
        try
        {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
            System.out.println("response=" +response);
            envelope = null;
        }
        catch (Exception exception)
        {
            response=exception.toString();
            System.out.println("response in catch=" +response);

        }
        return response.toString();
    }


}
