package com.cvirn.ferndaleforms2.worker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cvirn.ferndaleforms2.R;

public class WorkerActivity extends Activity {
	private Button btnWorkerMod;
	private Button btnWorkerNew;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		setContentView(R.layout.worker_main);
		this.btnWorkerMod = (Button) findViewById(R.id.btnWorkerMod);
		this.btnWorkerNew = (Button) findViewById(R.id.btnWorkerNew);
		this.btnWorkerNew.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Intent i=new Intent(WorkerActivity.this,WorkerSubActivity.class);
				startActivity(i);
			}
		});
		this.btnWorkerMod.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Intent i=new Intent(WorkerActivity.this,WorkerModActivity.class);
				startActivity(i);
			}
		});
		
		
		
		
	}
}
