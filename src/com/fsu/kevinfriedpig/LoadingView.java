/*
 * 
 * 
 *  
 * 
 */

package com.fsu.kevinfriedpig;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;  
import android.os.Bundle;  
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;  
import android.widget.TextView;  
  

public class LoadingView extends Activity {

	TextView 	tv_progress;
	ProgressBar	pb_progress;
	MovieLoad 	ml;
	AssetManager amInput;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.loading_view);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		tv_progress=(TextView)findViewById(R.id.tv_progress);
		pb_progress=(ProgressBar)findViewById(R.id.pb_progressBar);
		amInput = this.getAssets();
		
		ml = new MovieLoad("Kevin Bacon");
	
		ml.execute();
		
				
	}

	
	
	

	private class MovieLoad extends AsyncTask< Void, Integer, Void >{
	
		String baseActor_ = "Kevin Bacon";
		SymGraph sg_;
//		boolean actorExistsInDataBase = false;
//		boolean baseActorExistsInDataBase = false;
//		boolean connectedToBaseActor = false;
		Context context;
		int 	vertexSize = 207200,
				counter = 0;
		
		
		/*
		 * constructor
		 */
		
		MovieLoad(String base){
			 sg_ = new SymGraph();
			baseActor_ = new String(base);
		}
		
		
		
		/*
		 * Loads movies.txt
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Void... params) {
			
			sg_.SetVrtxSize(vertexSize);
			
	        String line = "";
	        
			
			
	        BufferedReader reader;
	        
	        InputStream is = null;
			
	        
	        try {
				is = amInput.open("movies.txt");
			} catch (IOException e2) {
				//Log.w("MovieLoad", "doInBackground, open movies.txt failed");
				e2.printStackTrace();
			}
			
	        
	        reader = new BufferedReader(new InputStreamReader(is));
	        
	        
	       try {
			while( (line = reader.readLine()) != null ){
				//Log.w("MovieLoad", "doInBackground, inside while(readLine ! null)");
				int 	startIndex = 0,
		        		index = 0;
		        Boolean gotMovie = false;
		        String movie = "";
		        int current = ++counter / vertexSize;
		        		
		       // publish updates at 5% intervals
		       if ( ((current % 2) == 0) && current <= 100)
		    	   publishProgress(current);
		        		
		       //Log.w("MovieLoad", "doInBackground, before while(index<length)");
			    //iterate though movie titles or actor names by using '/' to delimit a "token"
			    while(index < line.length() )
			    {
			    	//Log.w("MovieLoad", "doInBackground, inside while(index<length)");
			      String actor = "";
			      startIndex = index;
			      for(; index < line.length(); ++index)
			      {//interate through line from index to first '/'
			        if ( line.charAt(index) == '/' )
			        {
			          break;
			        }
			        else if ( line.charAt(index) == '\0' )
			        {
			          break;
			        }
			      }
			      
////Log.w("doInBackground","before if !gotmovie");
			      if ( !gotMovie )
			      {//if we havent gotten a movie yet get movie title
			        for( int j = startIndex; j < index; ++j )
			          movie += line.charAt(j);
			        Log.w("doInBackground","movie = " + movie);			        
			        //movie += '\0';
			        //Log.w("doInBackground","before sg_.push");
			        sg_.Push( movie );
			        //Log.w("doInBackground","after sg_.push");
			        ++index;
			        gotMovie = true;
			      }
			      else
			      {//else get actor name
			    	  //Log.w("doInBackground","first line in reading actor");
			        String 	lastName = "";
			        Boolean gotLast = false,
			        		dumpEnd = false;
			        
			        for( int j = startIndex; j < index; ++j )
			        {
			          if( line.charAt(j) != ',' && !gotLast )
			            lastName += line.charAt(j);
			          else if ( line.charAt(j) == ',' ) // ditch comma and the following space
			          {
			            gotLast = true;
			            ++j;
			          }
			          else if ( gotLast && line.charAt(j) == ' ' &&  line.charAt(j + 1) == '(' ) // ditch the spaces after FN
			            gotLast = true;
			          else if ( gotLast && line.charAt(j) != '(' && !dumpEnd )
			        actor += line.charAt(j);
			          else if ( line.charAt(j) == '(' )
			            dumpEnd = true;
			          else if ( dumpEnd )
			            break;
			        }
			      
			        actor += ' ';
			      
			        actor += lastName;
			      
			        //actor += '\0';
			        sg_.Push( actor );
			      
			        sg_.AddEdge( movie, actor );
			      
			        ++index;
			      }   
			 
			    }
			}
		} catch (IOException e1) {
			//Log.w("MovieLoad", "doInBackground, IOException from readline");
			e1.printStackTrace();
		}
	       
	       try {
	    	   reader.close();
	       } catch (IOException e) {
	    	   e.printStackTrace();
	    	   //Log.w("MovieLoad", "doInBackground, IOException from file.close()");
	       }
	            
	          return null;  
		}
	    
		
		
		@Override  
		protected void onProgressUpdate(Integer... values)  
		{  
			//Log.w("MovieLoad", "onProgressUpdate, before if values");
			//Update the progress at the UI if progress value is smaller than 100  
			if(values[0] <= 100)  
			{
				//Log.w("MovieLoad", "onProgressUpdate, inside if values");
				tv_progress.setText("Progress: " + Integer.toString(values[0]) + "%");  
				pb_progress.setProgress(values[0]);  
				//Log.w("MovieLoad", "onProgressUpdate, after if values");
			}  
			//Log.w("MovieLoad", "onProgressUpdate, last line");
			
		}  
	  
	        //After executing the code in the thread  
	        @Override  
	        protected void onPostExecute(Void result)  
	        {  
	        	Intent searchIntent = new Intent(context, SearchView.class);
	            startActivityForResult(searchIntent, 0);  
	        }  
	      
		
		
		
		
		
		
		
	
		/*
		 * calculates baseactor to actor given distance 
		 */
		int MovieDistance(String actor)
		// returns the number of movies required to get from actor to baseActor_
		{
		  BfSurvey bfs = new BfSurvey( sg_.g_ );
		    
		  //String actorPtr;
		  //String baseActorPtr;
		  //int i = 0;
	//	  while(!actor.isEmpty())
	//	  {
	//	    actorPtr += actor[i];
	//	    ++i;
	//	  }
	//	  actorPtr += '\0';
		  
		  
		  if (sg_.s2n_.contains(actor)) // if actor is in the graph
		  {
		    //std::cout << actor << " does not exist in the database.  Try again.\n";
			  //actorExistsInDataBase = false;
			  return 0;
		  }
		  
		  //int j = 0;
	//	  while(baseActor_[j] != '\0' && baseActor_[j] != '\n')
	//	  {
	//	    baseActorPtr += baseActor_[j];
	//	    ++j;
	//	  }
	//	  baseActorPtr += '\0';
		  
		  if (sg_.s2n_.contains(baseActor_))
		  {
		    //std::cout << baseActor_ << " does not exist in the database.  Try again.\n";
			//  baseActorExistsInDataBase = false;
			  return 0;
		  }
		  
		  bfs.Search(sg_.s2n_.get(baseActor_));
		  
		  if ( bfs.Distance().get(sg_.s2n_.get(actor)) >= sg_.VrtxSize() )
		  {
		    //std::cout << actorPtr << " has no shared path to "
		    //<< baseActorPtr << " \n";
			//  connectedToBaseActor = false;
		    return -1;
		  }
		  
		  return bfs.Distance().get(sg_.s2n_.get(actor)) / 2;
		}
	}



	

	public int distance( String actor ){
		return ml.MovieDistance( actor );
	}
		
	
	
	
	
}