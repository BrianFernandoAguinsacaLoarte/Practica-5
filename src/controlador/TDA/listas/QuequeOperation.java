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
public class QuequeOperation<E> extends LinkedList<E>{
    private Integer top;

    public QuequeOperation(Integer top) {
        this.top = top;
    }
    
    public Boolean verify() {
        return getSize().intValue() <= top.intValue();
    }
    
    public void queque(E dato) throws LlenoException {
        if(verify()) {
            addLast(dato);
        } else {
            throw new LlenoException("Cola llena");
        }
    }
    
    public E dequeque() throws EmptyException {
        if(isEmpty()) {
            throw new EmptyException("Cola vacia");
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


