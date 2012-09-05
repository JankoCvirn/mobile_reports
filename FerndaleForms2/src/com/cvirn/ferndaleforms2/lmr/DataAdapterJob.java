/**
 * 
 */
package com.cvirn.ferndaleforms2.lmr;

import java.util.ArrayList;


import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author janko
 * @param <E>
 *
 */
public class DataAdapterJob extends BaseAdapter{

	Context mContext;
	private LayoutInflater mInflater;
	//static final String LMRLABOR="LMRLABOR";
	private ArrayList<String> id;
	
	private DbHelper db;
	private String jobnumber;
	
	 
	public DataAdapterJob(Context c,String jobnumber) {
	
		mContext=c;
		this.jobnumber=jobnumber;
		
		db=new DbHelper(c,true);
		if (jobnumber.equals("false")) {
			getData2();
		}
		else {
		getData();}
        mInflater = LayoutInflater.from(c);
	
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return id.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 ViewHolder holder=null;
         if(convertView==null)
         {	
        	 
       	  
       	  
        	 
        	 //Log.d("DataAdapterLabor", "inflating");
                convertView = mInflater.inflate(R.layout.customgrid, 
                                                               parent,false);
                holder = new ViewHolder();
                holder.txtId=(TextView)convertView.findViewById(R.id.txtId);
                holder.txtId.setPadding(20, 10,10 , 10);
               
                
                if(position==0)
                {                             
                      convertView.setTag(holder);
                }
         }
         else
         {
                holder = (ViewHolder) convertView.getTag();
         }
         holder.txtId.setText(id.get(position).toString());
         
         return convertView;
  }
  static class ViewHolder
  {        
         TextView txtId;
  }     
        
	
	
  private void getData() {
		
	  
	  this.id=new ArrayList<String>();
	  
	  
	  Log.d("DataAdapterLabor", "start reading db");
		SQLiteDatabase d=this.db.getReadableDatabase();
		String sql;
		String[] params= {this.jobnumber};
		
		
		
		sql="SELECT * FROM JOB WHERE jobnumber=?";
		
		
		Cursor c=d.rawQuery(sql, params);
		if(c.moveToFirst()) {
		
			this.id.add("JobID:"+c.getString(8)+" ---- Customer: "+c.getString(7) );
			
		
			while (c.moveToNext()) {
				
				this.id.add("JobID:"+c.getString(8)+" - Customer: "+c.getString(7));
				
				
			}
		
			
		d.close();
		}
	}	
  
private void getData2() {
		
	  
	  this.id=new ArrayList<String>();
	  
	  
	  Log.d("DataAdapterLabor", "start reading db");
		SQLiteDatabase d=this.db.getReadableDatabase();
		String sql;
		
		
		
		
		sql="SELECT * FROM JOB ORDER BY _id ASC";
		
		
		Cursor c=d.rawQuery(sql, null);
		if(c.moveToFirst()) {
		
			this.id.add("JobID:"+c.getString(8)+" --- Customer: "+c.getString(7));
			
			
			while (c.moveToNext()) {
				
				this.id.add("JobID:"+c.getString(8)+" --- Customer: "+c.getString(7));
				
				
			}
		
			
		d.close();
		}
	}	

  }
