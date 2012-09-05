package com.cvirn.ferndaleforms2.customer;

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

public class CustomerSubActivity extends Activity {
	

	private Button btnClearCustomerData;
	private Button btnCreateNewCustomer;
	private EditText etxtCustomerContact;
	private EditText etxtCustomerState;
	private EditText etxtCustomerCity;
	private EditText etxtCustomerStreet;
	private EditText etxtCustomerName;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_new);
		this.btnClearCustomerData = (Button) findViewById(R.id.btnClearCustomerData);
		this.btnCreateNewCustomer = (Button) findViewById(R.id.btnCreateNewCustomer);
		this.etxtCustomerContact = (EditText) findViewById(R.id.etxtCustomerContact);
		this.etxtCustomerState = (EditText) findViewById(R.id.etxtCustomerState);
		this.etxtCustomerCity = (EditText) findViewById(R.id.etxtCustomerCity);
		this.etxtCustomerStreet = (EditText) findViewById(R.id.etxtCustomerStreet);
		this.etxtCustomerName = (EditText) findViewById(R.id.etxtCustomerName);
		this.btnCreateNewCustomer
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View target) {
						doSaveToDb();
					}
				});
		this.btnClearCustomerData
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View target) {
						
						etxtCustomerContact.setText("");
						  etxtCustomerState.setText("");
						  etxtCustomerCity.setText("");
						 etxtCustomerStreet.setText("");
						 etxtCustomerName.setText("");
						
					}
				});
		
		
		
		
		}
	
private void doSaveToDb(){
		
		
		DbHelper db=new DbHelper(this, true);
		db.copyDatabaseFile();
		SQLiteDatabase base=db.getReadableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("name", etxtCustomerName.getText().toString());
		values.put("street", etxtCustomerStreet.getText().toString());
		values.put("city", etxtCustomerCity.getText().toString());
		values.put("state", etxtCustomerState.getText().toString());
		values.put("contact", etxtCustomerContact.getText().toString());
		long i=base.insert("CUSTOMER", "null", values);
		base.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "New customer saved", Toast.LENGTH_LONG).show();
			etxtCustomerContact.setText("");
			  etxtCustomerState.setText("");
			  etxtCustomerCity.setText("");
			 etxtCustomerStreet.setText("");
			 etxtCustomerName.setText("");
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
		
	}
}
