package com.akash.tree;

public class FetchTreeFromInorderPreorder {

}

/**
* http://www.geeksforgeeks.org/construct-tree-from-given-inorder-and-preorder-traversal/
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
*    
*/