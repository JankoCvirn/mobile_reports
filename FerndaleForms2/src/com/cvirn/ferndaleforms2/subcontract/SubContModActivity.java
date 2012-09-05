package com.cvirn.ferndaleforms2.subcontract;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

public class SubContModActivity extends Activity {
	
	private Button btnUpdateCustomer;
	private EditText etxtModCustomerContact;
	private EditText etxtModCustomerState;
	private EditText etxtModCustomerCity;
	private EditText etxtModCustomerStreet;
	private EditText etxtModCustomerName;
	private Spinner spinnerCustMod;
	private DbHelper db;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subcont_mod);
		this.btnUpdateCustomer = (Button) findViewById(R.id.btnUpdateCustomer);
		this.etxtModCustomerContact = (EditText) findViewById(R.id.etxtModCustomerContact);
		this.etxtModCustomerState = (EditText) findViewById(R.id.etxtModCustomerState);
		this.etxtModCustomerCity = (EditText) findViewById(R.id.etxtModCustomerCity);
		this.etxtModCustomerStreet = (EditText) findViewById(R.id.etxtModCustomerStreet);
		this.etxtModCustomerName = (EditText) findViewById(R.id.etxtModCustomerName);
		this.spinnerCustMod = (Spinner) findViewById(R.id.spinnerCustMod);
		db=new DbHelper(this, true);
		this.spinnerCustMod = (Spinner) findViewById(R.id.spinnerCustMod);
		
		Cursor customerCursor;
		customerCursor=this.getSubCont();
		startManagingCursor(customerCursor);
		String[] from=new String[]{"name"};
		int[] to=new int[]{R.id.tvDBViewRow};
		
		SimpleCursorAdapter nameAdapter=new SimpleCursorAdapter(this, R.layout.db_view_row, customerCursor, from, to);
		spinnerCustMod.setAdapter(nameAdapter);
		this.btnUpdateCustomer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				setSubCDetails();
			}
		});
		
		this.spinnerCustMod
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						getSubCDetails();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		
		
		
	}
	
	private Cursor getSubCont() {
		
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		//String[] columns={"_id","name"};
		return d.query( "SUBCONTR",null, null, null, null, null,null, null);
		
	}
	
private void getSubCDetails() {
		
		Cursor mCursor=(Cursor)spinnerCustMod.getSelectedItem();	
		  String position=mCursor.getString(0);
		  SQLiteDatabase d=this.db.getWritableDatabase();
			String sql="SELECT * FROM SUBCONTR WHERE _id=?";
			Cursor c=d.rawQuery(sql, new String[] {position});
			if (c.moveToFirst()){
				etxtModCustomerName.setText(c.getString(1));
				etxtModCustomerStreet.setText(c.getString(2));
				etxtModCustomerCity.setText(c.getString(3));
				etxtModCustomerState.setText(c.getString(4));
				etxtModCustomerContact.setText(c.getString(5));
				}
				d.close();
		
	}
	
	private void setSubCDetails() {
		Cursor mCursor=(Cursor)spinnerCustMod.getSelectedItem();	
		  String position=mCursor.getString(0);
		
		SQLiteDatabase d=this.db.getWritableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("name", etxtModCustomerName.getText().toString());
		values.put("street", etxtModCustomerStreet.getText().toString());
		values.put("city", etxtModCustomerCity.getText().toString());
		values.put("state", etxtModCustomerState.getText().toString());
		values.put("contact", etxtModCustomerContact.getText().toString());
		long i=d.update("SUBCONTR", values, "_id=?",new String[] {position});
		d.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "SubContractor Updated", Toast.LENGTH_LONG).show();
			SubContModActivity.this.finish();
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
		
	}


}
