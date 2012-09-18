package com.cvirn.ferndaleforms2.lmr;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.constants.LMReport;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.*;
import android.widget.*;
import android.view.*;

public class LMROverSubActivity extends Activity {
	
	private GridView gridViewSubCon;
	private GestureOverlayView signature;
	private Button btnConfirm;
	private GridView gridViewEquip;
	private GridView gridViewMaterial;
	private GridView gridViewLabor;
	private EditText etxtWPerfLMRMod;
	private EditText etxtCustNrLMRMod;
	private EditText etxtFmanLMRMod;
	private EditText etxtJDateLMRMod;
	private EditText etxtJLocLMRMod;
	private EditText etxtJNameLMRMod;
	private EditText etxtCustomerLMRMod;
	private DbHelper db;
	Bitmap sig_image;
	Intent i;
	private String jobnumber;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmr_overview_sub);
		this.gridViewSubCon = (GridView) findViewById(R.id.gridViewSubCon);
		this.btnConfirm = (Button) findViewById(R.id.btnConfirm);
		this.gridViewEquip = (GridView) findViewById(R.id.gridViewEquip);
		this.gridViewMaterial = (GridView) findViewById(R.id.gridViewMaterial);
		this.gridViewLabor = (GridView) findViewById(R.id.gridViewLabor);
		this.etxtWPerfLMRMod = (EditText) findViewById(R.id.etxtWPerfLMRMod);
		this.etxtCustNrLMRMod = (EditText) findViewById(R.id.etxtCustNrLMRMod);
		this.etxtFmanLMRMod = (EditText) findViewById(R.id.etxtFmanLMRMod);
		this.etxtJDateLMRMod = (EditText) findViewById(R.id.etxtJDateLMRMod);
		this.etxtJLocLMRMod = (EditText) findViewById(R.id.etxtJLocLMRMod);
		this.etxtJNameLMRMod = (EditText) findViewById(R.id.etxtJNameLMRMod);
		this.etxtCustomerLMRMod = (EditText) findViewById(R.id.etxtCustomerLMRMod);
		this.signature=(GestureOverlayView)findViewById(R.id.signature);
		db = new DbHelper(this, true);
		
		
		etxtCustNrLMRMod.setEnabled(false);
		etxtCustomerLMRMod.setEnabled(false);
		etxtFmanLMRMod.setEnabled(false);
		etxtJDateLMRMod.setEnabled(false);
		etxtJLocLMRMod.setEnabled(false);
		etxtWPerfLMRMod.setEnabled(false);
		etxtJNameLMRMod.setEnabled(false);
		
		i=getIntent();
		jobnumber=i.getStringExtra(LMReport.LMR_JobNumber);
		getJobDetails();
		
		gridViewLabor.setAdapter(new DataAdapterLabor(this,jobnumber));
		gridViewMaterial.setAdapter(new DataAdapterMaterial(this,jobnumber));
		gridViewEquip.setAdapter(new DataAdapterEquip(this,jobnumber));
		gridViewSubCon.setAdapter(new DataAdapterSubContr(this, jobnumber));
		
		this.signature.addOnGesturePerformedListener(new OnGesturePerformedListener() {
			
			@Override
			public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
				sig_image=gesture.toBitmap(300, 150, 1, 1);
				
				
			       
			}
		});
		
		this.btnConfirm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				doSignatureSave();
			}
		});
		
		
		
	}
	
private void getJobDetails(){
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		String sql="SELECT * FROM JOB WHERE jobnumber=?";
		Cursor c=d.rawQuery(sql, new String[] {jobnumber});
		//Toast.makeText(this, "Put your message here"+c.getString(0), Toast.LENGTH_SHORT).show();
		//c.getString(0);
		if (c.moveToFirst()){
			etxtCustomerLMRMod.setText(c.getString(7));
			etxtCustNrLMRMod.setText(c.getString(0));
			etxtFmanLMRMod.setText(c.getString(1));
			etxtJDateLMRMod.setText(c.getString(3));
			etxtWPerfLMRMod.setText(c.getString(4));
			etxtJLocLMRMod.setText(c.getString(5));
			etxtJNameLMRMod.setText(c.getString(6));
		
		}
		d.close();
		
	}

private void doSignatureSave() {
	
	FileOutputStream out;
	try {
		out = new FileOutputStream("signature_"+jobnumber);
		
		sig_image.compress(Bitmap.CompressFormat.PNG, 90, out);
		
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}
