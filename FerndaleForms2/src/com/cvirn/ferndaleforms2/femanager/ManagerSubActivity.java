package com.cvirn.ferndaleforms2.femanager;

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

public class ManagerSubActivity extends Activity {
	private Button btnClearManagerData;
	private Button btnCreateNewManager;
	private EditText etxtManagerName;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_new);
		this.btnClearManagerData = (Button) findViewById(R.id.btnClearManagerData);
		this.btnCreateNewManager = (Button) findViewById(R.id.btnCreateNewManager);
		this.etxtManagerName = (EditText) findViewById(R.id.etxtManagerName);
		this.btnCreateNewManager.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				doSaveToDb(etxtManagerName.getText().toString());
			}
		});
		this.btnClearManagerData.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				etxtManagerName.setText("");
			}
		});
		
		
		
		
	}
	
private void doSaveToDb(String s){
		
		
		DbHelper db=new DbHelper(this, true);
		db.copyDatabaseFile();
		SQLiteDatabase base=db.getReadableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("name", s);
		long i=base.insert("MANAGER", "null", values);
		base.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "New manager saved", Toast.LENGTH_LONG).show();
			etxtManagerName.setText("");
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
		
	}
}
