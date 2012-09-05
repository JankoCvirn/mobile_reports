package com.cvirn.ferndaleforms2.femanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cvirn.ferndaleforms2.R;

public class ManagerActivity extends Activity {
	private Button btnManagerMod;
	private Button btnManagerNew;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_main);
		this.btnManagerMod = (Button) findViewById(R.id.btnManagerMod);
		this.btnManagerNew = (Button) findViewById(R.id.btnManagerNew);
		this.btnManagerNew.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				startActivity(new Intent(ManagerActivity.this,ManagerSubActivity.class));
			}
		});
		this.btnManagerMod.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				startActivity(new Intent(ManagerActivity.this,ManagerModActivity.class));
			}
		});
		
		
		
	}
}
