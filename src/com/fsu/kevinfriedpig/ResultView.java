package com.fsu.kevinfriedpig;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ResultView extends Activity {

	TextView	tvBaconNum,
				tvTrace;
	String[]	strTrace;
	int 		intEaster,
				intResult;
	
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
		
		  Log.w("ResultView", "onCreate entered");
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.result_view);
	      
	      tvBaconNum = (TextView)findViewById(R.id.tvBaconNum);
	      tvTrace = (TextView)findViewById(R.id.tvTrace);
	      intEaster = 0;
	      intResult = 0;
	      
	      showResults();
	}
	
	
	
	public void kevinClick(View view){
		++intEaster;
		if( intEaster > 2 ){
			Toast.makeText(this, "It is Not Easter", Toast.LENGTH_SHORT).show();
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pc.fsu.edu/"));
	    	startActivity(browserIntent);
			super.finish();
		}
	}
	
	private void showResults(){
		
		resetView();
		
		//intResult = SearchView.getBaconNum();
		
		
		//TODO manip results for display
		//TODO display results
		
	}
	
	private void resetView(){
		tvBaconNum.setText("");
		tvTrace.setText("");
	}
	

}
