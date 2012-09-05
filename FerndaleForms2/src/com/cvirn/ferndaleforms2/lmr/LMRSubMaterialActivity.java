package com.cvirn.ferndaleforms2.lmr;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.constants.LMReport;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;
import android.app.*;
import android.widget.*;
import android.view.*;

public class LMRSubMaterialActivity extends Activity {
	private GridView gridViewSubCon;
	private GridView gridViewEquip;
	private GridView gridViewMaterial;
	private GridView gridViewLabor;
	private EditText editText2;
	private Button btnCreateMaterial;
	private EditText editText1;
	private Spinner spinner1;
	Intent i;
	String jobnumber;
	private DbHelper db;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmr_sub_material_layout);
		this.gridViewSubCon = (GridView) findViewById(R.id.gridViewSubCon);
		this.gridViewEquip = (GridView) findViewById(R.id.gridViewEquip);
		this.gridViewMaterial = (GridView) findViewById(R.id.gridViewMaterial);
		this.gridViewLabor = (GridView) findViewById(R.id.gridViewLabor);
		this.editText2 = (EditText) findViewById(R.id.editText2);
		this.btnCreateMaterial = (Button) findViewById(R.id.btnCreateMaterial);
		this.editText1 = (EditText) findViewById(R.id.editText1);
		this.spinner1 = (Spinner) findViewById(R.id.spinner1);
		GridView gridview = (GridView) findViewById(R.id.gridViewLMRMat);  
		i=getIntent();
		jobnumber=i.getStringExtra(LMReport.LMR_JobNumber);
		
		db = new DbHelper(this, true);
		gridViewLabor.setAdapter(new DataAdapterLabor(this,jobnumber));
		gridViewMaterial.setAdapter(new DataAdapterMaterial(this,jobnumber));
		gridViewEquip.setAdapter(new DataAdapterEquip(this,jobnumber));
		gridViewSubCon.setAdapter(new DataAdapterSubContr(this, jobnumber));
		
		//db.copyDatabaseFile();
		gridview.setAdapter(new DataAdapterMaterial(this,jobnumber));
		//populate spinner
		this.spinner1 = (Spinner) findViewById(R.id.spinner1);
		Cursor workCursor;
		workCursor = this.getMaterials();
		startManagingCursor(workCursor);
		String[] from = new String[] { "name" };
		int[] to = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter nameAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, workCursor, from, to);
		this.spinner1.setAdapter(nameAdapter);
		
		
		
		
		this.btnCreateMaterial.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				if (doSaveToDB()!=-1){
					
					editText2.setText("");
				}
			}
		});
		
		
		
		
		
	}
	
	
	
	
	private Cursor getMaterials(){
		
		SQLiteDatabase d = this.db.getReadableDatabase();
		String[] columns = { "_id", "name" };
		return d.query("MATERIAL", columns, null, null, null, null, null, null);
		
		
		
	}
	
	private long doSaveToDB(){
		DbHelper db = new DbHelper(this, true);
		db.copyDatabaseFile();
		SQLiteDatabase base = db.getReadableDatabase();
		ContentValues values;
		values = new ContentValues();
		values.put("jobnr", jobnumber);
		if (editText2.getText().toString().length()==0){
			Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
			  values.put("name", mCursor.getString(1));
			
		}
		else{
		values.put("name",editText2.getText().toString());	
			
		}
		values.put("ammount", editText1.getText().toString());
		
//016407109
		long i = base.insert("LMRMATERIAL", "null", values);
		base.close();

		if (i != -1) {

			Toast.makeText(this, "New materials data saved", Toast.LENGTH_LONG)
					.show();
			LMRSubMaterialActivity.this.finish();
			
		} else {

			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();

		}

		return i;
		
		
		
		
		
		
	}
}
