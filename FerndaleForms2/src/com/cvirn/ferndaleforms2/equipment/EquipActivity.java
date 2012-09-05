package com.cvirn.ferndaleforms2.equipment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cvirn.ferndaleforms2.R;

public class EquipActivity extends Activity {
	private Button btnEquipMod;
	private Button btnEquipNew;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eqip_main);
		this.btnEquipMod = (Button) findViewById(R.id.btnEquipMod);
		this.btnEquipNew = (Button) findViewById(R.id.btnEquipNew);
		this.btnEquipNew.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Intent i=new Intent(EquipActivity.this,EquipSubActivity.class);
				startActivity(i);
			}
		});
		this.btnEquipMod.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Intent i=new Intent(EquipActivity.this,EquipModActivity.class);
				startActivity(i);
			}
		});
		
		
		
	}
}
