package com.cvirn.ferndaleforms2.lmr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cvirn.ferndaleforms2.LMRMainActivity;
import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.constants.LMReport;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;
import android.app.*;
import android.widget.*;
import android.view.*;

public class LMRModifyActivity extends Activity {
	private Button btnLMRDeleteSubCon;
	private Button btnLMRUpdateSubCon;
	private EditText etxtLMRUpdateSubAm;
	private Spinner spinnerLMRSubConEdit;
	
	private Button btnLMRModAdd;
	private Button btnLMRDeleteEquip;
	private Button btnLMRUpdateEquip;
	private Button btnLMRDeleteMaterial;
	private Button btnLMRUpdateMaterial;
	private Button btnLMRDeleteLabor;
	private Button btnLMRUpdateLabor;
	
	private EditText etxtLMRUpdateEuipAm;
	private EditText etxtLMRmaterialAmountUpdate;
	private EditText etxtDTime;
	private EditText etxtHTime;
	private EditText etxtSTime;
	
	private Spinner spinnerLMREquipEdit;
	private Spinner spinnerLMRMaterialEdit;
	private Spinner spinnerLMRLaborEdit;
	
	private Button btnDeleteLMR;
	private Button btnChangeLMR;
	
	private EditText etxtWPerfLMRMod;
	private EditText etxtCustNrLMRMod;
	private EditText etxtFmanLMRMod;
	private EditText etxtJDateLMRMod;
	private EditText etxtJLocLMRMod;
	private EditText etxtJNameLMRMod;
	private EditText etxtCustomerLMRMod;
	private Spinner spinner1;
	
