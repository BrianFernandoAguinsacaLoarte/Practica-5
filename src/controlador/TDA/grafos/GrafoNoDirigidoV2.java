/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

/**
 *
 * @author Usuario iTC
 */
public class GrafoNoDirigidoV2 extends GrafoDirigido {

    public GrafoNoDirigidoV2(Integer nro_vertices) {
        super(nro_vertices);
    }
    
    
     @Override
    public void Insertar(Integer a, Integer b) throws Exception{
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        Insertar(a,b,Double.NaN);
                  
    
    }
     @Override
    public void Insertar(Integer a, Integer b, Double peso) throws Exception {
     //   throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if(a.intValue() <= nro_vertices().intValue() && b.intValue() <= nro_vertices().intValue()){
            //verifico que no exista arista desde A a B 
            if (!existe_arista(a, b)) {
                setNro_aristas(nro_aristas() + 1);
                
                Adyacencia auxO = new Adyacencia();
                auxO.setPeso(peso);
                auxO.setD(b);
                
                Adyacencia auxD = new Adyacencia();
                auxD.setPeso(peso);
                auxD.setD(b);
                
               getListaAdyacente()[a].add(auxO);
               getListaAdyacente()[a].add(auxD);
            }
            }
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
