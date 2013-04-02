package com.fsu.kevinfriedpig;

import java.util.Vector;

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

		ALUGraph<N>      g_;
		HashTable<Integer,N,HashType> s2n_;
		Vector<Integer>        n2s_;
		Integer  lastPushed;
	    
	    };
	  
	    SymGraph ( )
		// default constructor
	    {
	    	s2n_(100);
	    	lastPushed(0);
	    }
	  
		template < typename S , typename N>
		    SymbolGraph<S,N>::SymbolGraph ( N n ): g_( n ), s2n_( n ), n2s_( n ), lastPushed(0)
		    // constructor that initializes the size of the graph and underlying conversions
		{
	    
		}
	  
	  
		    template < typename S , typename N>
			void   SymbolGraph<S,N>::SetVrtxSize  (N n)
			// Sets the vertex size of the graph
		    {
			g_.SetVrtxSize( n );
		    }
	  
		    template < typename S , typename N>
			void   SymbolGraph<S,N>::AddEdge      (Vertex from, Vertex to)
			// adds an edge between to vertecies of the graph
		    {
			g_.AddEdge(s2n_[from], s2n_[to]);
	    
		    }
	  
		    template < typename S , typename N>
			size_t SymbolGraph<S,N>::VrtxSize     () const
			// returns the number of vertecies in a graph
		    {
			return g_.VrtxSize();
		    }
	  
		    template < typename S , typename N>
			size_t SymbolGraph<S,N>::EdgeSize     () const
			// returns the total number of edges in a graph
		    {
			return g_.EdgeSize();
		    }
	  
		    template < typename S , typename N>
			size_t SymbolGraph<S,N>::OutDegree    (Vertex x) const
			// gives the degree out of a vertex in a directed graph (or the normal degree in undirected)
		    {
			return g_.OutDegree(s2n_[x]);
		    }
	  
		    template < typename S , typename N>
			size_t SymbolGraph<S,N>::InDegree     (Vertex x) const
			// gives the degree in of a vertex in a directed graph (or the normal degree is directed)
		    {
			return g_.InDegree(s2n_[x]);
		    }
	  
		    template < typename S , typename N>
			typename SymbolGraph<S,N>::AdjIterator SymbolGraph<S,N>::Begin   (Vertex x) const
			// returns the first item in a list of adjacencies
		    {
			return g_.Begin(s2n_[x]);
		    }
	  
		    template < typename S , typename N>
			typename SymbolGraph<S,N>::AdjIterator SymbolGraph<S,N>::End     (Vertex x) const
			// returns the last item in a list of adjacencies
		    {
			return g_.End(s2n_[x]);
		    }
	  
		    template < typename S , typename N>
			void   SymbolGraph<S,N>::Push         (const S& s)
			// add s to the vertex set
		    {
			if( s2n_.Includes(s) == s2n_.End() ) // value not in table and was addeded here
			{
			    s2n_.Put( s , lastPushed );
			    if( lastPushed >= g_.VrtxSize() )
				SetVrtxSize( lastPushed );
			    n2s_.PushBack(s);
			    ++lastPushed;
			    //std::cout << s << " was just pushed onto the graph at " << s2n_[s] << '\n';
			}
	    
			//else
			//already in the table
		    }
	  
	  
	} // namespace fsu

	#endif
	
}
