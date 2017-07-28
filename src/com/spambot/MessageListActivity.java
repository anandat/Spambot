package com.spambot;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MessageListActivity extends Activity {

	TextView Detail, send_mail;
	String Email = "demospambot@gmail.com";
	String subject = "Sample e-mail !!";
	String password = "spam1234";
	String body = "The quick brown fox jumps over the lazy little dog.";
	String senderEmail = "demospambot@gmail.com";
	private OrganizationHome organizationHome;
	private static final int PROGRESSDIALOG_ID = 0;
	private ProgressDialog progressDialog;
	public static List<String> newMailingList;
	ArrayList<String> Job_profile ;

	List<String> list1 = new ArrayList<String>();
	
	String mail = "";// MainActivity.result.size()

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		Detail = (TextView) findViewById(R.id.Detail_mail);
		send_mail = (TextView) findViewById(R.id.send_mail);


		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading. Please wait...");
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);

		
		//new_list.clear();
		
		for (int i = 0; i < MainActivity.mresult.size(); i++) {
			mail = mail + System.getProperty("line.separator")
					+ System.getProperty("line.separator")
					+ MainActivity.mresult.get(i).toString();
		}
		
		
		Detail.setText(mail);
		// no.setVisibility(View.GONE);

		// }
		send_mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// sendMessagetoParse();

				showDialog(PROGRESSDIALOG_ID);


			}
		});
	}

	
	
	/*private class SendEmail extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {

			for (int i = 0; i < Job_profile.size(); i++) {
				sendemailtocandidate(Job_profile.get(i).toString());
			}
			return null;
		}

		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			try{
			if (progressDialog != null)
				progressDialog.setMessage("Sending e-mail(s). Please wait ...");
			progressDialog.show();
			}catch(Exception e)
			{
			}
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {

			progressDialog.dismiss();
			Toast.makeText(MessageListActivity.this,
					"Mail(s) sent successfully", Toast.LENGTH_SHORT).show();

		}
	}
	*/
	

	public void sendemailtocandidate(String mail) {
		try {
			GMailSender sender = new GMailSender(Email, password);
			sender.sendMail(subject, body, senderEmail, mail);
			System.out.println("Result Value is try" + mail);

		} catch (Exception e) {
			System.out.println("Result Value in catch");

			Log.e("SendMail", e.getMessage(), e);
		}
	}
	
	   protected Dialog onCreateDialog(int id) {
			switch (id) {
			case PROGRESSDIALOG_ID:
				removeDialog(PROGRESSDIALOG_ID);
				progressDialog = new ProgressDialog(MessageListActivity.this);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setCancelable(false);
				//progressDialog.dismiss()
				progressDialog.setMessage("Sending e-mail(s). Please wait ...");
				progressDialog.setOnCancelListener(new OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						if(organizationHome != null
								&& organizationHome.getStatus() != AsyncTask.Status.FINISHED)
							organizationHome.cancel(true);
				 }
			});
				break;
			
			default:
				progressDialog = null;
			}
			return progressDialog;
		}

		@Override
		protected void onPrepareDialog(int id, Dialog dialog) {
			switch (id) {
			case PROGRESSDIALOG_ID:
				
				
				if (organizationHome != null
						&& organizationHome.getStatus() != AsyncTask.Status.FINISHED)
					organizationHome.cancel(true);
				organizationHome = new OrganizationHome();
				organizationHome.execute();
				break;
			}
		}





		class OrganizationHome extends AsyncTask<Void, Integer, String> {
			
			@Override
			protected String doInBackground(Void... arg0) { 

				parsequestion();
				

				for (int i = 0; i < Job_profile.size(); i++) {
					sendemailtocandidate(Job_profile.get(i).toString());
				}
				
				
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				try{
				progressDialog.dismiss();
				}
				catch(Exception e)
				{
					
				}
				
				showAlert("E-mail(s) sent successfully");		
				//Toast.makeText(MessageListActivity.this,"Mail(s) sent successfully", Toast.LENGTH_LONG).show();
			//	new SendEmail().execute();

			}
		}

	protected void parsequestion() {
		// TODO Auto-generated method stub
	
		BackendlessDataQuery dataQuery = new BackendlessDataQuery();
		//dataQuery.setWhereClause( whereClause );
		BackendlessCollection<Data> result = Backendless.Persistence.of( Data.class ).find( dataQuery );
		Job_profile = new ArrayList<String>();
		newMailingList = new ArrayList<String>();
		
		
		for( Data friend : result.getData() )
		{
		System.out.println( "Message is "+friend.getMessage()) ;
		
		newMailingList=GetEmailsFromString(friend.getMessage());
		for(int i=0;i<newMailingList.size();i++)
		{
				Job_profile.add(newMailingList.get(i).toString());
		}
		}
		
		System.out.println("fd"+newMailingList.size());
		System.out.println("fd_job"+Job_profile.size());

		
	}

	
	
	private List<String> GetEmailsFromString(String s) {
		System.out.println("email loop");
        list1.clear();
		Matcher m = Pattern.compile(
				"[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(s);
		int i=1;
		while (m.find()) {
			
			System.out.println("email loop" + m.group());
			
			//if (!list.contains(m.group())) {
				list1.add(m.group().trim());
			}
		
		return list1;

	}
	
	private void showAlert(String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(MessageListActivity.this);
		alert.setTitle("Success");
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
}