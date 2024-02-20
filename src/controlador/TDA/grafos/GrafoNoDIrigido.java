/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.grafos.excepcion.VerticeOfSizeExecption;
import controlador.TDA.listas.LinkedList;

/**
 *
 * @author Usuario iTC
 */
public class GrafoNoDIrigido extends Grafo {
    private Integer nro_vertices;
    private Integer nro_aristas;
    private LinkedList<Adyacencia> listaAdyacente[];

    public GrafoNoDIrigido(Integer nro_vertices) {
        this.nro_vertices = nro_vertices;
        nro_aristas = 0;
        listaAdyacente = new LinkedList[nro_vertices + 1];
        
        for(int i=1; i<= nro_vertices; i++){
            listaAdyacente[i] = new LinkedList<>();
            
        }
    }

    @Override
    public Integer nro_vertices() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return this.nro_vertices;
    }

    @Override
    public Integer nro_aristas() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return this.nro_aristas;
    }

    @Override
    public Boolean existe_arista(Integer a, Integer b) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
       Boolean band = false;
        if(a.intValue() <= nro_vertices.intValue() && b.intValue() <= nro_vertices.intValue()){
            LinkedList<Adyacencia> lista = listaAdyacente[a];
            
            for(int i = 0; i < lista.getSize(); i++){
                Adyacencia aux = lista.get(i);
                
                if(aux.getD().intValue() == b.intValue()){
                    band = true;
                    break;
                }
            }
            
        }else{
            throw new VerticeOfSizeExecption();
        } 
        
        return band;
    }

    @Override
    public Double peso_arista(Integer a, Integer b) throws Exception {
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
        Double peso = Double.NaN;
        if (existe_arista(a, b)) {
            LinkedList<Adyacencia> lista = listaAdyacente[a];

            for (int i = 0; i < lista.getSize(); i++) {
                Adyacencia aux = lista.get(i);

                if (aux.getD().intValue() == b.intValue()) {
                    peso = aux.getPeso();
                    break;
                }
            }
        }
        return peso;
    }

    @Override
    public void Insertar(Integer a, Integer b) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
          Insertar(a, b, Double.NaN);
    }

    @Override
    public void Insertar(Integer a, Integer b, Double peso) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        if (a.intValue() <= nro_vertices.intValue() && b.intValue() <= nro_vertices.intValue()) {
            
            if (!existe_arista(a, b)) {
                nro_aristas++;
                Adyacencia AdyacenciaA = new Adyacencia();
                AdyacenciaA.setPeso(peso);
                AdyacenciaA.setD(b);
                listaAdyacente[a].add(AdyacenciaA);

                Adyacencia AdyacenciaB = new Adyacencia();
                AdyacenciaB.setPeso(peso);
                AdyacenciaB.setD(a);
                listaAdyacente[b].add(AdyacenciaB);
            }
        }
    }

    @Override
    public LinkedList<Adyacencia> adyacentes(Integer a) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         return listaAdyacente[a];
    }
    
    public static void main(String args[]) {
        GrafoNoDIrigido gnd = new GrafoNoDIrigido(5);
        
        try {
            gnd.Insertar(1, 3);
            gnd.Insertar(5, 1);
            gnd.Insertar(2, 4);
            gnd.Insertar(3, 4);
            gnd.Insertar(4, 1);
            System.out.println(gnd.toString());

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    
    
}
