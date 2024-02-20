/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.listas;

import controlador.Excepcion.EmptyException;
import controlador.Excepcion.LlenoException;

/**
 *
 * @author Usuario iTC
 */
public class StackOperation<E> extends LinkedList<E>{
    private Integer top;

    public StackOperation(Integer top) {
        this.top = top;
    }
    
    public Boolean verify() {
        return getSize().intValue() <= top.intValue();
    }
    
    public void push(E dato) throws LlenoException {
        if(verify()) {
            addFirst(dato);
        } else {
            throw new LlenoException("Pila llena");
        }
    }
    
    public E pop() throws EmptyException {
        if(isEmpty()) {
            throw new EmptyException("Pila vacia");
        } else {
            return deleteFirst();
        }
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }
    
    
    
}





