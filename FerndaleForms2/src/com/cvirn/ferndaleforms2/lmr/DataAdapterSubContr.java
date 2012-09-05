/**
 * 
 */
package com.cvirn.ferndaleforms2.lmr;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.dbhelper.DbHelper;

/**
 * @author janko
 * @param <E>
 *
 */
public class DataAdapterSubContr extends BaseAdapter{

	Context mContext;
	private LayoutInflater mInflater;
	static final String MAT="LMRMATERIAL";
	private ArrayList<String> id;
	private ArrayList<String> name;
	private ArrayList<String> amount;
	
	
	private DbHelper db;
	private String jobnumber;
	
	 
	public DataAdapterSubContr(Context c,String jobnumber) {
	
		mContext=c;
		this.jobnumber=jobnumber;
		
		db=new DbHelper(c,true);
		getData();
        mInflater = LayoutInflater.from(c);
	
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//Log.d("DataAdapterLabor","Inside getCount()");
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
        	
       	  
       	  //Log.d("DataAdapterLabor", "start reading db");
       		
        	 
        	 //Log.d("DataAdapterLabor", "inflating");
                convertView = mInflater.inflate(R.layout.grid_lmr_material, 
                                                               parent,false);
                holder = new ViewHolder();
                holder.txtId=(TextView)convertView.findViewById(R.id.txtId);
                holder.txtId.setPadding(20, 10,10 , 10);
                holder.txtName=(TextView)convertView.findViewById(R.id.txtId2);
                holder.txtAmount=(TextView)convertView.findViewById(R.id.txtId3);
                
                
                
                
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
         holder.txtName.setText(name.get(position).toString());
         holder.txtAmount.setText(amount.get(position).toString());
         
         return convertView;
  }
  static class ViewHolder
  {        
         TextView txtId;        
         TextView txtName;
         TextView txtAmount;
         
         
  }
	
	
  private void getData() {
		//TODO initialize arrays...
	  
	  
	  
	  id=new ArrayList<String>();
	  this.name=new ArrayList<String>();
	  this.amount=new ArrayList<String>();
	  
	  
	  Log.d("DataAdapterLabor", "start reading db");
		SQLiteDatabase d=this.db.getReadableDatabase();
		
		String[] params= {this.jobnumber};
		String sql="SELECT * FROM LMRSUBCONTR WHERE jobnr=?";
		Cursor c=d.rawQuery(sql, params);
		
		
		if(c.moveToFirst()) {
		
			this.id.add(c.getString(0)+".");
			this.name.add(c.getString(2));
			this.amount.add(c.getString(3));
		
			while (c.moveToNext()) {
				
				this.id.add(c.getString(0)+".");
				this.name.add(c.getString(2));
				this.amount.add(c.getString(3));
			
				
			}
		
			
		d.close();
		}
	}	

}
