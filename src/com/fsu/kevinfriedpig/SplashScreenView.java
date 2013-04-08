package com.fsu.kevinfriedpig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
 
public class SplashScreenView extends Activity {
 
	Context context = this;
   private static String TAG = SplashScreenView.class.getName();
   private static long SLEEP_TIME = 5;    // Sleep for some time
 
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
 
      this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
      this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar
 
      setContentView(R.layout.splash_view);
 
      // Start timer and launch main activity
      IntentLauncher launcher = new IntentLauncher();
      launcher.start();
   }
 
   private class IntentLauncher extends Thread {
      @Override
      /**
       * Sleep for some time and than start new activity.
       */
      public void run() {
         try {
            // Sleeping
            Thread.sleep(SLEEP_TIME*1000);
         } catch (Exception e) {
            Log.e(TAG, e.getMessage());
         }
         Log.w("splashScreenView", "before intent call");
         // Start main activity
         Intent loadIntent = new Intent(context, LoadingView.class);
         startActivityForResult(loadIntent, 0);
         Log.w("splashScreenView", "before finish()");
         SplashScreenView.this.finish();
      }
   }
}