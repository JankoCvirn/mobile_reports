package com.cvirn.ferndaleforms2.lmr;



import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.constants.LMReport;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

public class LMRSubStep1Activity extends Activity implements OnClickListener{
	
	//elements
	//private EditText txtCustomer;
	private EditText txtJobNumber;
	private EditText txtJobName;
	//private EditText txtJobLocation;
	private DatePicker dateJobDate;
	//private EditText txtFecManager;
	private EditText txtCustomerOrdNo;
	private EditText txtWorkperformed;
	
	private Spinner spiCustomer;
	private Spinner spiLocation;
	private Spinner spiManager;
	
	private Button btnSave;
	private Button btnClear;
	
	private DbHelper db;
	
	
	protected HashMap<String, String> data;
	
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmr_sub_step1_layout);
		db=new DbHelper(this, true);
		db.copyDatabaseFile();
		//////////////////////////////////////////////
		//txtCustomer=(EditText)findViewById(R.id.txtCustomer);
		txtJobNumber=(EditText)findViewById(R.id.txtJobNumber);
		txtJobName=(EditText)findViewById(R.id.txtJobName);
		//txtJobLocation=(EditText)findViewById(R.id.txtJobLocation);
		dateJobDate=(DatePicker)findViewById(R.id.dateJobDate);
		//txtFecManager=(EditText)findViewById(R.id.txtFecManager);
		txtCustomerOrdNo=(EditText)findViewById(R.id.txtCustomerOrderNr);
		txtWorkperformed=(EditText)findViewById(R.id.txtWorkPerf);
		
		////////////////////////////////////////////////////////
		spiCustomer=(Spinner)findViewById(R.id.spinnerCustomer);
		Cursor customerCursor;
		customerCursor=this.getCustomerNames();
		startManagingCursor(customerCursor);
		String[] from=new String[]{"name"};
		int[] to=new int[]{R.id.tvDBViewRow};
		
		SimpleCursorAdapter nameAdapter=new SimpleCursorAdapter(this, R.layout.db_view_row, customerCursor, from, to);
		spiCustomer.setAdapter(nameAdapter);
		
		////////////////////////////////////////////////////////
		spiLocation=(Spinner)findViewById(R.id.spinnerLocation);
		Cursor locationCursor;
		locationCursor=this.getLocationNames();
		startManagingCursor(locationCursor);
		String[] from2=new String[]{"name"};
		int[] to2=new int[]{R.id.tvDBViewRow};
		
		SimpleCursorAdapter nameAdapter2=new SimpleCursorAdapter(this, R.layout.db_view_row, locationCursor, from2, to2);
		spiLocation.setAdapter(nameAdapter2);
		
		
		
		
		
		////////////////////////////////////////////////////////
		spiManager=(Spinner)findViewById(R.id.spinnerManager);
		Cursor managerCursor;
		managerCursor=this.getManagerNames();
		startManagingCursor(managerCursor);
		String[] from3=new String[]{"name"};
		int[] to3=new int[]{R.id.tvDBViewRow};
		
		SimpleCursorAdapter nameAdapter3=new SimpleCursorAdapter(this, R.layout.db_view_row, managerCursor, from3, to3);
		spiManager.setAdapter(nameAdapter3);
		
		
		//////////////////////////////////////////////
		btnClear=(Button)findViewById(R.id.btnClearLMR);
		btnClear.setOnClickListener(clear);
		
		btnSave=(Button)findViewById(R.id.btnCreateLMR);
		btnSave.setOnClickListener(save);
		
	}


	///////////////////////////////////////////////////
	//ClickListeners
	
	private OnClickListener save=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(LMRSubStep1Activity.this);
			builder.setMessage("Create a new report?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           @SuppressWarnings("unchecked")
				public void onClick(DialogInterface dialog, int id) {
		        	
		        	if (txtJobNumber.getText().toString()==null || txtJobNumber.getText().toString().length()==0){
		        		
		        		txtJobNumber.setHint("Enter job number");
		        		Toast.makeText(LMRSubStep1Activity.this, "Please fill out the job number field.", Toast.LENGTH_LONG).show();
		        		LMRSubStep1Activity.this.txtJobNumber.setFocusable(true);
		        	}   
		        	else{
		        	String job_time=""+dateJobDate.getMonth()+"-"+dateJobDate.getDayOfMonth()+"-"+dateJobDate.getYear();   
		        	
		        	///
		        	Cursor m1Cursor=(Cursor)spiCustomer.getSelectedItem();	
		        		String customer=m1Cursor.getString(1);
		        	Cursor m2Cursor=(Cursor)spiLocation.getSelectedItem();	
		  		  		String location=m2Cursor.getString(1);
		  		  	Cursor m3Cursor=(Cursor)spiManager.getSelectedItem();	
	  		  			String manager=m3Cursor.getString(1);
	  		  	
		  		
		        	
		        	///
		        	
		        	data=new HashMap<String, String>();
		        	data.put("customer", customer);
		        	data.put("cordernr", txtCustomerOrdNo.getText().toString());
		        	data.put("jobnumber", txtJobNumber.getText().toString());
		        	data.put("jobname", txtJobName.getText().toString());
		        	data.put("joblocation", location);
		        	data.put("jobdate", job_time);
		        	data.put("fecmanager", manager);
		        	data.put("work", txtWorkperformed.getText().toString());
		        	if(setValuesToDB()!=-1){
		        	doLMRSub2();}
		        	
		        	}
		        	
		        	//new PostToCloud().execute(data);
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   // put your code here 
		        	   dialog.cancel();
		           }
		       });
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
			
			
		}
	};
	private OnClickListener clear=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(LMRSubStep1Activity.this);
			builder.setMessage("Clear are report fields?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   
		        	//txtCustomer.setText("");
		   			txtCustomerOrdNo.setText("");
		   			//txtFecManager.setText("");
		   			//txtJobLocation.setText("");
		   			txtJobName.setText("");
		   			txtJobNumber.setText("");
		   			txtWorkperformed.setText("");
		   			
		                
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   
		        	   dialog.cancel();
		           }
		       });
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
							
			
			
			
		}
	};
	
	
	protected void doLMRSub2(){
		
		Intent lmr_sub2=new Intent(getApplicationContext(),LMRSubStep2Activity.class);
		lmr_sub2.putExtra(LMReport.LMR_JobNumber, data.get(LMReport.LMR_JobNumber));
		startActivity(lmr_sub2);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	/////////////////////////////////////////////////////
	/////////////////////////////////////////////////////
	
	public long setValuesToDB(){
		
		
		
		DbHelper db=new DbHelper(this, true);
		db.copyDatabaseFile();
		SQLiteDatabase base=db.getReadableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("customernr", data.get("cordernr"));
		values.put("fecmanager", data.get("fecmanager"));
		values.put("jobnumber", data.get("jobnumber"));
		values.put("date", data.get("jobdate") );
		values.put("workperformed", data.get("work"));
		values.put("joblocation",data.get("joblocation") );
		values.put("jobname",data.get("jobname") );
		values.put("customer", data.get("customer"));
		long i=base.insert("JOB", "null", values);
		base.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "New Report saved", Toast.LENGTH_LONG).show();
			
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
			
		return i;
	}
	
	/////////////////////////////////////////////////////
	//DB read
	
	private Cursor getCustomerNames(){
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		String[] columns={"_id","name"};
		return d.query( "CUSTOMER",columns, null, null, null, null,null, null);
		
	}
	private Cursor getManagerNames(){
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		String[] columns={"_id","name"};
		return d.query("MANAGER", columns, null, null, null, null,null, null);
		
	}
    private Cursor getLocationNames(){
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		String[] columns={"_id","name"};
		return d.query("LOCATION", columns, null, null, null, null,null, null);
		
	}
	
	
	
	/////////////////////////////////////////////////////
	/////////////////////////////////////////////////////
	//Cloud
	
	class PostToCloud extends AsyncTask<HashMap<String, String>, String, String>{

		@Override
		protected String doInBackground(HashMap<String, String>... params) {
			String result="";
			
			
			
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
		
	}
}
