package com.cvirn.ferndaleforms2;





import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cvirn.ferndaleforms2.customer.CustomerActivity;
import com.cvirn.ferndaleforms2.equipment.EquipActivity;
import com.cvirn.ferndaleforms2.femanager.ManagerActivity;
import com.cvirn.ferndaleforms2.location.LocationActivity;
import com.cvirn.ferndaleforms2.material.MaterialActivity;
import com.cvirn.ferndaleforms2.subcontract.SubContActivity;
import com.cvirn.ferndaleforms2.worker.WorkerActivity;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button btnSubCont;
	private Button btnManager;
	private Button btnUnits;
	private Button btnEqip;
	private Button btnMate;
	private Button btnLocations;
	private Button btnCust;
	private Button btnWork;
	private Button btnPrefs;
	private Button btnForms;
	
	
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		this.btnSubCont = (Button) findViewById(R.id.btnSubCont);
		this.btnManager = (Button) findViewById(R.id.btnManager);
		this.btnUnits = (Button) findViewById(R.id.btnUnits);
		this.btnEqip = (Button) findViewById(R.id.btnEqip);
		this.btnMate = (Button) findViewById(R.id.btnMate);
		this.btnLocations = (Button) findViewById(R.id.btnLocations);
		this.btnCust = (Button) findViewById(R.id.btnCust);
		this.btnWork = (Button) findViewById(R.id.btnWork);
		
		
		
		
		btnPrefs=(Button)findViewById(R.id.btnPrefs);
		 btnPrefs.setOnClickListener(prefs);
		
		btnForms=(Button)findViewById(R.id.btnForms);
		 btnForms.setOnClickListener(forms);
		this.btnWork.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				startActivity(new Intent(MainActivity.this,WorkerActivity.class));
			}
		});
		this.btnCust.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				startActivity(new Intent(MainActivity.this,CustomerActivity.class));
			}
		});
		this.btnLocations.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				startActivity(new Intent(MainActivity.this,LocationActivity.class));	
			}
		});
		this.btnMate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				startActivity(new Intent(MainActivity.this,MaterialActivity.class));
			}
		});
		this.btnEqip.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				startActivity(new Intent(MainActivity.this,EquipActivity.class));
			}
		});
		this.btnUnits.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Feature not implemented.")
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                
				           }
				       });
				       
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
			}
		});
		this.btnManager.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				startActivity(new Intent(MainActivity.this,ManagerActivity.class));
			}
		});
		this.btnSubCont.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				startActivity(new Intent(MainActivity.this,SubContActivity.class));
			}
		});
		 
		 
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
private OnClickListener prefs=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			startActivity(new Intent(MainActivity.this,PreferencesActivity.class));
		}
	};
	
private OnClickListener forms=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			startActivity(new Intent(MainActivity.this,FormMenuActivity.class));
		}
	};
	
}
