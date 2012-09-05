package com.cvirn.ferndaleforms2.femanager;

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

public class ManagerModActivity extends Activity {
	
	private Button btnModManager;
	private EditText etxtModManagerName;
	private Spinner spinnerManagerMod;
	private DbHelper db;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_mod);
		this.btnModManager = (Button) findViewById(R.id.btnModManager);
		this.etxtModManagerName = (EditText) findViewById(R.id.etxtModManagerName);
		this.spinnerManagerMod = (Spinner) findViewById(R.id.spinnerManagerMod);
		db=new DbHelper(this, true);
		
		Cursor mCursor;
		mCursor=this.getNames();
		startManagingCursor(mCursor);
		String[] from=new String[]{"name"};
		int[] to=new int[]{R.id.tvDBViewRow};
		
		SimpleCursorAdapter nameAdapter=new SimpleCursorAdapter(this, R.layout.db_view_row, mCursor, from, to);
		spinnerManagerMod.setAdapter(nameAdapter);
		
		
		
		
		this.btnModManager.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				setManDetails();
			}
		});
		
		this.spinnerManagerMod
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						getManDetails();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		
		
	}
	
	
	
	private Cursor getNames() {
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		//String[] columns={"_id","name"};
		return d.query( "MANAGER",null, null, null, null, null,null, null);
		
	}
	private void getManDetails() {
		
		Cursor mCursor=(Cursor)spinnerManagerMod.getSelectedItem();	
		  String position=mCursor.getString(0);
		  SQLiteDatabase d=this.db.getWritableDatabase();
			String sql="SELECT * FROM MANAGER WHERE _id=?";
			Cursor c=d.rawQuery(sql, new String[] {position});
			if (c.moveToFirst()){
				etxtModManagerName.setText(c.getString(1));
				
				}
				d.close();
		
	}
	
	private void setManDetails() {
		
		Cursor mCursor=(Cursor)spinnerManagerMod.getSelectedItem();	
		  String position=mCursor.getString(0);
		
		SQLiteDatabase d=this.db.getWritableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("name", etxtModManagerName.getText().toString());
		
		long i=d.update("MANAGER", values, "_id=?",new String[] {position});
		d.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "Manager Updated", Toast.LENGTH_LONG).show();
			ManagerModActivity.this.finish();
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
	}
	
	
}
