package com.cvirn.ferndaleforms2.subcontract;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cvirn.ferndaleforms2.R;

public class SubContActivity extends Activity {
	private Button btnMod;
	private Button btnNew;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subcont_main);
		this.btnMod = (Button) findViewById(R.id.btnMod);
		this.btnNew = (Button) findViewById(R.id.btnNew);
		this.btnNew.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				startActivity(new Intent(SubContActivity.this,SubContSubActivity.class));	
			}
		});
		this.btnMod.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				startActivity(new Intent(SubContActivity.this,SubContModActivity.class));	
			}
		});
		
		
	}
}
