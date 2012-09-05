package com.cvirn.ferndaleforms2.lmr;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.constants.LMReport;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

public class LMROverviewActivity extends Activity {
	private Button btnLMROverSelect;
	private Spinner spinnerLMROverMainReport;
	String jobnur;
	private DbHelper db;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmr_overview_main);
		this.btnLMROverSelect = (Button) findViewById(R.id.btnLMROverSelect);
		db = new DbHelper(this, true);
		this.spinnerLMROverMainReport = (Spinner) findViewById(R.id.spinnerLMROverMainReport);
		GridView gridview = (GridView) findViewById(R.id.gridViewLMROver); 
		String jobnur="false";
		gridview.setAdapter(new DataAdapterJob(this,jobnur));
		
		Cursor jobsCursor;
		jobsCursor = this.getJobs();
		startManagingCursor(jobsCursor);
		String[] from = new String[] { "_id" };
		int[] to = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter nameAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, jobsCursor, from, to);
		this.spinnerLMROverMainReport.setAdapter(nameAdapter);
		
		
		
		
		
		
		
		this.btnLMROverSelect.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				goSubActivity();
			}
		});
		
		
		
	}
	
	private void goSubActivity() {
		
		Cursor mCursor=(Cursor)spinnerLMROverMainReport.getSelectedItem();
		String pass=mCursor.getString(1);
		//START SUB ACTIVITY WITH INTENT
		Intent lmr_sub2=new Intent(getApplicationContext(),LMROverSubActivity.class);
		lmr_sub2.putExtra(LMReport.LMR_JobNumber, pass);
		startActivity(lmr_sub2);
		
	}
	
private Cursor getJobs(){
		
		SQLiteDatabase d = this.db.getReadableDatabase();
		String[] columns = { "_id","jobnumber" };
		return d.query("JOB", columns, null, null, null, null, null, null);
		
		
		
	}
}
