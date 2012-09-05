package com.cvirn.ferndaleforms2.subcontract;

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

public class SubContSubActivity extends Activity {
	private Button btnClearData;
	private Button btnCreateNew;
	private EditText etxtContact;
	private EditText etxtState;
	private EditText etxtCity;
	private EditText etxtStreet;
	private EditText etxtName;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subcont_new);
		this.btnClearData = (Button) findViewById(R.id.btnClearData);
		this.btnCreateNew = (Button) findViewById(R.id.btnCreateNew);
		this.etxtContact = (EditText) findViewById(R.id.etxtContact);
		this.etxtState = (EditText) findViewById(R.id.etxtState);
		this.etxtCity = (EditText) findViewById(R.id.etxtCity);
		this.etxtStreet = (EditText) findViewById(R.id.etxtStreet);
		this.etxtName = (EditText) findViewById(R.id.etxtName);
		this.btnCreateNew.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				doSaveToDb();
			}
		});
		this.btnClearData.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				etxtContact.setText("");
				etxtState.setText("");
				etxtCity.setText("");
				etxtStreet.setText("");
				etxtName.setText("");
				
			}
		});
		
		
		
		
	}
	
private void doSaveToDb(){
		
		
		DbHelper db=new DbHelper(this, true);
		db.copyDatabaseFile();
		SQLiteDatabase base=db.getReadableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("name", etxtName.getText().toString());
		values.put("street", etxtStreet.getText().toString());
		values.put("city", etxtCity.getText().toString());
		values.put("state", etxtState.getText().toString());
		values.put("contact", etxtContact.getText().toString());
		long i=base.insert("SUBCONTR", "null", values);
		base.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "New subcontractor saved", Toast.LENGTH_LONG).show();
			etxtContact.setText("");
			  etxtState.setText("");
			  etxtCity.setText("");
			 etxtStreet.setText("");
			 etxtName.setText("");
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
		
	}
}
