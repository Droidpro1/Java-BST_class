// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import java.util.NoSuchElementException;

class UnintendedUseException extends RuntimeException {
    public UnintendedUseException(String message){
        super(message);
    }
}

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 *
 * printTree, nodeCount, isFull, compareStructure
 * equals, copy, mirror, isMirror, rotateRight and Left,
 * printLevels, and the UnintendedUseException
 * all added by Ritvik Divanji
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{

    private BinaryNode<AnyType> root;


    public BinarySearchTree( ) { root = null; }

    private BinarySearchTree(BinaryNode<AnyType> b) { root = b; }

    public void insert( AnyType x ) { root = insert( x, root ); }

    public void remove( AnyType x ) { root = remove( x, root ); }

    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException("Empty");
        return findMin( root ).element;
    }

    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException("Empty");
        return findMax( root ).element;
    }

    public boolean contains( AnyType x ) { return contains( x, root ); }

    public void makeEmpty( ) { root = null; }

    public boolean isEmpty( ) { return root == null; }

    public void printTree( )
    {
        System.out.println("---------Printing Tree---------");
        System.out.println(printTree( root , 0));
        System.out.println("---------Finished Printing---------");
    }

    private String printTree( BinaryNode<AnyType> t , int depth)
    {
        if( t == null) { return ""; }
        if((t.left==null && t.right==null))
            return ""+t.element;
        String indentation = (" ").repeat((depth+1)*2);
        if(t.left == null){
            return "" + t.element +"\n"+"|"+
                    indentation + "-right: " + printTree(t.right,depth+1);
        }
        if(t.right == null){
            return "" + t.element +"\n"+"|"+
                    indentation + "-left: " + printTree(t.left,depth+1);
        }
        return "" + t.element +"\n"+"|"+
                indentation + "-left: " + printTree(t.left,depth+1) + "\n"+"|"+
                indentation + "-right: " + printTree(t.right,depth+1);
    }

    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return new BinaryNode<>( x, null, null );

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;  // Duplicate; do nothing
        return t;
    }

    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            throw new NoSuchElementException("That element does not exist");   // Item not found; do nothing

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;
        return t;
    }

    private boolean contains( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return false;

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true;    // Match
    }

    private int height( BinaryNode<AnyType> t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );
    }

    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType extends Comparable<? super AnyType>>
    {
        // Constructors
        BinaryNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt ) {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child

        private void setLeft(BinaryNode<AnyType> node){this.left = node;}
        private void setRight(BinaryNode<AnyType> node){this.right = node;}

    }

    public int nodeCount(){ //helper method
        return nodeCount(this.root);
    }

    private int nodeCount(BinaryNode<AnyType> node){
        if(node==null)
            return 0;
        return 1+nodeCount(node.right)+nodeCount(node.left);
    }

    public void isFull(){
        //helper method
        if(isFull(this.root))
            System.out.println("Tree 1 is full");
        else
            System.out.println("Tree 1 is NOT full");
    }

    private boolean isFull(BinaryNode<AnyType> node){
        if((node.left == null && node.right!=null) || (node.right == null && node.left!=null))
            return false;
        if(node.left == null)
            return true;
        return isFull(node.left) && isFull(node.right);
    }

    public void compareStructure(BinarySearchTree<AnyType> that){
        //helper method
        if(compareStructure(this.root,that.root))
            System.out.println("The trees' structures are the same");
        else
            System.out.println("The trees' structures are NOT the same");
    }

    private boolean compareStructure(BinaryNode<AnyType> a, BinaryNode<AnyType> b){
        if((a == null) == (b==null)){
            if(a!=null)
                return compareStructure(a.left,b.left) && compareStructure(a.right,b.right);
            return true;
        }
        return false;
    }

    public void equals(BinarySearchTree<AnyType> that){
        //helper method
        if(equals(this.root,that.root))
            System.out.println("The trees are identical");
        else
            System.out.println("The trees are NOT identical");
    }

    private boolean equals(BinaryNode<AnyType> a, BinaryNode<AnyType> b){
        if((a==null) == (b==null)) {
            if(a==null)
                return true;
            if((a.element).equals(b.element))
                return equals(a.left,b.left) && equals(a.right,b.right);
        }
        return false;
    }

    public BinarySearchTree<AnyType> copy(){
        return new BinarySearchTree<>(copy(this.root));
    }

    private BinaryNode<AnyType> copy(BinaryNode<AnyType> node){
        if(node == null)
            return null;
        return new BinaryNode<>(node.element, copy(node.left), copy(node.right));
    }

    public BinarySearchTree<AnyType> mirror(){
        BinarySearchTree<AnyType> newTree = this.copy();
        mirror(newTree.root);
        return newTree;
    }

    private void mirror(BinaryNode<AnyType> node){
        if(node==null)
            return;
        BinaryNode<AnyType> temp = node.left;
        node.setLeft(node.right);
        node.setRight(temp);
        if((node.left==null)&&(node.right==null)) {
            return;
        }
        mirror(node.left);
        mirror(node.right);
    }

    public void isMirror(BinarySearchTree<AnyType> that){
        //helper method
        if(isMirror(this.root,that.root))
            System.out.println("The trees are mirrors of each other");
        else
            System.out.println("The trees are NOT mirrors of each other");
    }

    private boolean isMirror(BinaryNode<AnyType> a, BinaryNode<AnyType> b){
        if((a==null) == (b==null)) {
            if(a==null)
                return true;
            if((a.element).equals(b.element))
                return (isMirror(a.left,b.right) && isMirror(a.right,b.left));
        }
        return false;
    }

    public void rotateRight(AnyType T){
        if(root.element.equals(T) || root.left.element.equals(T)){
            BinarySearchTree<AnyType> newTree = new BinarySearchTree<>();
            //if we want to rotate the root or its left child to the right
            //the result is the same
            BinaryNode<AnyType> tempRoot = new BinaryNode<>(root.element);
            BinaryNode<AnyType> tempRootRight = root.right;
            tempRoot.setRight(tempRootRight);
            root = root.left;
            BinaryNode<AnyType> temp = root;
            while(temp.right != null)
                temp = temp.right;
            if(tempRoot.element.compareTo(temp.element) <0)
                temp.setLeft(tempRoot);
            else
                temp.setRight(tempRoot);
        }
        else
            throw new UnintendedUseException("The method doesn't work with that parameter");
    }

    public void rotateLeft(AnyType T){
        if(root.element.equals(T) || root.right.element.equals(T)){
            BinarySearchTree<AnyType> newTree = new BinarySearchTree<>();
            //if we want to rotate the root or its left child to the right
            //the result is the same
            BinaryNode<AnyType> tempRoot = new BinaryNode<>(root.element);
            BinaryNode<AnyType> tempRootLeft = root.left;
            tempRoot.setRight(tempRootLeft);
            root = root.right;
            BinaryNode<AnyType> temp = root;
            while(temp.left != null)
                temp = temp.left;
            if(tempRoot.element.compareTo(temp.element) <0)
                temp.setLeft(tempRoot);
            else
                temp.setRight(tempRoot);
        }
        else
            throw new UnintendedUseException("The method doesn't work with that parameter");
    }

    public void printLevels() {
        int h = this.height(this.root);
        System.out.println(root.element);
        for (int i=1; i<=h; i++) {
            printLevels(root, i);
            System.out.println();
        }
    }

    private void printLevels(BinaryNode<AnyType> node, int level) {
        if (node == null)
            return;
        if (level == 0)
            System.out.print(node.element + " ");
        else if (level > 0) {
            printLevels(node.left, level-1);
            printLevels(node.right, level-1);
        }
    }

    // Test program
    public static void main( String [ ] args )
    {
        BinarySearchTree<Integer> tree1 = new BinarySearchTree<>();
        BinarySearchTree<Integer> tree2 = new BinarySearchTree<>();

        for(int i = 10; i>0; i--){
            tree1.insert(i);
            tree2.insert(i);
        }
        tree2.insert(14);
        tree2.insert(12);
        tree1.insert(14);
        tree1.insert(12);
        System.out.println("Tree 1 and 2 both have " + tree1.nodeCount() + " nodes");

        tree1.equals(tree2);
        tree1.compareStructure(tree2);
        tree1.isMirror(tree2);

        System.out.println("Inserting 16 and 24 into Tree1...");
        tree1.insert(16);
        tree1.insert(24);

        System.out.println("Now their structures should differ");
        tree1.compareStructure(tree2);
        tree1.isFull();
        System.out.println("Tree1:");
        tree1.printTree();

        System.out.println("Copying tree1 and printing it:");
        tree2 = tree1.copy();
        tree2.printTree();

        System.out.println("Mirroring tree1 and printing it:");
        tree2 = tree2.mirror();
        tree2.printTree();
        System.out.println("Tree1 and Tree2 should now be mirrors:");
        tree1.isMirror(tree2);
        tree2.printTree();


        System.out.println("Demonstrating the rotate functions:");
        tree1.rotateRight(10);
        tree1.printTree();
        tree1.rotateLeft(10);
        tree1.printTree();
        tree1.rotateRight(14);
        tree1.printTree();

        System.out.println("Printing the tree using printLevels():");
        tree1.printLevels();
    }
}