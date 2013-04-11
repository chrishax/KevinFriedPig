package com.fsu.kevinfriedpig;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchView extends Activity {

	EditText searchEditText;
	Button calculateButton;
	
	int distance = -1;
	String [] trace = new String [20]; // evens should be actors and odds movies
	String baseActor = "Kevin Bacon";
	int baseActorNum = LoadingView.string2number(baseActor);
	Context context = this;
	
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
		
		  Log.w("onCreate", "SearchView onCreate entered");
	      super.onCreate(savedInstanceState);
	 
	      this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
	      this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar
	 
	      searchEditText = (EditText)findViewById(R.id.editTextSearch);
	      calculateButton = (Button)findViewById(R.id.buttonCalculate);
	      
	      setContentView(R.layout.search_view);
	      
	      Log.w("onCreate", "End of onCreate in SearchView");
	      
	   }
	
	public void Reset () { // used to reset distance to -1 and reset other variables
		distance = -1;
	}
	
	public void DistanceCalc (View view) { // function called on button click
		Log.w("DistanceCalc", "entered function");
		String actor = new String();
		Log.w("DistanceCalc", "new String processed");
		// This is where programs come to die
		Log.w("DistanceCalc", "searchEditText = " + (searchEditText.getText().toString()));
		
		if( searchEditText.getText().toString().trim().length() == 0 ){
			Log.w("DistanceCalc", "toast should show == nothing or 1 space");
			Toast.makeText(context, "Please enter an actor or actress", Toast.LENGTH_SHORT).show();
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
		
		int currNum = 0; // = s2n.get(actor);
		int cnt = 0;
		
		if( actor == "Kevin Bacon"){
			Log.w("MovieDistance", "Actor == KevinBacon");
			cnt = 0;
			trace[0] = "Kevin Bacon";
			openResults(cnt, trace);
		}
		else if(!LoadingView.getS2N().contains(actor)){
			cnt = -1; // person is not in the database
			notInDatabase();
		}
		else {
			while(currNum != baseActorNum)
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
		
			Log.w("MovieDistance", "just stored trace[" + cnt + "] as " + trace[cnt]);
			Log.w("string2number", "Testing our n2s vector where n = 2 n2s[2] = " + LoadingView.n2sVect.get(2));
		}
	}
	
	public void openResults (int totDistance, String[] trace) {
		
	}

	public static int getBaconNum() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void notInDatabase(){
		
	}
	
}
