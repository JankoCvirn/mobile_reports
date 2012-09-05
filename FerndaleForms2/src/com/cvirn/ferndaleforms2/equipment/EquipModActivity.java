package com.cvirn.ferndaleforms2.equipment;

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

public class EquipModActivity extends Activity {
	
	private Button btnCreateNewEquip;
	private EditText etxtModEquipName;
	private Spinner spinnerEquipMod;
	private DbHelper db;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eqip_mod);
		this.btnCreateNewEquip = (Button) findViewById(R.id.btnCreateNewEquip);
		this.etxtModEquipName = (EditText) findViewById(R.id.etxtModEquipName);
		this.spinnerEquipMod = (Spinner) findViewById(R.id.spinnerEquipMod);
		db=new DbHelper(this, true);
		
		Cursor eCursor;
		eCursor=this.getNames();
		startManagingCursor(eCursor);
		String[] from=new String[]{"name"};
		int[] to=new int[]{R.id.tvDBViewRow};
		
		SimpleCursorAdapter nameAdapter=new SimpleCursorAdapter(this, R.layout.db_view_row, eCursor, from, to);
		spinnerEquipMod.setAdapter(nameAdapter);
		
		
		
		this.btnCreateNewEquip.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				setEquipDetails();
			}
		});
		
		this.spinnerEquipMod
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						getEquipDetails();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		
		
	}
	
	private Cursor getNames() {
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		//String[] columns={"_id","name"};
		return d.query( "EQUIP",null, null, null, null, null,null, null);
		
	}
	
	private void getEquipDetails() {
		
		Cursor mCursor=(Cursor)spinnerEquipMod.getSelectedItem();	
		  String position=mCursor.getString(0);
		  SQLiteDatabase d=this.db.getWritableDatabase();
			String sql="SELECT * FROM EQUIP WHERE _id=?";
			Cursor c=d.rawQuery(sql, new String[] {position});
			if (c.moveToFirst()){
				etxtModEquipName.setText(c.getString(1));
				
				}
				d.close();
		
	}
	
	private void setEquipDetails() {
		
		Cursor mCursor=(Cursor)spinnerEquipMod.getSelectedItem();	
		  String position=mCursor.getString(0);
		
		SQLiteDatabase d=this.db.getWritableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("name", etxtModEquipName.getText().toString());
		
		long i=d.update("EQUIP", values, "_id=?",new String[] {position});
		d.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "Equipment Updated", Toast.LENGTH_LONG).show();
			EquipModActivity.this.finish();
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
	}
}
