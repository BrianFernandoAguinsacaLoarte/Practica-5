/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.listas.tablas;

import controlador.TDA.listas.LinkedList;
import javax.swing.table.AbstractTableModel;
import modelo.Telefonia;

/**
 *
 * @author Usuario iTC
 */
public class ModeloTablaTelefonia extends AbstractTableModel {

    private LinkedList<Telefonia> telefonias = new LinkedList<>();

    public LinkedList<Telefonia> getTelefonias() {
        return telefonias;
    }

    public void setTelefonias(LinkedList<Telefonia> escuelas) {
        this.telefonias = escuelas;
    }
    
    
    @Override
    public int getRowCount() {
        return telefonias.getSize();
    }
    

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int fila , int columna){
         Telefonia e = null;
        try {
             e = telefonias.get(fila);
        } catch (Exception ex) {
        }
        
        switch (columna) {
            case 0:
                    return (fila  + 1);
            case 1: 
                    return e.getNombre();
            case 2:
                    return "["+e.getLongitud()+" -- "+ e.getLatitud()+"]";
            
                
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columna) {
        
        switch (columna) {
            case 0: 
                    return "Nro";
            case 1: 
                    return "Nombre";
            case 2: 
                    return "Ubicación [Longitud - Latitud]";
                    
            default:
                return null;
        }
        
    }
    
    
    
}
