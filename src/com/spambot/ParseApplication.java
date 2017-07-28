package com.spambot;
import com.backendless.Backendless;
import com.parse.Parse;
import com.parse.ParseACL;
 
import com.parse.ParseUser;
 
import android.app.Application;
 
public class ParseApplication extends Application {
 
	private static final String APP_ID ="5E67024F-3274-D002-FFC2-E4B2BDDE0B00";//"713035A5-A996-0E87-FFEC-8AEFFA081800";
	private static final String SECRET_KEY ="1C406022-6ED5-AA79-FF2A-84CDFF3D3600";//"1E1D1A69-6C32-6E58-FF9A-0F23D2DDA300";

	
	@Override
	public void onCreate() {
		super.onCreate();
		String appVersion = "v1";
	    Backendless.initApp( this, APP_ID, SECRET_KEY, appVersion );
	}
 
}