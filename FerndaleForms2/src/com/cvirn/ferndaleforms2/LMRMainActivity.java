package com.cvirn.ferndaleforms2;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cvirn.ferndaleforms2.lmr.LMRModifyActivity;
import com.cvirn.ferndaleforms2.lmr.LMROverviewActivity;
import com.cvirn.ferndaleforms2.lmr.LMRSubStep1Activity;
import com.cvirn.ferndaleforms2.lmr.LMRUploadActivity;

public class LMRMainActivity extends Activity implements OnClickListener {
	
	private Button btnExit;
	private Button btnLMROverview;
	private Button btnCreate;
	private Button btnModify;
	private Button btnUpload;
	private Button btnHelp;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmr_main_layout);
		this.btnExit = (Button) findViewById(R.id.btnExit);
		btnLMROverview = (Button) findViewById(R.id.btnLMROverview);
		btnLMROverview.setOnClickListener(over);
		// elements
		btnCreate=(Button)findViewById(R.id.btnLMRNew);
		btnCreate.setOnClickListener(create);
		
		btnModify=(Button)findViewById(R.id.btnLMRMod);
		btnModify.setOnClickListener(mod);
		
		btnUpload=(Button)findViewById(R.id.btnLMRUpload);
		btnUpload.setOnClickListener(upload);
		
		btnHelp=(Button)findViewById(R.id.btnLMRHelp);
		btnHelp.setOnClickListener(help);
		this.btnExit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				startActivity(new Intent(LMRMainActivity.this,MainActivity.class));
			}
		});
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	/////////////////////////////////////////
	//Listeners
	private OnClickListener create=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LMRMainActivity.this,LMRSubStep1Activity.class));
		}
	};
	
	private OnClickListener mod=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(LMRMainActivity.this,LMRModifyActivity.class));
			
		}
	};
	private OnClickListener over=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LMRMainActivity.this,LMROverviewActivity.class));
		}
	}; 
	
	private OnClickListener upload=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(LMRMainActivity.this,LMRUploadActivity.class));
		}
	};
	
private OnClickListener help=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(LMRMainActivity.this,LMRUploadActivity.class));
		}
	};
	
}
