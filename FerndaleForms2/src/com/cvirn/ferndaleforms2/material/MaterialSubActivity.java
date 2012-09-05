package com.cvirn.ferndaleforms2.material;


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

public class MaterialSubActivity extends Activity {
	private Button btnClearMaterialData;
	private Button btnCreateNewMaterial;
	private EditText etxtMaterialName;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		setContentView(R.layout.material_new);
		this.btnClearMaterialData = (Button) findViewById(R.id.btnClearMaterialData);
		this.btnCreateNewMaterial = (Button) findViewById(R.id.btnCreateNewMaterial);
		this.etxtMaterialName = (EditText) findViewById(R.id.etxtMaterialName);
		this.btnCreateNewMaterial
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View target) {
						doSaveToDb(etxtMaterialName.getText().toString());
					}
				});
		this.btnClearMaterialData
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View target) {
						
						etxtMaterialName.setText("");
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
		long i=base.insert("MATERIAL", "null", values);
		base.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "New material saved", Toast.LENGTH_LONG).show();
			etxtMaterialName.setText("");
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
		
	}
}
