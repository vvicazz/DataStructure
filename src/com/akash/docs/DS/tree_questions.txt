--binary tree
--BST
--AVL tree
--Segment tree
red black tree
interval tree
threaded binary tree
--Heap


--------------------------

Construct Tree from given Inorder and Preorder traversals
Construct a tree from Inorder and Level order traversals
Construct Tree from given Inorder and Postorder traversals
binary tree to doubly linked list
check if two trees are mirror
	->recursive
		->node.data, left==right, right==left
	->iterative
		->level order traversal with isLeft varaible
		->compare LNR and RNL
check if two trees are identical
convert binary tree into its mirror
BST from preorder
	->first element will be root, find first number in array greater than root,right subtree starts from there.Recursive
BST from postorder
--sorted linked list to BST
	->O(nlog(n)) mid of linked list will be root, use recursive for left and right
	->O(n) 
sorted array to balanced BST
	->mid of array will be root, use recursive for left and right
BST to min heap
	->inorder traversal of BST is min heap
normal BST to balanced BST
	->inorder of BST, sorted array to balanced BST
find median in BST
	->check no of nodes in BST,do inorder traversal and stop at n/2
check if binary tree is heap   -> level order traversal, check parent child relation (0 to n/2)
given level order traversal of binary tree, check if min heap
--merge k sorted arrays

------------------------------------------


--Construct all possible BSTs for keys 1 to N
--Find all possible binary trees with given Inorder Traversal
