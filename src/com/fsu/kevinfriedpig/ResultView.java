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
	String		strTrace;
	int 		intEaster,
				intResult;
	Boolean 	blInGraph;
	
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
		
		  Log.w("ResultView", "onCreate entered");
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.result_view);
	      
	      tvBaconNum = (TextView)findViewById(R.id.tvBaconNum);
	      tvTrace = (TextView)findViewById(R.id.tvTrace);
	      intEaster = 0;
	      intResult = 0;
	      blInGraph = false;
	      
	      showResults();
	}
	
	
	/*
	 * Method implements easter egg - can you find it?
	 */
	public void kevinClick(View view){
		++intEaster;
		if( intEaster > 2 ){
			Toast.makeText(this, "It is Not Easter", Toast.LENGTH_SHORT).show();
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pc.fsu.edu/"));
	    	startActivity(browserIntent);
			super.finish();
		}
	}
	
	
	
	/*
	 * Main method of thread that pulls results
	 * manips the results
	 * and updates UI with results
	 */
	private void showResults(){
		resetView();
		baconCalc();
		traceCalc();
	}
	
	
	/*
	 * resets the form
	 */
	private void resetView(){
		tvBaconNum.setText("");
		tvTrace.setText("");
	}
	
	

/*	
 *  if cnt ==  -1 // Actor exists in graph, but does not connect to base actor
 *	if cnt == -2 // unexpected error
 *	if cnt == 0 // baseActor is the option (SearchView.baseActor)
 *	if cnt > 0 // baconnumber = cnt/2
*/
	private void baconCalc(){
		
		int cnt = SearchView.getBaconNum();
		
		if ( cnt >= 0 ){
			tvBaconNum.setText(cnt);
			blInGraph = true;
		}
		else{
			switch(cnt){
			case -1:
				tvBaconNum.setText("Actor Has No Bacon Number");
				break;
			case -2:
				Log.w("ResultView","baconCalc, return value of SearchView.getBaconNum() = -2 ");
				break;
			default:
				Log.w("ResultView","baconCalc, return value of SearchView.getBaconNum() < -2.  This should never happen!");
				break;
			}//switch
		}//else
	}//baconCalc()
	
	
	private void traceCalc(){
		String 	strWas = " who was in the movie ",
				strWith  = " with ";
		
		
		if ( !blInGraph ){
			tvTrace.setText("No path between "+ SearchView.getTrace()[0] +" and " + SearchView.getBaseActor() + " exists.");
			return;
		}
		tvTrace.setText( SearchView.getTrace()[0] + " was in the movie " + SearchView.getTrace()[1] + strWith );
		for (int i = 2; i < SearchView.getDistance(); ++i){
			tvTrace.setText( SearchView.getTrace()[i] + strWas + SearchView.getTrace()[++i] + strWith );	
		}
		
		
	}

}
