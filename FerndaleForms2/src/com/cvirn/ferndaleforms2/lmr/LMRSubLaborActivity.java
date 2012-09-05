package com.cvirn.ferndaleforms2.lmr;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.constants.LMReport;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.*;
import android.widget.*;
import android.view.*;

public class LMRSubLaborActivity extends Activity {
	private GridView gridViewSubCon;
	private GridView gridViewEquip;
	private GridView gridViewMaterial;
	private GridView gridViewLabor;
	private EditText etxtNewLMRWorker;
	private Button btnCreateSubLabor;
	private EditText etxtDTimeLMRLabor;
	private EditText etxtHTimeLMRLabor;
	private EditText etxtSTimeLMRLabor;
	private Spinner spinnerLMRSubLaborWorker;
	Intent i;
	private DbHelper db;
	String jobnumber;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmr_sub_labor_layout);
		this.gridViewSubCon = (GridView) findViewById(R.id.gridViewSubCon);
		this.gridViewEquip = (GridView) findViewById(R.id.gridViewEquip);
		this.gridViewMaterial = (GridView) findViewById(R.id.gridViewMaterial);
		this.gridViewLabor = (GridView) findViewById(R.id.gridViewLabor);
		GridView gridview = (GridView) findViewById(R.id.gridView1);  
	       
		
		this.etxtNewLMRWorker = (EditText) findViewById(R.id.etxtNewLMRWorker);
		i=getIntent();
		jobnumber=i.getStringExtra(LMReport.LMR_JobNumber);
		gridview.setAdapter(new DataAdapterLabor(this,jobnumber));
		
		this.btnCreateSubLabor = (Button) findViewById(R.id.btnCreateSubLabor);
		this.etxtDTimeLMRLabor = (EditText) findViewById(R.id.etxtDTimeLMRLabor);
		this.etxtHTimeLMRLabor = (EditText) findViewById(R.id.etxtHTimeLMRLabor);
		this.etxtSTimeLMRLabor = (EditText) findViewById(R.id.etxtSTimeLMRLabor);

		db = new DbHelper(this, true);
		//db.copyDatabaseFile();
		gridViewLabor.setAdapter(new DataAdapterLabor(this,jobnumber));
		gridViewMaterial.setAdapter(new DataAdapterMaterial(this,jobnumber));
		gridViewEquip.setAdapter(new DataAdapterEquip(this,jobnumber));
		gridViewSubCon.setAdapter(new DataAdapterSubContr(this, jobnumber));
		
		// ////////////////////////////////////////////////////////////////////////////////////
		// Populate spinner from DB
		this.spinnerLMRSubLaborWorker = (Spinner) findViewById(R.id.spinnerLMRSubLaborWorker);
		Cursor workCursor;
		workCursor = this.getWorkerNames();
		startManagingCursor(workCursor);
		String[] from = new String[] { "lname"};
		int[] to = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter nameAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, workCursor, from, to);
		this.spinnerLMRSubLaborWorker.setAdapter(nameAdapter);

		this.btnCreateSubLabor.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {

				//dsiplay alert
				if (etxtSTimeLMRLabor.getText().toString().length() == 0
						|| etxtDTimeLMRLabor.getText().toString().length() == 0
						|| etxtHTimeLMRLabor.getText().toString().length() == 0) {
						
						AlertDialog.Builder builder = new AlertDialog.Builder(LMRSubLaborActivity.this);
						builder.setMessage("Please fill out the data fields.")
						       .setCancelable(false)
						       
						       .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						               dialog.cancel();
						           }
						       });
						AlertDialog alertDialog = builder.create();
						alertDialog.show();
				}
				//save to db
				else{
					
					doSaveToDb();
					
				}

			}
		});
		

	}

	private Cursor getWorkerNames() {

		SQLiteDatabase d = this.db.getReadableDatabase();
		String[] columns = { "_id", "lname","fname" };
		return d.query("WORKER", columns, null, null, null, null, null, null);

	}

	private long doSaveToDb() {

		
		
		
		DbHelper db = new DbHelper(this, true);
		db.copyDatabaseFile();
		SQLiteDatabase base = db.getReadableDatabase();
		ContentValues values;
		values = new ContentValues();
		values.put("jobnr", jobnumber);
		if (etxtNewLMRWorker.getText().toString().length()==0){
			
		  Cursor mCursor=(Cursor)spinnerLMRSubLaborWorker.getSelectedItem();	
		  values.put("worker", mCursor.getString(1));
		  //Toast.makeText(this, mCursor.getString(1), Toast.LENGTH_SHORT).show();
		}
		else{
			values.put("worker", etxtNewLMRWorker.getText().toString());
		}
		values.put("stime", etxtSTimeLMRLabor.getText().toString());
		values.put("htime", etxtHTimeLMRLabor.getText().toString());
		values.put("dtime", etxtHTimeLMRLabor.getText().toString());
		
//016407109
		long i = base.insert("LMRLABOR", "null", values);
		base.close();

		if (i != -1) {

			Toast.makeText(this, "New labor data saved", Toast.LENGTH_LONG)
					.show();
			LMRSubLaborActivity.this.finish();
		} else {

			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();

		}

		return i;

	}
}
