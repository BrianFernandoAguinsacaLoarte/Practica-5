/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.Excepcion.EmptyException;
import controlador.TDA.listas.LinkedList;
import controlador.util.Utilidades;
import java.io.FileWriter;
//import modelo.ejemploGrafo.Escuela;

/**
 *
 * @author Usuario iTC
 */
public class DibujarGrafo {
    
    String URL = "d3/grafo.js"; //Modificacion de la Url
    
    public void crearArchivo (GrafoEtiquetadoDirigido g){
        StringBuilder valor = new StringBuilder("var nodes = new vis.DataSet([\n");
        
        for(int i=1; i <= g.nro_vertices(); i++){
            String etq = g.obtenerEtiqueta(i).toString();
            
            valor.append("\t{ id: ").append(i).append(", label: '").append("").append(etq).append("' },\n");
        }
        valor.append("]);");
        
        try {
            valor.append("\nvar edges = new vis.DataSet([\n");
            for(int i=1; i <= g.nro_vertices(); i++){
                if(!g.adyacentes(i).isEmpty()){
                    Adyacencia[] lista = g.adyacentes(i).toArray();
                    for(int j = 0; j < lista.length; j++){
                        Adyacencia ady = lista[j];
                        if(g.existe_arista(i, ady.getD())){
                            valor.append("\t{ from: ").append(i).append(", to: ").append(ady.getD()).append(", label: '").append(Utilidades.redondear(ady.getPeso())).append("'},\n");
                        }
                    }
                }
            }
            valor.append("]);");
            String data = valor.toString();
            String finalArchivo = "\nvar container = document.getElementById(\"mynetwork\");\n"
                + "      var data = {\n"
                + "        nodes: nodes,\n"
                + "        edges: edges,\n"
                + "      };\n"
                + "      var options = {};\n"
                + "      var network = new vis.Network(container, data, options);\n"
                + "";
            
            FileWriter file = new FileWriter(URL);
            file.write(data+"\n" + finalArchivo);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    
}

    
    
    
    
    
    
    
    
    
    


















/*
 String URL = "d3/grafo.js"; //Modificacion de la Url
    
    public void crearArchivo(Grafo g) throws EmptyException {
        //Generar Datos y Adyacencias
        String autor = "//Brian Aguinsaca 3B";
        
        String nodes = "var nodes = new vis.DataSet([\n";
                    for(int i=1; i <= g.nro_vertices(); i++){
                        nodes += String.format("\t{id: %d, label: \"Node %d\"},\n",i,i);
                    }
               nodes += "\t]);";
               
        String edges = "\nvar edges = new vis.DataSet([\n";
                for(int i=1; i <= g.nro_vertices(); i++){
                    LinkedList<Adyacencia> adyacentes = g.adyacentes(i);
                        for(int j =0; j < adyacentes.getSize(); j++){
                            Adyacencia a = adyacentes.get(j);
                            edges += String.format("\t{from: %d, to: %d},\n",i,a.getD());
                        }
                    }
               edges +="\t]);";
        
        String finalArchivo = "\nvar container = document.getElementById(\"mynetwork\");\n"
                + "      var data = {\n"
                + "        nodes: nodes,\n"
                + "        edges: edges,\n"
                + "      };\n"
                + "      var options = {};\n"
                + "      var network = new vis.Network(container, data, options);\n"
                + "";
        try {
            FileWriter file = new FileWriter(URL);
            file.write(autor+"\n"+nodes+"\n"+ edges + "\n" + finalArchivo);
            file.close();

        } catch (Exception e) {
        }
    }
*/

