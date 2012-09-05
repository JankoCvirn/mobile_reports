package com.cvirn.ferndaleforms2.location;

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

public class LocationModActivity extends Activity {
	
	private Button btnModLocation;
	private EditText etxtLocationDetails;
	private EditText etxtLocationName;
	private Spinner spinnerLocationMod;
	private DbHelper db;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_mod);
		this.btnModLocation = (Button) findViewById(R.id.btnModLocation);
		this.etxtLocationDetails = (EditText) findViewById(R.id.etxtModLocationDetails);
		this.etxtLocationName = (EditText) findViewById(R.id.etxtModLocationName);
		this.spinnerLocationMod = (Spinner) findViewById(R.id.spinnerLocationMod);
		db=new DbHelper(this, true);
		
		
		Cursor lCursor;
		lCursor=this.getNames();
		startManagingCursor(lCursor);
		String[] from=new String[]{"name"};
		int[] to=new int[]{R.id.tvDBViewRow};
		
		SimpleCursorAdapter nameAdapter=new SimpleCursorAdapter(this, R.layout.db_view_row, lCursor, from, to);
		spinnerLocationMod.setAdapter(nameAdapter);
		
		
		
		
		
		
		
		this.btnModLocation.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				setLocDetails();
			}
		});
		
		this.spinnerLocationMod
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						getLocDetails();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		
		
		
		
	}
	
private Cursor getNames() {
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		//String[] columns={"_id","name"};
		return d.query( "LOCATION",null, null, null, null, null,null, null);
		
	}
	
	private void getLocDetails() {
		
		Cursor mCursor=(Cursor)spinnerLocationMod.getSelectedItem();	
		  String position=mCursor.getString(0);
		  SQLiteDatabase d=this.db.getWritableDatabase();
			String sql="SELECT * FROM LOCATION WHERE _id=?";
			Cursor c=d.rawQuery(sql, new String[] {position});
			if (c.moveToFirst()){
				etxtLocationName.setText(c.getString(1));
				etxtLocationDetails.setText(c.getString(2));
				}
				d.close();
		
	}
	
	private void setLocDetails() {
		
		Cursor mCursor=(Cursor)spinnerLocationMod.getSelectedItem();	
		  String position=mCursor.getString(0);
		
		SQLiteDatabase d=this.db.getWritableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("name", etxtLocationName.getText().toString());
		values.put("details", etxtLocationDetails.getText().toString());
		long i=d.update("LOCATION", values, "_id=?",new String[] {position});
		d.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "Location Updated", Toast.LENGTH_LONG).show();
			LocationModActivity.this.finish();
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
	}
}
