package com.fsu.kevinfriedpig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchView extends Activity {

	EditText searchEditText;
	Button calculateButton;
	
	static int distance = -1;
	static String [] trace = new String [20]; // evens should be actors and odds movies
	static final String baseActor = "Kevin Bacon";
	static final int baseActorNum = LoadingView.string2number(baseActor);
	Context context = this;
	
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
		
		  Log.w("onCreate", "SearchView onCreate entered");
	      super.onCreate(savedInstanceState);
	      
	      setContentView(R.layout.search_view);
	 
	      getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
	      
	      searchEditText = (EditText)findViewById(R.id.editTextSearch);
	      calculateButton = (Button)findViewById(R.id.buttonCalculate);
	      
	      Log.w("onCreate", "End of onCreate in SearchView");
	      
	   }
	
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setTitle("Really Exit?")
	        .setMessage("Are you sure you want to exit?")
	        .setNegativeButton(android.R.string.no, null)
	        .setPositiveButton(android.R.string.yes, new OnClickListener() {

	            public void onClick(DialogInterface arg0, int arg1) {
	            	LoadingView.prepareToClose();
	            	SearchView.super.finish();
	                android.os.Process.killProcess(android.os.Process.myPid());
	            }
	        }).create().show();
	}
	
	public void Reset () { // used to reset distance to -1 and reset other variables
		searchEditText.setText("");
		distance = -1;
	}
	
	public void DistanceCalc (View view) { // function called on button click
		Log.w("DistanceCalc", "entered function");
		String actor = new String();
		Log.w("DistanceCalc", "new String processed");
		// This is where programs come to die
		//Log.w("DistanceCalc", "searchEditText = " + (searchEditText.getText().toString()));
		
		if( searchEditText.getText().length() == 0 ){
			Log.w("DistanceCalc", "toast should show == nothing or 1 space");
			Toast.makeText(context, "Please enter an actor or actress", Toast.LENGTH_SHORT).show();
			Reset();
		}
		else{
			Log.w("DistanceCalc", "something is typed - call MovieDistance");
			actor = searchEditText.getText().toString();
			Log.w("DistanceCalc", "actor set from string");
			MovieDistance(actor);
		}
		
	}
	
	public void MovieDistance (String actor) { //function to find the moviedistance from actor to the baseActor (Kevin Bacon)
		Log.w("MovieDistance", "entered function");
		
		if((LoadingView.getS2N().get(actor)) == null){ // person isn't in the database at all
			Log.w("Movie Distance", "actor not in database");
			distance = -4; // person is not in the database
			notInDatabase(actor);
			Reset();
			return;
		}
		
		int cnt = 0;
		int currNum = LoadingView.getS2N().get(actor);
		
		if( currNum == baseActorNum){ // the actor entered is the base actor BN of 0
			Log.w("MovieDistance", "Actor == BaseActor");
			cnt = 0;
			trace[0] = baseActor;
			openResults(cnt, trace);
		}
		else { // person is in the database
			if(LoadingView.getParentVector().get(currNum) >= 117877) { // person has no connection to baseActor
				distance = -2; // no path to Kevin Bacon but in graph
				trace[0] = LoadingView.getN2S().get(currNum);
				openResults(cnt, trace);
			}
			
			while(currNum != baseActorNum) // else person in graph and has connection
			{
				trace[cnt] = LoadingView.getN2S().get(currNum);
				Log.w("MovieDistance", "just stored trace[" + cnt + "] as " + trace[cnt]);
				++cnt;
			
				currNum = LoadingView.getParentVector().get(currNum);
					
			}
			if (currNum == baseActorNum)
				trace[cnt] = LoadingView.getN2S().get(currNum);
			else
				Log.w("ELSE", "trace didn't follow correctly");
			distance = cnt;
		
			openResults(cnt, trace);
			Log.w("MovieDistance", "just stored trace[" + cnt + "] as " + trace[cnt]);
			Log.w("string2number", "Testing our n2s vector where n = 2 n2s[2] = " + LoadingView.n2sVect.get(2));
		}
	}
	
	public void openResults (int totDistance, String[] trace) {
		Log.w("openResults", "newView, entered successfully");
		
			if(distance != totDistance){
				Log.w("openResults", "Contradiction of distance and cnt");
				distance = totDistance;
			}
			if(distance > 20){
				Log.w("openResults", "distance < 20, ERROR");
				distance = 20;
			}
			searchEditText.setText("");
			Intent searchIntent = new Intent(getBaseContext(), ResultView.class);
			Log.w("newView", "before start activity for result");
	        startActivityForResult(searchIntent, 0); 
	        Log.w("newView", "after start activity for result");
		
	}
	
	public void notInDatabase(String actor){
		Toast.makeText(context, actor + " is not in the database. Please try again.", Toast.LENGTH_SHORT).show();
		Reset();
	}
	
	static public int getBaconNum(){
		if(distance % 2 == 0)
			return distance/2;
		Log.w("getBaconNum()", "Distance was not an even number");
		return -2;
	}
	
	static public String[] getTrace(){
		return trace;
	}
	
	static public int getDistance(){
		return distance;
	}

	static public String getBaseActor(){
		return baseActor;
	}
	
	
}
