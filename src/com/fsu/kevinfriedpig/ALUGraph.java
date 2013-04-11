package com.fsu.kevinfriedpig;

import java.util.LinkedList;
import java.util.Vector;
import java.util.ListIterator;

public class ALUGraph {
	
//	public:
//	    typedef N                                          Vertex;
//	    typedef typename fsu::List<Vertex>::ConstIterator  AdjIterator;
//
//	    void   SetVrtxSize  (N n);
//	    void   AddEdge      (Vertex from, Vertex to);
//	    size_t VrtxSize     () const;
//	    size_t EdgeSize     () const;           // Theta (|V| + |E|)
//	    size_t OutDegree    (Vertex v) const;
//	    size_t InDegree     (Vertex v) const;
//	    AdjIterator Begin   (Vertex x) const;
//	    AdjIterator End     (Vertex x) const;
//
//	    void Dump           (std::ostream& os); // Theta (|V| + |E|)
//
//	    ALUGraph ( );
//	    ALUGraph ( N n );
//
//	  protected:
//	    fsu::Vector < fsu::List < Vertex > > al_;
	
	Vector<LinkedList <Integer> >  al_;

	ALUGraph ()
	{
		al_ = new Vector<LinkedList<Integer>>();
		al_.setSize(0);
	}

//	ALUGraph ( int n ) 
//	{
//		al_.setSize(n);
//	}

	
	void SetVrtxSize (int n)
 	{
		al_.setSize(n);
	
 	}

	void AddEdge (int from, int to)
	{
		LinkedList<Integer> froml = al_.get(from);
		LinkedList<Integer> tol = al_.get(to);
		
		if (froml == null) froml = new LinkedList<Integer>();
		if (tol == null) tol = new LinkedList<Integer>();
		
		froml.add(to);
		tol.add(from);
		
	
	}
	
	int VrtxSize () 
	{
		return al_.size();
	}
	
	int EdgeSize ()  // Theta (|V| + |E|)
	// Theta (|V| + |E|)
	{
		int esize = 0;
	    for (int i = 0; i < al_.size(); ++i)
	      esize += al_.get(i).size();
	    return esize >> 1;
	}
	
	int OutDegree (int v) 
	{
		return al_.get(v).size();
	}

	int InDegree (int v) 
	{
		return OutDegree(v);	
	}

	ListIterator<Integer> Begin (int x) 
	{
		return al_.get(x).listIterator(0);
	}
	
	ListIterator<Integer> End (int x)
	{
		int y = al_.get(x).size();
		return al_.get(x).listIterator(y);
	}

//	  template < typename N >
//	  void ALUGraph<N>::Dump (std::ostream& os)
//	  // Theta (|V| + |E|)
//	  {
//	    AdjIterator j;
//	    for (size_t i = 0; i < VrtxSize(); ++i)
//	    {
//	      os << '[' << i << "]->";
//	      j = Begin((N)i);
//	      if (j != End((N)i))
//	      {
//		os << *j;
//		++j;
//	      }
//	      for ( ; j != End((N)i); ++j)
//	      {
//		os << ',' << *j;
//	      }
//	      os << '\n';
//	    }
//	  }

}