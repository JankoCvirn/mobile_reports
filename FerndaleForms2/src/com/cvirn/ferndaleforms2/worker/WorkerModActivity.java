package com.cvirn.ferndaleforms2.worker;

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

public class WorkerModActivity extends Activity {
	
	private Button btnModWorker;
	private EditText etxtModWorkerNotes;
	private EditText etxtModWorkerProf;
	private EditText etxtModWorkerLName;
	private EditText etxtModWorkerFname;
	private Spinner spinnerModWorker;
	private DbHelper db;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.worker_mod);
		this.btnModWorker = (Button) findViewById(R.id.btnModWorker);
		this.etxtModWorkerNotes = (EditText) findViewById(R.id.etxtModWorkerNotes);
		this.etxtModWorkerProf = (EditText) findViewById(R.id.etxtModWorkerProf);
		this.etxtModWorkerLName = (EditText) findViewById(R.id.etxtModWorkerLName);
		this.etxtModWorkerFname = (EditText) findViewById(R.id.etxtModWorkerFname);
		this.spinnerModWorker = (Spinner) findViewById(R.id.spinnerModWorker);
		db=new DbHelper(this, true);
		//
		Cursor mCursor;
		mCursor=this.getNames();
		startManagingCursor(mCursor);
		String[] fname=new String[]{"lname"};
		int[] to=new int[]{R.id.tvDBViewRow};
		
		SimpleCursorAdapter nameAdapter=new SimpleCursorAdapter(this, R.layout.db_view_row, mCursor, fname, to);
		spinnerModWorker.setAdapter(nameAdapter);
		
		
		
		//
		this.btnModWorker.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				setWDetails();
			}
		});
		
		this.spinnerModWorker
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						getWDetails();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		
		
	}

private Cursor getNames() {
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		//String[] columns={"_id","name"};
		return d.query( "WORKER",null, null, null, null, null,null, null);
		
	}
	
	private void getWDetails() {
		
		Cursor mCursor=(Cursor)spinnerModWorker.getSelectedItem();	
		  String position=mCursor.getString(0);
		  SQLiteDatabase d=this.db.getWritableDatabase();
			String sql="SELECT * FROM WORKER WHERE _id=?";
			Cursor c=d.rawQuery(sql, new String[] {position});
			if (c.moveToFirst()){
				etxtModWorkerFname.setText(c.getString(1));
				etxtModWorkerLName.setText(c.getString(2));
				etxtModWorkerProf.setText(c.getString(3));
				etxtModWorkerNotes.setText(c.getString(4));
				
				}
				d.close();
		
	}
	
	private void setWDetails() {
		
		Cursor mCursor=(Cursor)spinnerModWorker.getSelectedItem();	
		  String position=mCursor.getString(0);
		
		SQLiteDatabase d=this.db.getWritableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("fname", etxtModWorkerFname.getText().toString());
		values.put("lname", etxtModWorkerLName.getText().toString());
		values.put("notes", etxtModWorkerNotes.getText().toString());
		values.put("proffession", etxtModWorkerProf.getText().toString());
		
		long i=d.update("WORKER", values, "_id=?",new String[] {position});
		d.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "Material Updated", Toast.LENGTH_LONG).show();
			WorkerModActivity.this.finish();
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
	}
}
