package com.fsu.kevinfriedpig;

import java.util.ListIterator;
import java.util.Vector;
import java.util.Hashtable;

public class SymGraph {

	
//		typedef S      Vertex;
//		typedef N      Number;
//		typedef typename fsu::List<Number>::ConstIterator  AdjIterator;
//		typedef typename hashclass::KISS < S >  HashType;
//	    
//	
//		void   SetVrtxSize  (N n);
//		void   AddEdge      (Vertex from, Vertex to);
//		size_t VrtxSize     () const;
//		size_t EdgeSize     () const;
//		size_t OutDegree    (Vertex x) const;
//		size_t InDegree     (Vertex x) const;
//		AdjIterator Begin   (Vertex x) const;
//		AdjIterator End     (Vertex x) const;
//	    
//		void   Push         (const S& s); // add s to the vertex set
//	    
//		// access to underlying data
//		const ALUGraph<N>&      GetAbstractGraph() const { return &g_; } // reference to g_
//		const HashTable<S,N,HashType>& GetSymbolMap() const { return &s2n_; } // reference to s2n_
//		const Vector<S>&        GetVertexMap() const { return &n2s_; } // reference to n2s_
//	    
//		SymbolGraph ( );
//		SymbolGraph ( N n );
//	    
//		S GetSymbol ( N n ) { return n2s_[n]; }
//		N GetNumber ( S s ) { return s2n_[s]; }
//		//private: // changed for the purposes of this

		ALUGraph	    	  g_;
		Hashtable<String, Integer> s2n_;
		Vector<String>        n2s_;
		Integer  lastPushed;
	    
	  
	    SymGraph ( )
		// default constructor
	    {
	    	lastPushed = 0;
	    }

	    SymGraph ( int n )
	    // constructor that initializes the size of the graph and underlying conversions
		{
	    	g_.SetVrtxSize(n);
	    	//s2n_ -> n
	    	n2s_.setSize(n);
	    	lastPushed = 0;
		}
	  
	    void SetVrtxSize  (int n)
	    // Sets the vertex size of the graph
	    {
	    	g_.SetVrtxSize( n );
	    }
	  
	    void AddEdge (String from, String to)
	    // adds an edge between to vertecies of the graph
	    {
	    	g_.AddEdge(s2n_.get(from), s2n_.get(to));
	    }
	  
	    int VrtxSize () 
	    // returns the number of vertecies in a graph
	    {
	    	return g_.VrtxSize();
	    }
	  
	    int EdgeSize () 
	    // returns the total number of edges in a graph
	    {
			return g_.EdgeSize();
	    }
	  
	    int OutDegree (String x) 
	    // gives the degree out of a vertex in a directed graph (or the normal degree in undirected)
	    {
	    	return g_.OutDegree(s2n_.get(x));
	    }

	    int InDegree (String x) 
	    // gives the degree in of a vertex in a directed graph (or the normal degree is directed)
	    {
	    	return g_.InDegree(s2n_.get(x));
	    }

	    ListIterator<Integer> Begin	(String x) 
	    // returns the first item in a list of adjacencies
	    {
			return g_.Begin(s2n_.get(x));
	    }
	    
	    ListIterator<Integer> End (String x)
	    {
	    	return g_.End(s2n_.get(x));
	    }
	  
//	    template < typename S , typename N>
//	    typename SymbolGraph<S,N>::AdjIterator SymbolGraph<S,N>::End     (Vertex x) const
//	    // returns the last item in a list of adjacencies
//	    {
//			return g_.End(s2n_[x]);
//	    }
	  
	    void Push (String s)
	    // add s to the vertex set
	    {
	    	if(!s2n_.contains(s) ) // value not in table and was added here
			{
	    		s2n_.put( s , lastPushed );
			    if( lastPushed >= g_.VrtxSize() )
			    	SetVrtxSize( lastPushed );
			    n2s_.add(s);
			    ++lastPushed;
			}
	    
			//else
			//already in the table
	    }
	    
}
	  
	  



