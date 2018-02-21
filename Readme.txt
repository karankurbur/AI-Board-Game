Minmax Algorithm: Depth level for look ahead = 1
Time Complexity: O(b^m)
Space complexity: O(bm)
My program only works with depth level one. 
The node expanded will be 288 - (plays made). 
On average we expand 288^m nodes where m is the level.
With depth level 2 , we will expand around 82,944 nodes. 
With depth level 3, we will expand around 23 million nodes.




Minmax with alphabeta: Depth level for look ahead = 1
Time Complexity: O(b^m). Best case with best ordering is 
O(b^(m/2))/
Space complexity: O(b^m)
Best case is O(b^(m/2)). 
My program only works with depth level one. 
The node expanded will be 288 - (plays made). 

We assume we have best ordering.
On average we expand 288^(m/2) nodes where m is the level.
With depth level 2 , we will expand around 41,000 nodes. 
With depth level 3, we will expand around 11.5 million nodes.