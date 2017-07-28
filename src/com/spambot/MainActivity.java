package com.spambot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

public class MainActivity extends Activity {

	TextView dashboard;
	public static List<String> result = new ArrayList<String>();
	public static List<String> mresult = new ArrayList<String>();
	List<String> mlist = new ArrayList<String>();
	String mail = "";

	EditText edt_url;
	private ProgressDialog progress = null;
	List<String> list = new ArrayList<String>();
	String URL = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dashboard = (TextView) findViewById(R.id.dashboard);
		edt_url = (EditText) findViewById(R.id.edt_url_text);
		progress = new ProgressDialog(this);
		progress.setMessage("Loading. Please wait...");
		progress.setIndeterminate(false);
		progress.setCancelable(true);
		progress.setCanceledOnTouchOutside(false);

		// https://www.wordsinarow.com/optin-email-lists.html"

		dashboard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mail = "";
				result.clear();
				list.clear();
				mresult.clear();
				mlist.clear();
				
				ConnectionDetector cd = new ConnectionDetector(
						MainActivity.this);
				boolean isInternetPresent = cd.isConnectingToInternet();
				if (!isInternetPresent) {
					showAlert("No internet connection");
					
				} else {
					if (edt_url.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(),
								"Please enter URL", Toast.LENGTH_LONG).show();
					} else if (edt_url.getText().toString().equals("Z")) {

						URL = "https://www.wordsinarow.com/optin-email-lists.html";
						new LongOperation().execute();
					} else if (edt_url.getText().toString().equals("E")) {
//1
						URL = "http://www.eminentbtechprojects.com";
						new LongOperation().execute();
					} else if (edt_url.getText().toString().equals("P")) {
//2
						URL = "http://www.pulseinfotech.com/contact-us.html";
						new LongOperation().execute();
					}
				//2	
					 else if (edt_url.getText().toString().equals("B")) {

							URL = "http://www.bsesdelhi.com/bsesdelhi/billonEmail.do";
							new LongOperation().execute();
						}else {
						
						URL = edt_url.getText().toString();

						new LongOperation().execute();
					}

				}
			}
		});
	}

	private class LongOperation extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			// GetMethodEx test = new GetMethodEx();
			String returned = null;

			try {
				returned = getInternetData();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return returned;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if (progress != null)
				progress.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println("Result Value is " + result);

			progress.dismiss();
			try {

				MainActivity.result = GetEmailsFromString(result);

				// }
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (MainActivity.result.size() == 0) {
				Toast.makeText(MainActivity.this,
						"No e-mail addresses available", Toast.LENGTH_LONG)
						.show();

			} else {
				for (int i = 0; i < MainActivity.result.size(); i++) {
					mail = mail + MainActivity.result.get(i).toString() + ", ";
				}
				sendMessagetoParse();

				Intent i = new Intent(MainActivity.this,
						MessageListActivity.class);
				startActivity(i);
			}
		}
	}

	public void sendMessagetoParse() {

		Backendless.Persistence.save( new Data( mail), new BackendlessCallback<Data>()
			    {
			      @Override
			      public void handleResponse( Data comment )
			      {
			        Log.i( "Comments", "Got new comment from " + comment.getMessage() );
			//        Toast.makeText(getApplicationContext(), "Send Sucessfully", Toast.LENGTH_SHORT).show();
			      }
			      
			      @Override
		          public void handleFault(BackendlessFault fault) {
		              
				      
			        Toast.makeText(getApplicationContext(), "Unknown error occured while saving data", Toast.LENGTH_LONG).show();

			    	  Log.i( "Comments", "Got new comment from " + fault.getMessage() );

		          }
				
			      
			    } );
		 
	/*	final ParseObject gameScore = new ParseObject("Mail");
		gameScore.put("email", mail);

		gameScore.saveInBackground(new SaveCallback() {

			@Override
			public void done(com.parse.ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					// Toast.makeText(DetailActivity.this,
					// "Thanks for Your Feedback", Toast.LENGTH_SHORT).show();
					
					 * Intent i=new
					 * Intent(DetailActivity.this,MainActivity.class);
					 * startActivity(i); finish();
					 

				}

				else {
					Toast.makeText(MainActivity.this,
							"Could Not send due to an error",
							Toast.LENGTH_SHORT).show();

					finish();
				}

			}
		});
*/
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String> GetEmailsFromString(String s) {
		System.out.println("email loop");

		Matcher m = Pattern.compile(
				"[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(s);
		int i = 1;
		while (m.find()) {

			System.out.println("email loop" + m.group());

			if (!list.contains(m.group())) {
				list.add(m.group().trim());
				mlist.add(i + ") " + m.group().trim());
				i = i + 1;
			}

		}

		/*
		 * HashSet hs = new HashSet(); hs.addAll(list); list.clear();
		 * list.addAll(hs);
		 */

		if (result.contains("<") || result.contains("DOCTYPE")
				|| result.contains("null")) {
			list.add("No e-mail addresses found");// etEmailsFromString(result);
		}

		MainActivity.mresult = mlist;
		return list;

	}

	private void showAlert(String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
		alert.setTitle("Error");
		alert.setCancelable(false);
		alert.setMessage(message);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// moveTaskToBack(true);
				finish();
			}
		});
		alert.show();
	}

	public String getInternetData() throws Exception {

		// new TrustAllManager();
		// new TrustAllSSLSocketFactory();

		BufferedReader in = null;
		String data = null;

		try {
			HttpClient client = new DefaultHttpClient();
			// String url=
			URI website = new URI(URL);// edt_url.getText().toString());//"https://www.wordsinarow.com/optin-email-lists.html");
			HttpGet request = new HttpGet();
			request.setURI(website);
			HttpResponse response = client.execute(request);
			response.getStatusLine().getStatusCode();

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String l = "";
			String nl = System.getProperty("line.separator");
			while ((l = in.readLine()) != null) {
				sb.append(l + nl);
			}
			in.close();
			data = sb.toString();
			return data;
		} finally {
			if (in != null) {
				try {
					in.close();
					return data;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}