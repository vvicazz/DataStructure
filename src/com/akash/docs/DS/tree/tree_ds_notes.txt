=>Binary Tree: Tree that has at most 2 children

=>Binary tree traversal :
PreOrder	- NLR
InOrder		- LNR
PostOrder	- LRN

=================================================================

=>Binary Search Tree : It is a binary tree with a property

val[node.right] >= val[node] > val[node.left]

class Node<T> {
	T value;
	Node<T> left;
	Node<T> right;
}

Operations in BST:
1) search = height of the tree
2) insert = height of the tree
3) delete = height of the tree

Avg(height of the tree) = log(n)
worst(height of the tree) = n

=================================================================

=>AVL tree

worst(height of the tree) = log(n)
This can be possible by rotation of nodes while insert and delete
BF(node) = height of left - height of right
BF can have values b/w  0,1,-1


search = similar to BST
insert = rotation is done from the node that is nearest ancestor (lets say A) of newly added node.
	RR : node is added to right child of right child of A
	LR : node is added to left child of right child of A
	LL : node is added to left child of left child of A
	RL : node is added to right child of left child of A
	

RR is mirror image of LL
LR is mirror image of RL

delete = Similarly there are four cases of deletion in AVL and 2 cases are mirror image of other two.

class Node<T> {
	T value;
	Node<T> left;
	Node<T> right;
	int height;
	Node<T> parent;
}

=================================================================

=> 2-4 tree and Multi way search tree

Both of them are ways to store dictionaries.
easy to insert,search and delete.

=> multi-way search tree properties :
1) At least 2 children
2) At most d-1 items in a node and d children
3) node has pointer to d children

=> 2-4 tree or 2-3-4 tree properties :
1) At least 2 children
2) At most 4 children
3) node has pointer to 2/3/4 children
4) All the leaf nodes are at same level

=> height of 2-4 tree

log(n) base 4 < Height < log(n) base 2


=> InOrder traversal of a 2-4 tree :
leftmost child
1st left node
child b/w 1st and 2nd node
...
in the end rightmost child



=> Complexity for insert, search and delete = height of the tree

https://www.cs.purdue.edu/homes/ayg/CS251/slides/chap13b.pdf
http://smile.ee.ncku.edu.tw/Links/MTable/Course/DataStructure/2-3,2-3-4&red-blackTree_952.pdf

class TwoFourNode<T> {
	T keys[] = new Object[3];
	TwoFourNode<T> children[] = new TwoFourNode[4];
}

=================================================================

=>insert
	insertion of a new node is always done at leaf node of the tree
	(1)no problem if node has empty space
	(2)nodes get split if there is an insufficient space
	one key is promoted to parent and inserted in there
	If parent does not have sufficient space, then it is split
	In this manner split can cascade
	Eventually we may have to create new root, this increase height of the tree.

=>delete
	(1)no problem if key to be deleted is in the leaf node with at least 2 keys.
	(2)If key to be deleted is an internal node then we swap it with
	 	its predecessor(which is in a leaf) and then delete it.(as in BST)
	predecessor of a node : go left from a node and keep going right
	predecessor of a node will always be in a leaf node.
	(4)If after deleting a key a node becomes empty then we borrow a key from its sibling.
	(5)If sibling has only one key then we merge with it.
		The key in the parent node separating these two siblings moves down into the merged node.
		one of the key from parent node has to come down
	(6)If sibling has only one key and if while merging, parent node has only one key.
		
=================================================================

AVL tree makes the binary tree balanced.	<br><br>
 * 
 * Height of Tree   = height of root node = length of longest path from root to leaf	<br>
 * 
 * Height of a Node = length of longest path from that node to leaf	
 * 					= 1 + MAX {
 * 								height of left child ,
 * 								height of right child
 * 								}
 * 
 * 	<br>
 * 
 *  AVL requires heights of left and right child of every node in the tree,
 *   must have a difference of {-1,0,+1} .	<br><br>
 * 
 * Here we maintain height of each node with node itself (as nodeHeight). <br>
 * While adding or deleting a node from tree, we update the height of each node coming in that path.
 * 

--------------------------------------------------
 
 http://www.geeksforgeeks.org/construct-tree-from-given-inorder-and-preorder-traversal/
* 
*Inorder  : DBEAFC
*Preorder : ABDECF
*
*(1) Get first element in preorder (say prevalue at preindex) , it will be root of the tree.
*
* A BDECF
* -
* 
*(2) Find the index of prevalue in inorder (invalue at inindex)
*
* BDE A FC
*     -
*     
*(3) Now left part of invalue in inorder will be its left subtree.
*    To get this left subtree, find the value at 
*    { tempval = VAL[inindex-1] in inorder }
*    Now find this 
*    temp2index = tempval in preorder after preindex.
*    So now we will preorder and inorder of this left subtree.
*    
* Inorder  : BDE
* Preorder : BDE
*
*(4) Repeat step 1 for this left subtree.
*
*(5) elements in preorder after temp2index+1
*    will be right sub tree
*    
* Inorder  : FC
* Preorder : CF
* 
* (6) Repeat step 1 for this right subtree.
