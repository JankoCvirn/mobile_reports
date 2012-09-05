package com.cvirn.ferndaleforms2.lmr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cvirn.ferndaleforms2.FormMenuActivity;
import com.cvirn.ferndaleforms2.LMRMainActivity;
import com.cvirn.ferndaleforms2.MainActivity;
import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.constants.LMReport;
import android.app.*;
import android.widget.*;
import android.view.*;

public class LMRSubStep2Activity extends Activity {
	
	private Button btnCreateSubC;
	private Button btnLMRSub2Finish;
	private Button btnCreateEquip;
	private Button btnCreateMaterial;
	private Button btnCreateLabor;
	Intent i;
	private EditText txtJobNumber;
	
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmr_sub_step2_layout);
		
		// TODO Put your code here
		
		this.btnCreateSubC = (Button) findViewById(R.id.btnCreateSubC);
		this.btnLMRSub2Finish = (Button) findViewById(R.id.btnLMRSub2Finish);
		this.btnCreateEquip = (Button) findViewById(R.id.btnCreateEquip);
		this.btnCreateMaterial = (Button) findViewById(R.id.btnCreateMaterial);
		this.btnCreateLabor = (Button) findViewById(R.id.btnCreateLabor);
		i=getIntent();
		final String jobnumber=i.getStringExtra(LMReport.LMR_JobNumber);
		
		txtJobNumber=(EditText)findViewById(R.id.editTextLMR_Sub2_JobNumber);
		txtJobNumber.setText("Details for JobNr. : - " +jobnumber+" -");
		txtJobNumber.setEnabled(false);
		this.btnCreateLabor.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Intent lmr_sub2=new Intent(getApplicationContext(),LMRSubLaborActivity.class);
				lmr_sub2.putExtra(LMReport.LMR_JobNumber, jobnumber);
				startActivity(lmr_sub2);
			}
		});
		this.btnCreateMaterial.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Intent lmr_sub2=new Intent(getApplicationContext(),LMRSubMaterialActivity.class);
				lmr_sub2.putExtra(LMReport.LMR_JobNumber, jobnumber);
				startActivity(lmr_sub2);
			}
		});
		this.btnCreateEquip.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Intent lmr_sub2=new Intent(getApplicationContext(),LMRSubEquipActivity.class);
				lmr_sub2.putExtra(LMReport.LMR_JobNumber, jobnumber);
				startActivity(lmr_sub2);
			}
		});
		this.btnLMRSub2Finish.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				Intent lmr_sub2=new Intent(getApplicationContext(),LMRMainActivity.class);
				//lmr_sub2.putExtra(LMReport.LMR_JobNumber, jobnumber);
				startActivity(lmr_sub2);
			}
		});
		this.btnCreateSubC.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Intent lmr_sub2=new Intent(getApplicationContext(),LMRSubContractActivity.class);
				lmr_sub2.putExtra(LMReport.LMR_JobNumber, jobnumber);
				startActivity(lmr_sub2);
			}
		});
		
		
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent lmr_sub2=new Intent(getApplicationContext(),FormMenuActivity.class);
		//lmr_sub2.putExtra(LMReport.LMR_JobNumber, jobnumber);
		startActivity(lmr_sub2);
		LMRSubStep2Activity.this.finish();
	}
}
