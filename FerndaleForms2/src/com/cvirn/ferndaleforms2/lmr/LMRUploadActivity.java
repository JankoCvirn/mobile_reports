package com.cvirn.ferndaleforms2.lmr;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.bean.LMREquipmentBean;
import com.cvirn.ferndaleforms2.bean.LMRJobBean;
import com.cvirn.ferndaleforms2.bean.LMRLaborBean;
import com.cvirn.ferndaleforms2.bean.LMRMaterialBean;
import com.cvirn.ferndaleforms2.bean.LMRSubConBean;
import com.cvirn.ferndaleforms2.cloud.WebServClientLMR;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

public class LMRUploadActivity extends Activity {
	
	private Button btnLMROverUpload;
	private Spinner spinnerLMROverMainReport;
	String jobnur;
	private DbHelper db;
	private LMRJobBean job;
	private ArrayList<LMRMaterialBean> mat_holder;
	private ArrayList<LMRLaborBean> lab_holder;
	private ArrayList<LMREquipmentBean> eqp_holder;
	private ArrayList<LMRSubConBean> sub_holder;
	SharedPreferences sp;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmr_upload);
		
		this.btnLMROverUpload = (Button) findViewById(R.id.btnLMROverUpload);
		db = new DbHelper(this, true);
		this.spinnerLMROverMainReport = (Spinner) findViewById(R.id.spinnerLMROverMainReport);
		GridView gridview = (GridView) findViewById(R.id.gridViewLMROver); 
		String jobnur="false";
		gridview.setAdapter(new DataAdapterJob(this,jobnur));
		
		sp=PreferenceManager.getDefaultSharedPreferences(this);
		
		
		Cursor jobsCursor;
		jobsCursor = this.getJobs();
		startManagingCursor(jobsCursor);
		String[] from = new String[] { "_id" };
		int[] to = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter nameAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, jobsCursor, from, to);
		this.spinnerLMROverMainReport.setAdapter(nameAdapter);
		this.btnLMROverUpload.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(LMRUploadActivity.this);
					builder.setMessage("Upload the report?")
					       .setCancelable(false)
					       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                //doUpload();
					        	   //Toast.makeText(LMRUploadActivity.this, "This will trigger the report upload.", Toast.LENGTH_LONG).show();
					        	   doUpload();
					           }
					       })
					       .setNegativeButton("No", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   
					        	   dialog.cancel();
					           }
					       });
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
									
								}
		});
		
	}
	
	
private Cursor getJobs(){
		
		SQLiteDatabase d = this.db.getReadableDatabase();
		String[] columns = { "_id","jobnumber" };
		return d.query("JOB", columns, null, null, null, null, null, null);
		
		
		
	}

private void doUpload() {
	
	Cursor mCursor=(Cursor)spinnerLMROverMainReport.getSelectedItem();
	String pass=mCursor.getString(1);
	mCursor.close();
	
	SQLiteDatabase d=this.db.getReadableDatabase();
	String sql="SELECT * FROM JOB WHERE jobnumber=?";
	Cursor c=d.rawQuery(sql, new String[] {pass});
	//Toast.makeText(this, "Put your message here"+c.getString(0), Toast.LENGTH_SHORT).show();
	//c.getString(0);
	job=new LMRJobBean();
	
	if (c.moveToFirst()){
		job.setCustomer(c.getString(7));
		job.setCustomernr(c.getString(0));
		job.setDate(c.getString(3));
		job.setFecmanager(c.getString(1));
		job.setJoblocation(c.getString(5));
		job.setJobname(c.getString(6));
		job.setJobnumber(c.getString(2));
		job.setWorkperformed(c.getString(4));
	}
	d.close();
	
	
	//////////////////////////////////////////////
	//Labor
	d=this.db.getReadableDatabase();
	String[] params= {pass};
	sql="SELECT * FROM LMRLABOR WHERE jobnr=?";
	c=d.rawQuery(sql, params);
	lab_holder=new ArrayList<LMRLaborBean>();
	
	if(c.moveToFirst()) {
	
		LMRLaborBean lbean=new LMRLaborBean();
		lbean.setWorker(c.getString(2));
		lbean.setStime(c.getString(3));
		lbean.setHtime(c.getString(4));
		lbean.setDtime(c.getString(5));
		
		lab_holder.add(lbean);
	
		while (c.moveToNext()) {
			
			lbean=new LMRLaborBean();
			lbean.setWorker(c.getString(2));
			lbean.setStime(c.getString(3));
			lbean.setHtime(c.getString(4));
			lbean.setDtime(c.getString(5));
			
			lab_holder.add(lbean);
			
			
		}
	
		
	d.close();
	}
	///////////////////////////////////////////
	//Mat
	d=this.db.getReadableDatabase();
	sql="SELECT * FROM LMRMATERIAL WHERE jobnr=?";
	c=d.rawQuery(sql, params);
	mat_holder=new ArrayList<LMRMaterialBean>();
	
	if(c.moveToFirst()) {
	
		LMRMaterialBean mbean=new LMRMaterialBean();
		mbean.setName(c.getString(2));
		mbean.setAmmount(c.getString(3));
		mat_holder.add(mbean);
		
	
		while (c.moveToNext()) {
			
			mbean.setName(c.getString(2));
			mbean.setAmmount(c.getString(3));
			mat_holder.add(mbean);
			
			
		}
	
		
	d.close();
	}
	
	///////////////////////////////////////////
	//Equip
	d=this.db.getReadableDatabase();
	sql="SELECT * FROM LMREQUIP WHERE jobnr=?";
	c=d.rawQuery(sql, params);
	eqp_holder=new ArrayList<LMREquipmentBean>();
	
	if(c.moveToFirst()) {
	
		LMREquipmentBean ebean=new LMREquipmentBean();
		ebean.setName(c.getString(2));
		ebean.setAmmount(c.getString(3));
		eqp_holder.add(ebean);
	
		while (c.moveToNext()) {
			ebean.setName(c.getString(2));
			ebean.setAmmount(c.getString(3));
			eqp_holder.add(ebean);
			
			
			
		}
	
		
	d.close();
	}
	//////////////////////////////////////////////
	//SubContractor
	d=this.db.getReadableDatabase();
	sql="SELECT * FROM LMRSUBCONTR WHERE jobnr=?";
	c=d.rawQuery(sql, params);
	sub_holder=new ArrayList<LMRSubConBean>();
	
	if(c.moveToFirst()) {
	
		LMRSubConBean sbean=new LMRSubConBean();
		sbean.setName(c.getString(2));
		sbean.setAmmount(c.getString(3));
		sub_holder.add(sbean);
	
		while (c.moveToNext()) {
			sbean.setName(c.getString(2));
			sbean.setAmmount(c.getString(3));
			sub_holder.add(sbean);
			
			
			
		}
	
		
	d.close();
	}
	
	///////////////////////////////////////////
	new PostToCloud().execute("");
	///////////////////////////////////////////
	//new PostEqpToCloud().execute("");
	//new PostLaborToCloud().execute("");
	//new PostMatToCloud().execute("");
	
	//Log.d("UPLOAD Activity","Lab_holder size:"+lab_holder.size());
	//Log.d("UPLOAD Activity","Mat_holder size:"+mat_holder.size());
	//Log.d("UPLOAD Activity","Equip_holder size:"+eqp_holder.size());
}

