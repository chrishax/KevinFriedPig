package com.fsu.kevinfriedpig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;

public class MovieMatch{
	
	String baseActor_ = new String();
	SymGraph sg_ = new SymGraph();
	boolean actorExistsInDataBase = false;
	boolean baseActorExistsInDataBase = false;
	boolean connectedToBaseActor = false;
	Context context;
	int 	vertexSize = 207200;
	
	MovieMatch ( String baseActor)
	//constructor
	{
	  //int length = baseActor.length();
	  baseActor_ = baseActor;	
	  //baseActor_[length] = '\0';
	  //strcpy (baseActor_,baseActor);
	}

	
	void load(){
		
		AssetManager amInput = context.getAssets();
        BufferedReader reader;
        InputStream is = null;
        sg_.SetVrtxSize(vertexSize);
        int 	startIndex = 0,
        		index = 0;
        Boolean gotMovie = false;
        		String line = "";
        	    String movie = "";
     
        try {
			is = amInput.open("movies.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        reader = new BufferedReader(new InputStreamReader(is));
        
        
       try {
		while( (line = reader.readLine()) != null ){
		    
		    //iterate though movie titles or actor names by using '/' to delimit a "token"
		    while(index < line.length() )
		    {
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
		      if ( !gotMovie )
		      {//if we havent gotten a movie yet get movie title
		        for( int j = startIndex; j < index; ++j )
		          movie += line.charAt(j);
		        
		        movie += '\0';
		        sg_.Push( movie );
		        ++index;
		        gotMovie = true;
		      }
		      else
		      {//else get actor name
		        
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
		        
		        actor += '\0';
		        sg_.Push( actor );
		        sg_.AddEdge( movie, actor );
		        ++index;
		      }   
		 
		    }
		}
	} catch (IOException e1) {
		
		e1.printStackTrace();
	}
       
       try {
    	   reader.close();
       } catch (IOException e) {
    	   e.printStackTrace();
       }
            
            
	}
    


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
		  actorExistsInDataBase = false;
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
		  baseActorExistsInDataBase = false;
		  return 0;
	  }
	  
	  bfs.Search(sg_.s2n_.get(baseActor_));
	  
	  if ( bfs.Distance().get(sg_.s2n_.get(actor)) >= sg_.VrtxSize() )
	  {
	    //std::cout << actorPtr << " has no shared path to "
	    //<< baseActorPtr << " \n";
		  connectedToBaseActor = false;
	    return -1;
	  }
	  
	  return bfs.Distance().get(sg_.s2n_.get(actor)) / 2;
	}

}
	
	
	
	
    
    
    
//  /*
//   * precount movie total to get vertexSize
//   * This saves exponential growth problems in 
//   * resizing vectors every time a bound is reached
//   */
//  
//  try {
//		is = amInput.open("movies.txt");
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//      
//  reader = new BufferedReader(new InputStreamReader(is));
//  try {
//  	while( (mvData = reader.readLine()) != null ){
//		
//			int index = 0;
//			while( index < mvData.length() )
//		    {
//		      for(; index < mvData.length(); ++index)
//		      {//interate through line from index to first '/'
//		        if ( mvData.charAt(index)== '/' || mvData.charAt(index) == '\0')
//		          ++vertexSize;
//		        else if ( mvData.charAt(index) == '\n' )
//		        {
//		          ++vertexSize;
//		          break;
//		        }
//		      }
//			}
//		}//while lines left
//	} catch (IOException e1) {
//		
//		e1.printStackTrace();
//	}
//  try {
//		reader.close();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
  