/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.grafo.escuela;

import controlador.Excepcion.EmptyException;
import controlador.TDA.grafos.Grafo;
import controlador.TDA.grafos.GrafoEtiquetadoDirigido;
import controlador.TDA.listas.LinkedList;
import controlador.grafo.telefono.TelefoniaDAO;
import controlador.util.Utilidades;
import java.io.FileWriter;
import modelo.Telefonia;
import org.edisoncor.gui.comboBox.ComboBoxRect;
import org.edisoncor.gui.comboBox.ComboBoxRound;

/**
 *
 * @author Usuario iTC
 */
public class UtilesEscuelaVista {
    

     public static void cargaComboTelefonia(ComboBoxRect combo) throws EmptyException{
        combo.removeAllItems();
        LinkedList<Telefonia> lista = new TelefoniaDAO().listAll();
        
        for(int i =0; i < lista.getSize(); i++){
            combo.addItem(lista.get(i));
        }
    }
    
    public static Telefonia getComboTelefonia(ComboBoxRect combo){
        return (Telefonia) combo.getSelectedItem();
    }
    
   public static void cargaComboAlgortimo(ComboBoxRect combo) throws EmptyException{
        combo.removeAllItems();
        
        combo.addItem("Floyd");
        combo.addItem("Dijkstra");
    }
    
    public static String getComboAlgoritmo(ComboBoxRect combo){
        return (String) combo.getSelectedItem();
    }
    
    public static void cargaComboRecorrido(ComboBoxRect combo) throws EmptyException{
        combo.removeAllItems();
        
        combo.addItem("Anchura");
        combo.addItem("Profundidad");
    }
    
    public static String getComboRecorrido(ComboBoxRect combo){
        return (String) combo.getSelectedItem();
    }
    
    
    
    
    
    
    public static void cargaComboTelefonia(ComboBoxRound combo) throws EmptyException{
        combo.removeAllItems();
        LinkedList<Telefonia> lista = new TelefoniaDAO().listAll();
        
        for(int i =0; i < lista.getSize(); i++){
            combo.addItem(lista.get(i));
        }
    }
    
    public static Telefonia getComboTelefonia(ComboBoxRound combo){
        return (Telefonia) combo.getSelectedItem();
    }
    
   

    public static Double distanciaTelefonias (Telefonia o, Telefonia d){
        return Utilidades.distanciaDosPuntos(o.getLatitud(), o.getLongitud(), d.getLatitud(), d.getLongitud());
    }



    public static void crearMapaTelefonia(Grafo grafo){
        if(grafo instanceof GrafoEtiquetadoDirigido || grafo instanceof GrafoEtiquetadoDirigido){
            GrafoEtiquetadoDirigido ge = (GrafoEtiquetadoDirigido) grafo;
            
            String mapa = "var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',\n" +
                "                    osmAttrib = '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors',\n" +
                "                    osm = L.tileLayer(osmUrl, {maxZoom: 15, attribution: osmAttrib});\n" +
                "\n" +
                "            var map = L.map('map').setView([-4.036, -79.201], 15);\n" +
                "\n" +
                "            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {\n" +
                "                attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\n" +
                "            }).addTo(map);"+"\n";
            
            try {
                for (int i = 1; i <= ge.nro_vertices(); i++) {
                    Telefonia ec = (Telefonia) ge.obtenerEtiqueta(i);
                    mapa += "\t\t\t\nL.marker(["+ec.getLatitud()+","+ec.getLongitud()+"]).addTo(map)"+"\n";
                    mapa += ".bindPopup('"+ ec.getNombre()+"')"+"\n";
                    mapa += ".openPopup();" + "\n";
                
                }
                    FileWriter file = new FileWriter("mapas/mapa.js");
                    file.write(mapa);
                    file.close();
            } catch (Exception e) {
            }
        }
    }
    
    
    
}
