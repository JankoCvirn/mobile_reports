package com.cvirn.ferndaleforms2.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cvirn.ferndaleforms2.R;

public class LocationActivity extends Activity {
	private Button btnLocationMod;
	private Button btnLocationNew;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_main);
		this.btnLocationMod = (Button) findViewById(R.id.btnLocationMod);
		this.btnLocationNew = (Button) findViewById(R.id.btnLocationNew);
		this.btnLocationNew.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				Intent iExp = new Intent(LocationActivity.this, LocationSubActivity.class); 

				startActivity(iExp);
			}
		});
		this.btnLocationMod.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Intent iExp = new Intent(LocationActivity.this, LocationModActivity.class); 

				startActivity(iExp);
			}
		});
		
		
	}
}
