package com.cvirn.ferndaleforms2;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Splash extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ImageView image=(ImageView)findViewById(R.id.imageSplash);
        Animation animSplash=AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        image.startAnimation(animSplash);
        animSplash.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				startActivity(new Intent(Splash.this,MainActivity.class));
				Splash.this.finish();
			}
		});
    }
}