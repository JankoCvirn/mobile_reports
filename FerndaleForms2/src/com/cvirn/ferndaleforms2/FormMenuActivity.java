package com.cvirn.ferndaleforms2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FormMenuActivity extends Activity implements OnClickListener{
	
	private Button btnLMR;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formsmenu);
		setTitle("Forms Menu");
		
		btnLMR=(Button)findViewById(R.id.btnLMR);
		btnLMR.setOnClickListener(lmr);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	private OnClickListener lmr=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(FormMenuActivity.this,LMRMainActivity.class));
			
		}
	};
}
