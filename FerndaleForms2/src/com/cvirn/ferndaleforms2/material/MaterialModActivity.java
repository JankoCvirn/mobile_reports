package com.cvirn.ferndaleforms2.material;

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

public class MaterialModActivity extends Activity {
	
	private Button btnModMaterial;
	private EditText etxtModMaterialName;
	private Spinner spinnerMatMod;
	private DbHelper db;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.material_mod);
		
		this.btnModMaterial = (Button) findViewById(R.id.btnModMaterial);
		this.etxtModMaterialName = (EditText) findViewById(R.id.etxtModMaterialName);
		this.spinnerMatMod = (Spinner) findViewById(R.id.spinnerMatMod);
		db=new DbHelper(this, true);
		//
		
		
		Cursor mCursor;
		mCursor=this.getNames();
		startManagingCursor(mCursor);
		String[] from=new String[]{"name"};
		int[] to=new int[]{R.id.tvDBViewRow};
		
		SimpleCursorAdapter nameAdapter=new SimpleCursorAdapter(this, R.layout.db_view_row, mCursor, from, to);
		spinnerMatMod.setAdapter(nameAdapter);
		
		
		
		//
		this.btnModMaterial.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				setMatDetails();
			}
		});
		
		this.spinnerMatMod
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						getMatDetails();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		
	}
	
private Cursor getNames() {
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		//String[] columns={"_id","name"};
		return d.query( "MATERIAL",null, null, null, null, null,null, null);
		
	}
	
	private void getMatDetails() {
		
		Cursor mCursor=(Cursor)spinnerMatMod.getSelectedItem();	
		  String position=mCursor.getString(0);
		  SQLiteDatabase d=this.db.getWritableDatabase();
			String sql="SELECT * FROM MATERIAL WHERE _id=?";
			Cursor c=d.rawQuery(sql, new String[] {position});
			if (c.moveToFirst()){
				etxtModMaterialName.setText(c.getString(1));
				
				}
				d.close();
		
	}
	
	private void setMatDetails() {
		
		Cursor mCursor=(Cursor)spinnerMatMod.getSelectedItem();	
		  String position=mCursor.getString(0);
		
		SQLiteDatabase d=this.db.getWritableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("name", etxtModMaterialName.getText().toString());
		
		long i=d.update("MATERIAL", values, "_id=?",new String[] {position});
		d.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "Material Updated", Toast.LENGTH_LONG).show();
			MaterialModActivity.this.finish();
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
	}
}
