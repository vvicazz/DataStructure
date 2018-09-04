Red Black Tree

A red black tree is a binary search tree in which each node is coloured red or black.
root node is coloured black
Red node can have only black children
If a node in Red black tree does not have a left or right child then we add an external node.
External nodes are not coloured
Black depth of an external node is defined as the number of black ancestors it has.
In a red black tree every external node has the same black depth and it is also known as black height of the tree.


If a red black tree has a black height of h, then
minimum no of nodes for this black hight = 2pow(h) - 1
max no of nodes for this black height = 2pow(2h) - 1

log n base 4 < h < log n base 2

