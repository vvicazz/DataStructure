Interval Search Tree

data structure to hold set of overlapping intervals (l,h)

=>operations :
1) insert an interval
2) search for an interval
3) delete an interval
4) find single or all intervals overlapping a given interval in the tree

Rules : 
no two intervals can have same start point

public class IntervalSt<K extends Comparable<K>, V> {
	IntervalSt(){}
	
	void put(K low, K high, V value);
	
	V get(K low, K high);
	
	void delete(K low, K high);
	
	Iterable<V> intersects(K low, K high);
}


=> create BST, where each node stores an interval (low,high)
=> low is used as BST key for comparison.
=> Store max endpoint in subtree rooted at node.

To insert node in IST :
1) insert into BST using low as key
2) update max in each node on search path

To search for any one interval that intersects given interval (low,high)
1) If interval in node intersects given interval, return it.
2) else if left subtree is null, go right
3) else if max endpoint in left subtree is less than low, go right
4) else go left.