class PostToCloud extends AsyncTask<String, String, String>{
	ProgressDialog dialog = ProgressDialog.show(LMRUploadActivity.this, "Sending Report.", "Please wait...", true);
	@Override
	protected String doInBackground(String... params) {
		WebServClientLMR client=new WebServClientLMR();
		client.setUsername(sp.getString("username", ""));
		client.setPassword(sp.getString("password", ""));
		client.setJobbean(job);
		String result=client.getPostNewReport();
		//Log.d("POST","POST job");
		//Labor upload
		for (int i=0;i<lab_holder.size();i++) {
			client.setLabor_bean(lab_holder.get(i));
			String l=client.getPostReportDetailsLabor();
			//Log.d("POST","POST Labor result:"+l);
		}
		//Material upload
		for (int i=0;i<mat_holder.size();i++) {
			client.setMat_bean(mat_holder.get(i));
			String m=client.getPostReportDetailsMat();
			//Log.d("POST","POST mat result:"+m);
			
		}
		//Equipment upload
		for (int i=0;i<eqp_holder.size();i++) {
			client.setEquip_bean(eqp_holder.get(i));
			String e=client.getPostReportDetailsEquip();
			//Log.d("POST","POST eq result="+e);
			
		}
		//SubContractor upload
		for (int i=0;i<sub_holder.size();i++) {
			client.setSub_bean(sub_holder.get(i));
			String s=client.getPostReportDetailsSubCon();
			Log.d("POST","POST eq result="+s);
			
		}
		//Signature upload
		String sig=client.DoFileUpload(job.getJobnumber());
		
		Log.d("POST-SIG",sig);
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.d("POST",result);
		dialog.dismiss();
		LMRUploadActivity.this.finish();
		
	}
}

/*class PostLaborToCloud extends AsyncTask<String, String, String>{
	ProgressDialog dialog = ProgressDialog.show(LMRUploadActivity.this, "Sending Labor details.", "Please wait...", true);
	@Override
	protected String doInBackground(String... params) {
		WebServClientLMR client=new WebServClientLMR();
		client.setUsername(sp.getString("username", ""));
		client.setPassword(sp.getString("password", ""));
		for (int i=0;i<lab_holder.size();i++) {
			client.setLabor_bean(lab_holder.get(i));
			client.getPostReportDetailsLabor();
			Log.d("POST","POST labor");
		}
		String s="";
		return s;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.dismiss();
	}
}

class PostMatToCloud extends AsyncTask<String, String, String>{
	ProgressDialog dialog = ProgressDialog.show(LMRUploadActivity.this, "Sending Material Details.", "Please wait...", true);
	@Override
	protected String doInBackground(String... params) {
		WebServClientLMR client=new WebServClientLMR();
		client.setUsername(sp.getString("username", ""));
		client.setPassword(sp.getString("password", ""));
		for (int i=0;i<mat_holder.size();i++) {
			client.setMat_bean(mat_holder.get(i));
			client.getPostReportDetailsMat();
			Log.d("POST","POST mat");
			
		}
		String s="";
		return s;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.dismiss();
	}
}
class PostEqpToCloud extends AsyncTask<String, String, String>{
	ProgressDialog dialog = ProgressDialog.show(LMRUploadActivity.this, "Sending Equipment Details.", "Please wait...", true);
	@Override
	protected String doInBackground(String... params) {
		WebServClientLMR client=new WebServClientLMR();
		client.setUsername(sp.getString("username", ""));
		client.setPassword(sp.getString("password", ""));
		for (int i=0;i<eqp_holder.size();i++) {
			client.setEquip_bean(eqp_holder.get(i));
			client.getPostReportDetailsEquip();
			Log.d("POST","POST eq");
			
		}
		String s="";
		return s;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.dismiss();
	}
}*/
}