	private DbHelper db;
	

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmrmodify);
		this.btnLMRDeleteSubCon = (Button) findViewById(R.id.btnLMRDeleteSubCon);
		this.btnLMRUpdateSubCon = (Button) findViewById(R.id.btnLMRUpdateSubCon);
		this.etxtLMRUpdateSubAm = (EditText) findViewById(R.id.etxtLMRUpdateSubAm);
		this.spinnerLMRSubConEdit = (Spinner) findViewById(R.id.spinnerLMRSubConEdit);
		this.btnLMRModAdd = (Button) findViewById(R.id.btnLMRModAdd);
		this.btnLMRDeleteEquip = (Button) findViewById(R.id.btnLMRDeleteEquip);
		this.btnLMRUpdateEquip = (Button) findViewById(R.id.btnLMRUpdateEquip);
		this.btnLMRDeleteMaterial = (Button) findViewById(R.id.btnLMRDeleteMaterial);
		this.btnLMRUpdateMaterial = (Button) findViewById(R.id.btnLMRUpdateMaterial);
		this.btnLMRDeleteLabor = (Button) findViewById(R.id.btnLMRDeleteLabor);
		this.btnLMRUpdateLabor = (Button) findViewById(R.id.btnLMRUpdateLabor);
		this.etxtLMRUpdateEuipAm = (EditText) findViewById(R.id.etxtLMRUpdateEuipAm);
		this.etxtLMRmaterialAmountUpdate = (EditText) findViewById(R.id.etxtLMRmaterialAmountUpdate);
		this.etxtDTime = (EditText) findViewById(R.id.etxtDTime);
		this.etxtHTime = (EditText) findViewById(R.id.etxtHTime);
		this.etxtSTime = (EditText) findViewById(R.id.etxtSTime);
		this.spinnerLMREquipEdit = (Spinner) findViewById(R.id.spinnerLMREquipEdit);
		this.spinnerLMRMaterialEdit = (Spinner) findViewById(R.id.spinnerLMRMaterialEdit);
		this.spinnerLMRLaborEdit = (Spinner) findViewById(R.id.spinnerLMRLaborEdit);
		this.btnDeleteLMR = (Button) findViewById(R.id.btnDeleteLMR);
		this.btnChangeLMR = (Button) findViewById(R.id.btnChangeLMR);
		this.etxtWPerfLMRMod = (EditText) findViewById(R.id.etxtWPerfLMRMod);
		this.etxtCustNrLMRMod = (EditText) findViewById(R.id.etxtCustNrLMRMod);
		this.etxtFmanLMRMod = (EditText) findViewById(R.id.etxtFmanLMRMod);
		this.etxtJDateLMRMod = (EditText) findViewById(R.id.etxtJDateLMRMod);
		this.etxtJLocLMRMod = (EditText) findViewById(R.id.etxtJLocLMRMod);
		this.etxtJNameLMRMod = (EditText) findViewById(R.id.etxtJNameLMRMod);
		this.etxtCustomerLMRMod = (EditText) findViewById(R.id.etxtCustomerLMRMod);
		//this.spinner1 = (Spinner) findViewById(R.id.sp);
		///////////////////////////////////////////////////////////////////
		
		
		
		
		
		//////////////////////////////////////////////////////////////////
		db = new DbHelper(this, true);
		
		//db.copyDatabaseFile();
		
		//////////////////////////////////////////////////////////////////
		//Spinner populate with job numbers
		this.spinner1 = (Spinner) findViewById(R.id.spinnerLMRMod);
		Cursor jobnrCursor;
		jobnrCursor = this.getJobNumbers();
		startManagingCursor(jobnrCursor);
		String[] from = new String[] { "jobnumber" };
		int[] to = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter nameAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, jobnrCursor, from, to);
		this.spinner1.setAdapter(nameAdapter);
		
		//////////////////////////////////////////////////////////////////
		
		
		
		
		///////////////////////////////////////////////////////////////////
		this.btnChangeLMR.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
				builder.setMessage("Update Report details?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   setJobDetails();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   // put your code here 
				        	   dialog.cancel();
				           }
				       });
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
				
				
			}
		});
		this.btnDeleteLMR.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
				builder.setMessage("Dekete Report ?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   deleteJob();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   // put your code here 
				        	   dialog.cancel();
				           }
				       });
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
			}
		});
		
		this.spinner1
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						
							getJobDetails();
							getPopulateSub();
					}

					

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		this.spinnerLMRLaborEdit
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						
						getLaborDetails();
							
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		this.spinnerLMRMaterialEdit
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						getMaterialDetails();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		this.spinnerLMREquipEdit
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						getEquipDetails();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		this.btnLMRUpdateLabor.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
				builder.setMessage("Update Labor details?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   setLaborDetails();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   // put your code here 
				        	   dialog.cancel();
				           }
				       });
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
				
			}
		});
		this.btnLMRDeleteLabor.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
				builder.setMessage("Delete Labor details?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   deleteLabor();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   // put your code here 
				        	   dialog.cancel();
				           }
				       });
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
				
				
			}
		});
		this.btnLMRUpdateMaterial
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View target) {
						AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
						builder.setMessage("Update Material details?")
						       .setCancelable(false)
						       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	   setMaterialDetails();
						           }
						       })
						       .setNegativeButton("No", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	   // put your code here 
						        	   dialog.cancel();
						           }
						       });
						AlertDialog alertDialog = builder.create();
						alertDialog.show();
						
					}
				});
		this.btnLMRDeleteMaterial
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View target) {
						AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
						builder.setMessage("Delete Material details?")
						       .setCancelable(false)
						       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	   deleteMaterial();
						           }
						       })
						       .setNegativeButton("No", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	   // put your code here 
						        	   dialog.cancel();
						           }
						       });
						AlertDialog alertDialog = builder.create();
						alertDialog.show();
					}
				});
		this.btnLMRUpdateEquip.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
				builder.setMessage("Update Equipment details?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   setEquipmentDetails();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   // put your code here 
				        	   dialog.cancel();
				           }
				       });
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
			}
		});
		this.btnLMRDeleteEquip.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
				builder.setMessage("Delete Eqiupment details?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   deleteEquipment();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   // put your code here 
				        	   dialog.cancel();
				           }
				       });
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
			}
		});
		this.btnLMRModAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
				  String position=mCursor.getString(1);
				  
				if (position.length()!=0) {
				  
				Intent lmr_sub2=new Intent(getApplicationContext(),LMRSubStep2Activity.class);
				lmr_sub2.putExtra(LMReport.LMR_JobNumber, position);
				startActivity(lmr_sub2);}
				else {
					AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
					builder.setMessage("Please create a report.")
					       .setCancelable(false)
					       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	 

									Intent iExp = new Intent(LMRModifyActivity.this,LMRMainActivity.class); 
									
									startActivity(iExp);  
					           }
					       })
					       .setNegativeButton("No", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   // put your code here 
					        	   dialog.cancel();
					           }
					       });
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
					
					
				}
				
			}
		});
		this.btnLMRUpdateSubCon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
				builder.setMessage("Update Subcontractor details?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   setSubConDetails();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   // put your code here 
				        	   dialog.cancel();
				           }
				       });
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
			}
		});
		this.btnLMRDeleteSubCon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				AlertDialog.Builder builder = new AlertDialog.Builder(LMRModifyActivity.this);
				builder.setMessage("Delete Subcontractor details?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   deleteSubCon();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   // put your code here 
				        	   dialog.cancel();
				           }
				       });
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
			}
		});
		
		this.spinnerLMRSubConEdit
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long row) {
						getSubContDetails();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
		
		
		
		
		
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		
	}
	private void getPopulateSub(){
		
		
		Cursor laborCursor;
		laborCursor = this.getLabor();
		startManagingCursor(laborCursor);
		String[] from = new String[] { "worker" };
		int[] to = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter workerAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, laborCursor, from, to);
		this.spinnerLMRLaborEdit.setAdapter(workerAdapter);
		
		
		//Spiner for Material
		Cursor matCursor;
		matCursor = this.getMaterial();
		startManagingCursor(matCursor);
		String[] from2 = new String[] { "name" };
		int[] to2 = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter matAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, matCursor, from2, to2);
		this.spinnerLMRMaterialEdit.setAdapter(matAdapter);
		
		
		//Spiner for Equip
		Cursor eqCursor;
		eqCursor = this.getEquipment();
		startManagingCursor(eqCursor);
		String[] from3 = new String[] { "name" };
		int[] to3 = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter eqAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, eqCursor, from3, to3);
		this.spinnerLMREquipEdit.setAdapter(eqAdapter);
		
		//Spinner for Subcont
		Cursor scCursor;
		scCursor = this.getSunContr();
		startManagingCursor(scCursor);
		String[] from4 = new String[] { "name" };
		int[] to4 = new int[] { R.id.tvDBViewRow };

		SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this,
				R.layout.db_view_row, scCursor, from4, to4);
		this.spinnerLMRSubConEdit.setAdapter(scAdapter);
		
		
	}
	
	private Cursor getJobNumbers(){
		
		
		SQLiteDatabase d = this.db.getReadableDatabase();
		String[] columns = { "_id", "jobnumber" };
		return d.query("JOB", columns, null, null, null, null, null, null);
		
		
		
	}
	
	private void getJobDetails(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		  
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		String sql="SELECT * FROM JOB WHERE jobnumber=?";
		Cursor c=d.rawQuery(sql, new String[] {position});
		//Toast.makeText(this, "Put your message here"+c.getString(0), Toast.LENGTH_SHORT).show();
		//c.getString(0);
		if (c.moveToFirst()){
		etxtCustomerLMRMod.setText(c.getString(7));
		etxtCustNrLMRMod.setText(c.getString(0));
		etxtFmanLMRMod.setText(c.getString(1));
		etxtJDateLMRMod.setText(c.getString(3));
		etxtWPerfLMRMod.setText(c.getString(4));
		etxtJLocLMRMod.setText(c.getString(5));
		etxtJNameLMRMod.setText(c.getString(6));
		}
		d.close();
		
	}
	
	private void getLaborDetails(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String jobnr=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMRLaborEdit.getSelectedItem();
		  String worker=nCursor.getString(2);
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		String sql="SELECT * FROM LMRLABOR WHERE jobnr=? AND worker=?";
		Cursor c=d.rawQuery(sql, new String[] {jobnr,worker});
		//Toast.makeText(this, "Put your message here"+c.getString(0), Toast.LENGTH_SHORT).show();
		//c.getString(0);
		if (c.moveToFirst()){
		etxtSTime.setText(c.getString(3));
		etxtHTime.setText(c.getString(4));
		etxtDTime.setText(c.getString(5));
		
		}
		
		d.close();
		
		
	}
	
	private void getMaterialDetails(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String jobnr=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMRMaterialEdit.getSelectedItem();
		  String mat=nCursor.getString(2);
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		String sql="SELECT * FROM LMRMATERIAL WHERE jobnr=? AND name=?";
		Cursor c=d.rawQuery(sql, new String[] {jobnr,mat});
		//Toast.makeText(this, "Put your message here"+c.getString(0), Toast.LENGTH_SHORT).show();
		//c.getString(0);
		if (c.moveToFirst()){
		etxtLMRmaterialAmountUpdate.setText(c.getString(3));
		}
		d.close();
		
		
		
	}
	
	private void getEquipDetails(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String jobnr=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMREquipEdit.getSelectedItem();
		  String e=nCursor.getString(2);
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		String sql="SELECT * FROM LMREQUIP WHERE jobnr=? AND name=?";
		Cursor c=d.rawQuery(sql, new String[] {jobnr,e});
		//Toast.makeText(this, "Put your message here"+c.getString(0), Toast.LENGTH_SHORT).show();
		//c.getString(0);
		if (c.moveToFirst()){
		etxtLMRUpdateEuipAm.setText(c.getString(3));}
		
		
		d.close();
		
		
	}
	
