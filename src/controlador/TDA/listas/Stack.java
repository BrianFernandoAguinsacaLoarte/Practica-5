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
public class Stack<E> {
    private StackOperation<E> stackOperation;
    public Stack(Integer cant) {
        this.stackOperation = new StackOperation<>(cant);
    }
    public void push(E dato) throws LlenoException {
        stackOperation.push(dato);
    }
    
    public Integer getSize() {
        return this.stackOperation.getSize();
    }
    
    public void clear() {
        this.stackOperation.clear();
    }
    
    public Integer getTop() {
        return this.stackOperation.getTop();
    }
    
    public void print() {
        System.out.println("PILA");
        System.out.println(stackOperation.print());
        System.out.println("******");
    }
    //TODO.. falta push
    public E pop() throws EmptyException {
        return stackOperation.pop();
    }
}


