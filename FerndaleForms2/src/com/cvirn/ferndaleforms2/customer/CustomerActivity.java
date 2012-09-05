package com.cvirn.ferndaleforms2.customer;

import com.cvirn.ferndaleforms2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomerActivity extends Activity {
	private Button btnCustomerMod;
	private Button btnCustomerNew;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		setContentView(R.layout.customer_main);
		this.btnCustomerMod = (Button) findViewById(R.id.btnCustomerMod);
		this.btnCustomerNew = (Button) findViewById(R.id.btnCustomerNew);
		this.btnCustomerNew.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				startActivity(new Intent(CustomerActivity.this,CustomerSubActivity.class));
			}
		});
		this.btnCustomerMod.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				startActivity(new Intent(CustomerActivity.this,CustomerUpdateActivity.class));
			}
		});
		
		
		
	}
}
