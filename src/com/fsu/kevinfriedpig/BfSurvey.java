package com.fsu.kevinfriedpig;

import java.util.Deque;
import java.util.Vector;

import android.R.bool;

public class BfSurvey {

	Vector < Integer > distance; // distance from origin
    Vector < Integer > parent; // for BFS tree
    Vector < Character > color; // bookkeeping
    Vector < Integer > dtime;
    Boolean traceQue;
    
 
    ALUGraph g_;
    Vector < Boolean > visited_ ;
    Deque < Integer > conQ_ ;
  
  
  
  public BfSurvey ( const ALUGraph& g)
  // constructor requiring a graph to be passed... init all variables
  {
	  distance(g.VrtxSize(), g.VrtxSize());
	  parent(g.VrtxSize(), g.VrtxSize() + 1);
	  color(g.VrtxSize(), 'w');
	  dtime(g.VrtxSize(), g.VrtxSize());
	  traceQue = false;
	  g_(g);
	  visited_(g.VrtxSize(), false);
	  
  }
  
  
	  void search( Integer v )
	  {
	    Integer dt = 0;
	    conQ_.addLast(v);
	    visited_.set(v, true);
	    distance.set( v, 0 );
	    color.set(v,'g'); // grey for currently in queue
	    dtime.set(v,dt);
	    ++dt;
	    int cnt = 0; // counts the deepness in the iterator
	    
	    while (!conQ_.isEmpty())
	    {
	      int n;
	      int f = conQ_.peekFirst();
	      
	      typename ALUGraph<Vertex>::AdjIterator adjacents = g_.Begin(f);
	      while ( adjacents != g_.End(f) )
	      {
	        n = *adjacents;
	        if( visited_.get(v) == false )
	        {
	          conQ_.addLast(n); // PushFIFO
	          visited_.set(n,true);
	          distance.set( n, distance.get(f + 1) );
	          parent.set(n,f);
	          color.set(n,'g');
	          dtime.set(n, dt);
	          ++dt;
	        }
	        ++adjacents;
	      } // end while (adjacents != g_.End(f) )
	      if( adjacents == g_.End(f) && visited_.get(n) == false )
	      {
	        conQ_.addLast(n); // PushFIFO
	        visited_.set(n,true);
	        distance.set(n, distance.get(f + 1) );
	        parent.set(n,f);
	        color.set(n,'g');
	        dtime.set(n,dt);
	        ++dt;
	      } // end if
	      else
	      {
	        conQ_.removeFirst();
	        color.set(f,'b'); // black for removed queue
	        cnt = 0;
	      } // end else
	    }
	  }
	  
	  
	  
	  
	  void Search()
	  {
	    Reset();
	    for (Integer v = 0; v < g_.VrtxSize(); ++v )
	    {
	      if (color.get(v) == 'w') Search(v); // white for never been in queue
	    }
	  }
	  
	  
	  void Reset()
	  {
	    for (Integer v = 0; v < g_.VrtxSize(); ++v )
	    {
	      visited_.set(v,0); //
	      distance.set(v, g_.VrtxSize()); // 1 over the vrtx size
	      parent.set(v, g_.VrtxSize()) ; // 1 over the distance
	      color.set(v, 'w'); // no vertex has been in queue
	      dtime.set(v, g_.VrtxSize()); // 1 over the highest number
	    }
	    while(!conQ_.isEmpty())
	    {
	      conQ_.removeFirst();
	    }
	  }
	  
	  
	  Integer DistanceTo (Integer v)
	  {
	    return distance.get(v); // returns the distance to a vertex v
	  }
	  
	  
	  Vector < Integer > Distance() 
	  //returns the distance array
	  {
	    return distance;
	  }
	  
	  
	  Vector < Integer > Parent() 
	  //returns the parent array
	  {
	    return parent;
	  }
	  
	  
	  Vector < Character> Color() 
	  //returns the color array
	  {
	    return color;
	  }
	  
	  
	  Vector < Integer > DTime () 
	  //returns the discovery time array
	  {
	    return dtime;
	  }
  
  
	  Integer VrtxSize() 
	  //returns vrtx size of the underlying graph
	  //added functionality
	  {
	    return g_.VrtxSize();
	  }
  }
 