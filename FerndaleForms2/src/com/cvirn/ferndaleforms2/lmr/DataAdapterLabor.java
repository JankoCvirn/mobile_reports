/**
 * 
 */
package com.cvirn.ferndaleforms2.lmr;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
public class DataAdapterLabor extends BaseAdapter{

	Context mContext;
	private LayoutInflater mInflater;
	static final String LMRLABOR="LMRLABOR";
	private ArrayList<String> id;
	private ArrayList<String> name;
	private ArrayList<String> ammount_s;
	private ArrayList<String> ammount_h;
	private ArrayList<String> ammount_d;
	private DbHelper db;
	private String jobnumber;
	
	 
	public DataAdapterLabor(Context c,String jobnumber) {
	
		mContext=c;
		this.jobnumber=jobnumber;
		
		db=new DbHelper(c,true);
		getData();
        mInflater = LayoutInflater.from(c);
	
	}
	@Override
	public int getCount() {
		
		
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
        	 
        	 
        	 
        	 
        	 
                convertView = mInflater.inflate(R.layout.grid_lmr_labor, 
                                                               parent,false);
                holder = new ViewHolder();
                holder.txtId=(TextView)convertView.findViewById(R.id.txtId);
                holder.txtId.setPadding(20, 10,10 , 10);
                holder.txtName=(TextView)convertView.findViewById(R.id.txtId2);
                
                holder.txtStime=(TextView)convertView.findViewById(R.id.txtId3);
               
                holder.txtHtime=(TextView)convertView.findViewById(R.id.txtId4);
                
                holder.txtDtime=(TextView)convertView.findViewById(R.id.txtId5);
               
                
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
         holder.txtStime.setText(ammount_s.get(position).toString());
         holder.txtHtime.setText(ammount_h.get(position).toString());
         holder.txtDtime.setText(ammount_d.get(position).toString());
         
         return convertView;
  }
  static class ViewHolder
  {        
         TextView txtId;        
         TextView txtName;  
         TextView txtStime;
         TextView txtHtime;
         TextView txtDtime;
         
  }
	
	
  private void getData() {
		
	  
	  
	  
	  id=new ArrayList<String>();
	  name=new ArrayList<String>();
	  ammount_d=new ArrayList<String>();
	  ammount_h=new ArrayList<String>();
	  ammount_s=new ArrayList<String>();
	  
	  
		SQLiteDatabase d=this.db.getReadableDatabase();
		
		String[] params= {this.jobnumber};
		String sql="SELECT * FROM LMRLABOR WHERE jobnr=?";
		Cursor c=d.rawQuery(sql, params);
		
		
		if(c.moveToFirst()) {
		
			this.id.add(c.getString(0)+".");
			this.name.add(c.getString(2));
			this.ammount_s.add(c.getString(3));
			this.ammount_h.add(c.getString(4));
			this.ammount_d.add(c.getString(5));
		
			while (c.moveToNext()) {
				

				this.id.add(c.getString(0)+".");
				this.name.add(c.getString(2));
				this.ammount_s.add(c.getString(3));
				this.ammount_h.add(c.getString(4));
				this.ammount_d.add(c.getString(5));
				
			}
		
			
		d.close();
		}
	}	

}
