/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.listas.tablas;

import controlador.TDA.grafos.GrafoEtiquetadoNoDirigido;
import javax.swing.table.AbstractTableModel;
import modelo.Telefonia;

/**
 *
 * @author Usuario iTC
 */
public class ModeloTablaAdyacencias extends AbstractTableModel {

    private GrafoEtiquetadoNoDirigido<Telefonia> grafo; 
    
    @Override
    public int getRowCount() {
        return grafo.nro_vertices();
    }

    @Override
    public int getColumnCount() {
        return grafo.nro_vertices() + 1;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        if (columna == 0) {
            return grafo.obtenerEtiqueta(fila + 1).toString();

        } else {
            String valor = "-**-";
            try {
                Telefonia o = grafo.obtenerEtiqueta(fila + 1);
                Telefonia d = grafo.obtenerEtiqueta(columna);

                if (grafo.existeAristaE(o, d)) {
                    valor = grafo.peso_arista((fila + 1), columna).toString();
                }
                return valor;

                //LinkedList<Adyacencia> ady = grafo.adyacentesE(e);
            } catch (Exception e) {
                System.out.println("Error en modelo tabla" + e);
                return "";
            }
        }
    }

    @Override
    public String getColumnName(int columna) {
        
        if(columna == 0){
            return "Escuelas";
            
        }else{
            return grafo.obtenerEtiqueta(columna).toString();
        }
    }

    public GrafoEtiquetadoNoDirigido<Telefonia> getGrafo() {
        return grafo;
    }

    public void setGrafo(GrafoEtiquetadoNoDirigido<Telefonia> grafo) {
        this.grafo = grafo;
    }
    
    
    
}
