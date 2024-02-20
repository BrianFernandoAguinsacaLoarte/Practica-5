/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.grafos.excepcion.VerticeOfSizeExecption;
import controlador.TDA.listas.LinkedList;
import controlador.grafo.telefono.Par;
import controlador.grafo.telefono.TelefoniaDAO;
import controlador.util.Utilidades;
import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import modelo.Telefonia;

/**
 *
 * @author Usuario iTC
 */
public class GrafoDirigido extends Grafo{
    
    private Integer nro_vertices;
    private Integer nro_aristas;
    private LinkedList<Adyacencia> listaAdyacente[];
    
    public GrafoDirigido(Integer nro_vertices){
        this.nro_vertices = nro_vertices;
        nro_aristas = 0;
        listaAdyacente = new LinkedList[nro_vertices + 1];
    
        for(int i = 1; i <= nro_vertices; i++){
            listaAdyacente[i] = new LinkedList<>();
        }
    }

    public LinkedList<Adyacencia>[] getListaAdyacente() {
        return listaAdyacente;
    }
    
    

    public void setNro_aristas(Integer nro_aristas) {
        this.nro_aristas = nro_aristas;
    }
    
    
    
    
    
    @Override
    public Integer nro_vertices() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return this.nro_vertices;
    }

    @Override
    public Integer nro_aristas() {
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return this.nro_aristas;
    }

    @Override
    public Boolean existe_arista(Integer a, Integer b)  throws Exception{
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        Boolean band = false;
        if(a.intValue() <= nro_vertices.intValue() && b.intValue() <= nro_vertices.intValue()){
            LinkedList<Adyacencia> lista = listaAdyacente[a];
            
            for(int i = 0; i < lista.getSize(); i++){
                Adyacencia aux = lista.get(i);
                
                //Compara el vertice de destino b con los vertices de destino de cada adyacencia
                //en la lista
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
    public Double peso_arista(Integer a, Integer b) throws Exception{
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        Double peso = Double.NaN;
        if(existe_arista(a, b)){
            //Accedo a la lista de adyacencia del vertice a
            //Representa las aristas salientes del vertice a
            LinkedList<Adyacencia> lista = listaAdyacente[a];
            
            for(int i = 0; i < lista.getSize(); i++){
                Adyacencia aux = lista.get(i);
                
                if(aux.getD().intValue() == b.intValue()){
                    peso = aux.getPeso();
                    break;
                }
            }
        }
        return peso;
    }

    @Override
    public void Insertar(Integer a, Integer b) throws Exception{
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        Insertar(a,b,Double.NaN);
                  
    
    }
    
    
    @Override
    public void Insertar(Integer a, Integer b, Double peso) throws Exception {
     //   throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if(a.intValue() <= nro_vertices.intValue() && b.intValue() <= nro_vertices.intValue()){
            //verifico que no exista arista desde A a B 
            if (!existe_arista(a, b)) {
                nro_aristas++;
                Adyacencia aux = new Adyacencia();
                aux.setPeso(peso);
                aux.setD(b);
                listaAdyacente[a].add(aux);
            }
            }
    }

    @Override
    public LinkedList<Adyacencia> adyacentes(Integer a) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return listaAdyacente[a];
    }
    
    public HashMap<Integer, Double> dijkstra(Integer origen) {
        // Inicializar un mapa para almacenar las distancias más cortas desde el origen hasta cada vértice
        HashMap<Integer, Double> distancias = new HashMap<>();

        // Inicializar un conjunto para almacenar los vértices visitados
        HashSet<Integer> visitados = new HashSet<>();

        // Inicializar una cola de prioridad para almacenar los vértices pendientes y sus distancias
        PriorityQueue<Par<Integer, Double>> colaPrioridad = new PriorityQueue<>(Comparator.comparingDouble(Par::getDerecha));

        // Inicializar todas las distancias con infinito y el origen con distancia 0
        for (int i = 1; i <= nro_vertices(); i++) {
            distancias.put(i, Double.POSITIVE_INFINITY);
        }
        distancias.put(origen, 0.0);

        // Agregar el origen a la cola de prioridad
        colaPrioridad.offer(new Par<>(origen, 0.0));

        // Iterar hasta que no queden vértices pendientes
        while (!colaPrioridad.isEmpty()) {
            // Obtener el vértice con la distancia más corta de la cola de prioridad
            int actual = colaPrioridad.poll().getIzquierda();

            // Si el vértice actual ya fue visitado, continuar con el siguiente
            if (visitados.contains(actual)) {
                continue;
            }

            // Marcar el vértice actual como visitado
            visitados.add(actual);

            // Obtener las adyacencias del vértice actual
            LinkedList<Adyacencia> adyacencias = adyacentes(actual);

            // Iterar sobre las adyacencias
            for (Adyacencia adyacente : adyacencias.toArray()) {
                int vecino = adyacente.getD();
                double peso = adyacente.getPeso();

                // Si la distancia desde el origen hasta el vecino a través del vértice actual es menor que la distancia actual,
                // actualizar la distancia y agregar el vecino a la cola de prioridad
                if (!visitados.contains(vecino) && distancias.get(actual) + peso < distancias.get(vecino)) {
                    distancias.put(vecino, distancias.get(actual) + peso);
                    colaPrioridad.offer(new Par<>(vecino, distancias.get(vecino)));
                }
            }
        }

        return distancias;
    }
    
//    public static void main(String[] args){
//        try {
//            //Brian Aguinsaca 3B
//            //System.out.println(Utilidades.getOS());
//           // System.out.println(Utilidades.getDirProject());
//            
//            GrafoDirigido gd = new GrafoDirigido(5);
//            gd.Insertar(1, 3);
//            gd.Insertar(1, 2);
//            gd.Insertar(2, 4);
//            gd.Insertar(2, 5);
//            gd.Insertar(3, 3);
//            
//            System.out.println(gd);
//            DibujarGrafo df = new DibujarGrafo();
//            
//            //df.crearArchivo(gd);
//            
//            String os = Utilidades.getOS();
//            String dir = Utilidades.getDirProject();
//            
//            if(os.equalsIgnoreCase("Windows 10")){
//                System.out.println("Entro en el if");
//                Utilidades.abrirNavegadorPredeterminadoWindows(dir + File.separatorChar+"d3"+File.separatorChar+"grafo.html");
//            }
//        } catch (Exception ex) {
//            System.out.println("ex "+ex);
//        }
//    }
    
    public static void main(String[] args) {
    // Crear una instancia de EscuelaDAO
        TelefoniaDAO escuelaDAO = new TelefoniaDAO();
    try {
        // Listar todas las escuelas
        escuelaDAO.listAll();

        // Obtener el grafo de escuelas
        GrafoEtiquetadoDirigido<Telefonia> grafoEscuelas = escuelaDAO.getGrafoTelefonia();

        if (grafoEscuelas != null) {
            System.out.println("Grafo de escuelas cargado correctamente.");

            // Imprimir el grafo para verificar su estructura
            System.out.println(grafoEscuelas.toString());

            // Realizo adyacencias
            System.out.println("Inserto Arista ->>>>>>>>");
            System.out.println(escuelaDAO.getTelefonos().get(0));
            System.out.println(escuelaDAO.getTelefonos().get(1));
            grafoEscuelas.InsertarAristaE(escuelaDAO.getTelefonos().get(0), escuelaDAO.getTelefonos().get(1), 50.00);
            grafoEscuelas.InsertarAristaE(escuelaDAO.getTelefonos().get(1), escuelaDAO.getTelefonos().get(2), 80.00);
            grafoEscuelas.InsertarAristaE(escuelaDAO.getTelefonos().get(0), escuelaDAO.getTelefonos().get(3), 40.00);
            System.out.println("Adyancencias ->>>");
            System.out.println(grafoEscuelas.adyacentes(1).print());
            System.out.println("Lista");

            // Aplicar el algoritmo de Dijkstra para calcular las distancias más cortas desde el vértice 1
            HashMap<Integer, Double> distanciasDijkstra = grafoEscuelas.dijkstra(1);

            // Imprimir las distancias más cortas
            for (Map.Entry<Integer, Double> entry : distanciasDijkstra.entrySet()) {
                System.out.println("Distancia más corta desde el vértice 1 al vértice " + entry.getKey() + ": " + entry.getValue());
            }

        } else {
            System.out.println("No se pudo cargar el grafo de escuelas.");
        }
    } catch (Exception ex) {
        System.out.println(ex);
        ex.printStackTrace();
    }
}
    
}
