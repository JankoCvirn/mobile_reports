package com.cvirn.ferndaleforms2.material;





import com.cvirn.ferndaleforms2.R;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.*;
import android.view.*;

public class MaterialActivity extends Activity {
	
	
	
	private Button btnMaterialMod;
	private Button btnMaterialNew;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.material_main);
		this.btnMaterialMod = (Button) findViewById(R.id.btnMaterialMod);
		this.btnMaterialNew = (Button) findViewById(R.id.btnMaterialNew);
		this.btnMaterialNew.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				//Calls another activity, by name, without passing data

				Intent iExp = new Intent(MaterialActivity.this, MaterialSubActivity.class); 

				startActivity(iExp);
			}
		});
		this.btnMaterialMod.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Intent iExp = new Intent(MaterialActivity.this, MaterialModActivity.class); 

				startActivity(iExp);
				
			}
		});
		
		
	}
}