private void getSubContDetails(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String jobnr=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMRSubConEdit.getSelectedItem();
		  String e=nCursor.getString(2);
		
		SQLiteDatabase d=this.db.getReadableDatabase();
		String sql="SELECT * FROM LMRSUBCONTR WHERE jobnr=? AND name=?";
		Cursor c=d.rawQuery(sql, new String[] {jobnr,e});
		//Toast.makeText(this, "Put your message here"+c.getString(0), Toast.LENGTH_SHORT).show();
		//c.getString(0);
		if (c.moveToFirst()){
		etxtLMRUpdateSubAm.setText(c.getString(3));}
		
		
		d.close();
		
		
	}
	
	
	private Cursor getLabor(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		  SQLiteDatabase d=this.db.getWritableDatabase();
			String sql="SELECT * FROM LMRLABOR WHERE jobnr=?";
			Cursor c=d.rawQuery(sql, new String[] {position});
			
			return c;
		 
		
	}
	private Cursor getMaterial(){
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		  SQLiteDatabase d=this.db.getWritableDatabase();
			String sql="SELECT * FROM LMRMATERIAL WHERE jobnr=?";
			Cursor c=d.rawQuery(sql, new String[] {position});
			
			return c;
	}
	private Cursor getEquipment(){
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		  SQLiteDatabase d=this.db.getWritableDatabase();
			String sql="SELECT * FROM LMREQUIP WHERE jobnr=?";
			Cursor c=d.rawQuery(sql, new String[] {position});
			
			return c;
			
	}
	
	private Cursor getSunContr(){
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		  SQLiteDatabase d=this.db.getWritableDatabase();
			String sql="SELECT * FROM LMRSUBCONTR WHERE jobnr=?";
			Cursor c=d.rawQuery(sql, new String[] {position});
			
			return c;
			
	}
	
	//
	
	private void setJobDetails(){}
	
	//delete job and close activity
	private void deleteJob(){
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		SQLiteDatabase d=this.db.getWritableDatabase();
		
		int rows_aff= d.delete("JOB","jobnumber=?", new String[]{position});
		
		//Log.d("SQL-DELETE-LABOR", "rows:"+rows_aff);
		d.close();
		if (rows_aff==1) {
		 LMRModifyActivity.this.finish();
		}
		else {
			
			Toast.makeText(this, "Job delete failed.", Toast.LENGTH_LONG).show();
		}
		
	}
	//
	
	private void setLaborDetails(){
		
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMRLaborEdit.getSelectedItem();
		  String worker=nCursor.getString(2);  
		  
		  SQLiteDatabase d=this.db.getWritableDatabase();
		  ContentValues cv=new ContentValues();
		  cv.put("stime",etxtSTime.getText().toString() );
		  cv.put("htime",etxtHTime.getText().toString() );
		  cv.put("dtime",etxtDTime.getText().toString() );
		  d.update("LMRLABOR", cv, "jobnr=? and worker=?", new String[]{position,worker});
		  d.close();
		
		
	}
	
	private void deleteLabor(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMRLaborEdit.getSelectedItem();
		  String worker=nCursor.getString(2);  
		  
		  SQLiteDatabase d=this.db.getWritableDatabase();
			
			
			int rows_aff= d.delete("LMRLABOR","jobnr=? and worker=? ", new String[]{position,worker});
			
			//Log.d("SQL-DELETE-LABOR", "rows:"+rows_aff);
			d.close();
			if (rows_aff==1) {
			 LMRModifyActivity.this.finish();
			}
			else {
				
				Toast.makeText(this, "Labor delete failed.", Toast.LENGTH_LONG).show();
			}
			
			
		
	}
	
	//
	
	private void setMaterialDetails(){
		
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMRMaterialEdit.getSelectedItem();
		  String mat=nCursor.getString(2);  
		  
		  SQLiteDatabase d=this.db.getWritableDatabase();
		  ContentValues cv=new ContentValues();
		  cv.put("ammount",etxtLMRmaterialAmountUpdate.getText().toString() );
		  
		  d.update("LMRMATERIAL", cv, "jobnr=? and name=?", new String[]{position,mat});
		  d.close();
		  updateAndExit();
		
	}
	private void deleteMaterial(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMRMaterialEdit.getSelectedItem();
		  String name=nCursor.getString(2);  
		  
		  SQLiteDatabase d=this.db.getWritableDatabase();
			
			
			int rows_aff= d.delete("LMRMATERIAL","jobnr=? and name=? ", new String[]{position,name});
			
			//Log.d("SQL-DELETE-LABOR", "rows:"+rows_aff);
			d.close();
			if (rows_aff==1) {
			 updateAndExit();
			}
			else {
				
				Toast.makeText(this, "Material delete failed.", Toast.LENGTH_LONG).show();
			}
		
	}
	//
	
	private void setEquipmentDetails(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMREquipEdit.getSelectedItem();
		  String eq=nCursor.getString(2);  
		  
		  SQLiteDatabase d=this.db.getWritableDatabase();
		  ContentValues cv=new ContentValues();
		  cv.put("ammount",etxtLMRUpdateEuipAm.getText().toString() );
		  
		  d.update("LMREQUIP", cv, "jobnr=? and name=?", new String[]{position,eq});
		  d.close();
		  updateAndExit();
		
		
	}
	private void deleteEquipment(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMREquipEdit.getSelectedItem();
		  String name=nCursor.getString(2);  
		  
		  SQLiteDatabase d=this.db.getWritableDatabase();
			
			
			int rows_aff= d.delete("LMREQUIP","jobnr=? and name=? ", new String[]{position,name});
			
			//Log.d("SQL-DELETE-LABOR", "rows:"+rows_aff);
			d.close();
			if (rows_aff==1) {
			 updateAndExit();
			}
			else {
				
				Toast.makeText(this, "Equipment delete failed.", Toast.LENGTH_LONG).show();
			}
		
	}
	
    private void setSubConDetails(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMRSubConEdit.getSelectedItem();
		  String sc=nCursor.getString(2);  
		  
		  SQLiteDatabase d=this.db.getWritableDatabase();
		  ContentValues cv=new ContentValues();
		  cv.put("amount",etxtLMRUpdateSubAm.getText().toString() );
		  
		  d.update("LMRSUBCONTR", cv, "jobnr=? and name=?", new String[]{position,sc});
		  d.close();
		  updateAndExit();
		
		
	}
	private void deleteSubCon(){
		
		Cursor mCursor=(Cursor)spinner1.getSelectedItem();	
		  String position=mCursor.getString(1);
		Cursor nCursor=(Cursor)spinnerLMRSubConEdit.getSelectedItem();
		  String name=nCursor.getString(2);  
		  
		  SQLiteDatabase d=this.db.getWritableDatabase();
			
			
			int rows_aff= d.delete("LMRSUBCONTR","jobnr=? and name=? ", new String[]{position,name});
			
			//Log.d("SQL-DELETE-LABOR", "rows:"+rows_aff);
			d.close();
			if (rows_aff==1) {
			 updateAndExit();
			}
			else {
				
				Toast.makeText(this, "Subcontractor delete failed.", Toast.LENGTH_LONG).show();
			}
		
	}
	
	private void updateAndExit() {
		
		Toast.makeText(this, "Report updated.", Toast.LENGTH_LONG).show();
		LMRModifyActivity.this.finish();
		
	}
}
