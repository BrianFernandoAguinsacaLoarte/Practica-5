/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package controlador.TDA.grafos;

/**
 *
 * @author Usuario iTC
 */
public class EtiquetaException extends Exception {

    /**
     * Creates a new instance of <code>EtiquetaException</code> without detail message.
     */
    public EtiquetaException() {
        super("Grafo no Etiquetado");
    }

    /**
     * Constructs an instance of <code>EtiquetaException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public EtiquetaException(String msg) {
        super(msg);
    }
}
