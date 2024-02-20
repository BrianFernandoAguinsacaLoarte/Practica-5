/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.arboles;

/**
 *
 * @author Usuario iTC
 */
public class NodeTree<E> {
    
    private NodeTree father;
    private NodeTree left;
    private NodeTree right;
    private E info;
    
    //Constructor

    public NodeTree(E dato) {
        this.info = dato;
        father = null;
        left = null;
        right = null;
    }
    
    
    
    
    
    //Getter and Setter

    public NodeTree getFather() {
        return father;
    }

    public void setFather(NodeTree father) {
        this.father = father;
    }

    public NodeTree getLeft() {
        return left;
    }

    public void setLeft(NodeTree left) {
        this.left = left;
    }

    public NodeTree getRight() {
        return right;
    }

    public void setRight(NodeTree right) {
        this.right = right;
    }

    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }
    
    //ToString

    @Override
    public String toString() {
        return info.toString();
    }
    
    
    
    
}
