package com.cvirn.ferndaleforms2.signature;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cvirn.ferndaleforms2.R;
import com.cvirn.ferndaleforms2.constants.LMReport;

public class SignatureCapture extends Activity implements OnGesturePerformedListener{
	
	private Button button1;
	private TextView textView1;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	Intent i;
	private String jobnumber;
	private GestureOverlayView gesture;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signature);
		
		
		this.button1 = (Button) findViewById(R.id.button1);
		this.textView1 = (TextView) findViewById(R.id.textView1);
		i=getIntent();
		jobnumber=i.getStringExtra(LMReport.LMR_JobNumber);
		this.textView1.setText("Customer Signature for Job:"+jobnumber);
		
		this.gesture=(GestureOverlayView)findViewById(R.id.gestureOverlayView1);
		//gesture.addOnGesturePerformedListener(list);
		this.button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View target) {
				Gesture gesture2=gesture.getGesture();
				Bitmap bmp=gesture2.toBitmap(600, 400,-1 , Color.rgb(255, 255, 255));
				String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
				Log.d("SIGNATURE",extStorageDirectory );
			    OutputStream outStream = null;
			    File file = new File(extStorageDirectory, jobnumber+"Signature.png");
			    Log.d("SIGNATURE",file.getPath());
			    try {
			     outStream = new FileOutputStream(file);
			     bmp.compress(Bitmap.CompressFormat.PNG, 50, outStream);
			     outStream.flush();
			     outStream.close();
			     Toast.makeText(SignatureCapture.this, "Signature saved", Toast.LENGTH_LONG)
					.show();
			     SignatureCapture.this.finish();
			    }
			    catch(Exception e)
			    {
			    	Log.d("SIGNATURE", e.getLocalizedMessage());
			    	Toast.makeText(SignatureCapture.this, "Signature save failed.", Toast.LENGTH_LONG)
					.show();
			    	
			    }
				
				
			}
		});
		
		
		
		
		
	}
//Not used 
public OnGesturePerformedListener list=new OnGesturePerformedListener() {
		
		@Override
		public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
			// TODO Auto-generated method stub
			Bitmap bmp=gesture.toBitmap(600, 400,-1 , Color.rgb(255, 255, 255));
			String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
			Log.d("SIGNATURE",extStorageDirectory );
		    OutputStream outStream = null;
		    File file = new File(extStorageDirectory, jobnumber+"Signature.jpeg");
		    
		    try {
		     outStream = new FileOutputStream(file);
		     bmp.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
		     outStream.flush();
		     outStream.close();
		     Toast.makeText(SignatureCapture.this, "Signature saved", Toast.LENGTH_LONG)
				.show();
		     SignatureCapture.this.finish();
		    }
		    catch(Exception e)
		    {
		    	Toast.makeText(SignatureCapture.this, "Signature save failed.", Toast.LENGTH_LONG)
				.show();
		    	SignatureCapture.this.finish();
		    }
		}
		
	};

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		// TODO Auto-generated method stub
		
	}
}
