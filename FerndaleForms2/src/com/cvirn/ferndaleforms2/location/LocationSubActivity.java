package com.cvirn.ferndaleforms2.location;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

public class LocationSubActivity extends Activity {
	private Button btnClearLocationData;
	private Button btnCreateNewLocation;
	private EditText etxtLocationDetails;
	private EditText etxtLocationName;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_new);
		this.btnClearLocationData = (Button) findViewById(R.id.btnClearLocationData);
		this.btnCreateNewLocation = (Button) findViewById(R.id.btnCreateNewLocation);
		this.etxtLocationDetails = (EditText) findViewById(R.id.etxtLocationDetails);
		this.etxtLocationName = (EditText) findViewById(R.id.etxtLocationName);
		
		this.btnCreateNewLocation
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View target) {
						doSaveToDb();
					}
				});
		this.btnClearLocationData
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View target) {
						
						etxtLocationName.setText("");
						  etxtLocationDetails.setText("");
						  
						
					}
				});
		
		
		
		
	}
	
private void doSaveToDb(){
		
		
		DbHelper db=new DbHelper(this, true);
		db.copyDatabaseFile();
		SQLiteDatabase base=db.getReadableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("name", etxtLocationName.getText().toString());
		values.put("details", etxtLocationDetails.getText().toString());
		
		long i=base.insert("LOCATION", "null", values);
		base.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "New location saved", Toast.LENGTH_LONG).show();
			etxtLocationName.setText("");
			  etxtLocationDetails.setText("");
			  
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
		
	}
}
