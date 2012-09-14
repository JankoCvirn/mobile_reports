package com.cvirn.ferndaleforms2.lmr;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.constants.LMReport;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.*;
import android.widget.*;
import android.view.*;

public class LMRSubContractActivity extends Activity {
	
	private GridView gridViewSubCon;
	private GridView gridViewEquip;
	private GridView gridViewMaterial;
	private GridView gridViewLabor;
	private Button btnCreateSubCon;
	private EditText etxtAmount;
	private EditText etxtSubCont;
	private Spinner spinnerLMRSubCont;
	private GridView gridViewLMRSubCon;
	Intent i;
	String jobnumber;
	private DbHelper db;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmr_sub_contractor_layout);
		
		this.gridViewSubCon = (GridView) findViewById(R.id.gridViewSubCon);
		this.gridViewEquip = (GridView) findViewById(R.id.gridViewEquip);
		this.gridViewMaterial = (GridView) findViewById(R.id.gridViewMaterial);
		this.gridViewLabor = (GridView) findViewById(R.id.gridViewLabor);
		this.btnCreateSubCon = (Button) findViewById(R.id.btnCreateSubCon);
		this.etxtAmount = (EditText) findViewById(R.id.etxtAmount);
		this.etxtSubCont = (EditText) findViewById(R.id.etxtSubCont);
		this.spinnerLMRSubCont = (Spinner) findViewById(R.id.spinnerLMRSubCont);
		//this.gridViewLMRSubCon = (GridView) findViewById(R.id.gridViewLMRSubCon);
		i=getIntent();
		jobnumber=i.getStringExtra(LMReport.LMR_JobNumber);
		
		db = new DbHelper(this, true);
		gridViewLabor.setAdapter(new DataAdapterLabor(this,jobnumber));
		gridViewMaterial.setAdapter(new DataAdapterMaterial(this,jobnumber));
		gridViewEquip.setAdapter(new DataAdapterEquip(this,jobnumber));
		gridViewSubCon.setAdapter(new DataAdapterSubContr(this, jobnumber));
		
		//gridViewLMRSubCon.setAdapter(new DataAdapterSubContr(this,jobnumber));
		//Populate spinner subcontr
		Cursor eCursor;
		eCursor = this.getSubCon();
		startManagingCursor(eCursor);
		String[] from = new String[] { "name" };
		int[] to = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter nameAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, eCursor, from, to);
		this.spinnerLMRSubCont.setAdapter(nameAdapter);
		
		
		/////////////////////////////
		this.btnCreateSubCon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				doSaveToDB();
			}
		});
		
		this.spinnerLMRSubCont
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						getSubCon();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		
		
		
	}
	
private Cursor getSubCon(){
		
		SQLiteDatabase d = this.db.getReadableDatabase();
		String[] columns = { "_id", "name" };
		return d.query("SUBCONTR", columns, null, null, null, null, null, null);
		
		
		
	}
	
	private long doSaveToDB(){
		DbHelper db = new DbHelper(this, true);
		db.copyDatabaseFile();
		SQLiteDatabase base = db.getReadableDatabase();
		ContentValues values;
		values = new ContentValues();
		values.put("jobnr", jobnumber);
		if (etxtSubCont.getText().toString().length()==0){
			Cursor mCursor=(Cursor)spinnerLMRSubCont.getSelectedItem();	
			  values.put("name", mCursor.getString(1));
			
		}
		else{
		values.put("name",etxtSubCont.getText().toString());	
			
		}
		values.put("amount", etxtAmount.getText().toString());
		
//016407109
		long i = base.insert("LMRSUBCONTR", "null", values);
		base.close();

		if (i != -1) {

			Toast.makeText(this, "New subcontractor data saved", Toast.LENGTH_LONG)
					.show();
			LMRSubContractActivity.this.finish();
			
		} else {

			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();

		}

		return i;
		
		
		
		
		
		
	}
}
