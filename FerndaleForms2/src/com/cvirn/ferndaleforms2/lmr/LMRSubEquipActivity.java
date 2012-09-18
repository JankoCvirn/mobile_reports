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

public class LMRSubEquipActivity extends Activity {
	private GridView gridViewSubCon;
	private GridView gridViewEquip;
	private GridView gridViewMaterial;
	private GridView gridViewLabor;
	private Button btnCreateEquip;
	private EditText editText1;
	private EditText editText2;
	private Spinner spinner1;
	Intent i;
	private DbHelper db;
	String jobnumber;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//2340009-1510502224  3224699856
		setContentView(R.layout.lmr_sub_euipment_layout);
		this.gridViewSubCon = (GridView) findViewById(R.id.gridViewSubCon);
		//this.gridViewEquip = (GridView) findViewById(R.id.gridViewEquip);
		this.gridViewMaterial = (GridView) findViewById(R.id.gridViewMaterial);
		this.gridViewLabor = (GridView) findViewById(R.id.gridViewLabor);
		this.btnCreateEquip = (Button) findViewById(R.id.btnCreateEquip);
		this.editText1 = (EditText) findViewById(R.id.editText1);
		this.editText2 = (EditText) findViewById(R.id.editText2);
		this.spinner1 = (Spinner) findViewById(R.id.spinnerLMRSubEQ);
		GridView gridview = (GridView) findViewById(R.id.gridViewLMREquip);  
		
		
		
		
		i=getIntent();
		jobnumber=i.getStringExtra(LMReport.LMR_JobNumber);
		db = new DbHelper(this, true);
		gridview.setAdapter(new DataAdapterEquip(this,jobnumber));
		//db.copyDatabaseFile();
		gridViewLabor.setAdapter(new DataAdapterLabor(this,jobnumber));
		gridViewMaterial.setAdapter(new DataAdapterMaterial(this,jobnumber));
		//gridViewEquip.setAdapter(new DataAdapterEquip(this,jobnumber));
		gridViewSubCon.setAdapter(new DataAdapterSubContr(this, jobnumber));
		
		
		Cursor eCursor;
		eCursor = this.getEqNames();
		startManagingCursor(eCursor);
		String[] from = new String[] { "name" };
		int[] to = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter nameAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, eCursor, from, to);
		this.spinner1.setAdapter(nameAdapter);
		
		
		
		
		this.btnCreateEquip.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				doSaveToDb();
			}
		});
		
		
		
		
		
	}
	
	private Cursor getEqNames() {

		SQLiteDatabase d = this.db.getReadableDatabase();
		//String[] columns = { "_id", "name" };
		return d.query("EQUIP", null, null, null, null, null, null, null);

	}
	
private long doSaveToDb() {

		
		
		
		DbHelper db = new DbHelper(this, true);
		db.copyDatabaseFile();
		SQLiteDatabase base = db.getWritableDatabase();
		ContentValues values;
		values = new ContentValues();
		values.put("jobnr", jobnumber);
		
		if (editText2.getText().toString().length()==0){
			Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
			  values.put("name", mCursor.getString(1));
			
		}
		else{
			
			values.put("name", editText2.getText().toString());
			
		}
		values.put("ammount",	editText1.getText().toString());
		
		
//016407109
		long i = base.insert("LMREQUIP", "null", values);
		base.close();

		if (i != -1) {

			Toast.makeText(this, "New equipment data saved", Toast.LENGTH_LONG)
					.show();
			LMRSubEquipActivity.this.finish();
			
		} else {

			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();

		}

		return i;

	}
}
