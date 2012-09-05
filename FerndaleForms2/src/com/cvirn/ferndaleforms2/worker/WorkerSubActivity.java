package com.cvirn.ferndaleforms2.worker;

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

public class WorkerSubActivity extends Activity {
	private Button btnClearWorkerData;
	private Button btnCreateNewWorker;
	private EditText etxtWorkerNotes;
	private EditText etxtWorkerLName;
	private EditText etxtWorkerFname;
	private EditText etxtWorkerProf;
	

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		setContentView(R.layout.worker_new);
		this.btnClearWorkerData = (Button) findViewById(R.id.btnClearWorkerData);
		this.btnCreateNewWorker = (Button) findViewById(R.id.btnCreateNewWorker);
		this.etxtWorkerNotes = (EditText) findViewById(R.id.etxtWorkerNotes);
		this.etxtWorkerFname = (EditText) findViewById(R.id.etxtWorkerFname);
		this.etxtWorkerLName = (EditText) findViewById(R.id.etxtWorkerLName);
		this.etxtWorkerProf = (EditText) findViewById(R.id.etxtWorkerProf);
		this.btnCreateNewWorker.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				doSaveToDb();
				
			}
		});
		this.btnClearWorkerData.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				etxtWorkerFname.setText("");
				etxtWorkerLName.setText("");
				etxtWorkerNotes.setText("");
				etxtWorkerProf.setText("");
				
			}
		});
		
		
	}
	
private void doSaveToDb(){
		
		
		DbHelper db=new DbHelper(this, true);
		db.copyDatabaseFile();
		SQLiteDatabase base=db.getReadableDatabase();
		ContentValues values;
		values=new ContentValues();
		values.put("fname", etxtWorkerFname.getText().toString());
		values.put("lname", etxtWorkerLName.getText().toString()+","+etxtWorkerFname.getText().toString());
		values.put("proffession", etxtWorkerProf.getText().toString());
		values.put("notes", etxtWorkerNotes.getText().toString());
		long i=base.insert("WORKER", "null", values);
		base.close();
		
		if (i!=-1){
			
			Toast.makeText(this, "New worker saved", Toast.LENGTH_LONG).show();
			etxtWorkerFname.setText("");
			etxtWorkerLName.setText("");
			etxtWorkerNotes.setText("");
			etxtWorkerProf.setText("");
		}
		else{
			
			Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show();
			
		}
		
		
	}
	
	
	
}
