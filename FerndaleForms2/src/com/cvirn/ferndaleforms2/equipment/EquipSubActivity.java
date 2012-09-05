package com.cvirn.ferndaleforms2.equipment;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

import android.widget.*;
import android.view.*;

public class EquipSubActivity extends Activity {
	

	private Button btnClearEqipData;
	private Button btnCreateNewEquip;
	private EditText etxtEquipName;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eqip_new);
		this.btnClearEqipData = (Button) findViewById(R.id.btnClearEqipData);
		this.btnCreateNewEquip = (Button) findViewById(R.id.btnCreateNewEquip);
		this.etxtEquipName = (EditText) findViewById(R.id.etxtEquipName);
		this.btnCreateNewEquip.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				doSaveToDb(etxtEquipName.getText().toString());
			}
		});
		this.btnClearEqipData.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				etxtEquipName.setText("");
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
		long i=base.insert("EQUIP", "null", values);
		base.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "New equipment saved", Toast.LENGTH_LONG).show();
			etxtEquipName.setText("");
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
		
	}
}
