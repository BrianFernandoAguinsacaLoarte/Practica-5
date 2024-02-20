/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.Excepcion.EmptyException;
import controlador.Excepcion.LlenoException;
import controlador.TDA.listas.LinkedList;
import controlador.TDA.listas.Queque;
import controlador.TDA.listas.Stack;
import controlador.grafo.telefono.Par;
import controlador.grafo.telefono.TelefoniaDAO;
import java.util.HashMap;
import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import javax.swing.JOptionPane;
import modelo.Telefonia;

/**
 *
 * @author Usuario iTC
 */
public class GrafoEtiquetadoDirigido<E> extends GrafoDirigido {

    protected E etiqueta[]; //etiquetas del grafo
    protected HashMap<E, Integer> dicVertices;
    private Class<E> clazz;
    private Double[][] distancias;
    private Integer[][] nodosIntermedios;

    public GrafoEtiquetadoDirigido(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices);
        this.clazz = clazz;
        etiqueta = (E[]) Array.newInstance(clazz, nro_vertices() + 1);
        dicVertices = new HashMap<>(nro_vertices);
    }

    //Si existe arista etiquetado, Origen y destino
    public Boolean existeAristaE(E o, E d) throws Exception {

        if (estaEtiquetado()) {
            return existe_arista(obtenerCodigoE(o), obtenerCodigoE(d));
        } else {
            throw new EtiquetaException();
        }
    }

    public void InsertarAristaE(E o, E d, Double peso) throws Exception {
        if (estaEtiquetado()) {
            Insertar(obtenerCodigoE(o), obtenerCodigoE(d), peso);
        } else {
            throw new EtiquetaException();
        }
    }

    public void insertarAristaE(E o, E d) throws Exception {
        if (estaEtiquetado()) {
            Insertar(obtenerCodigoE(o), obtenerCodigoE(d), Double.NaN);
        } else {
            throw new EtiquetaException();
        }
    }

    public LinkedList<Adyacencia> adyacentesE(E o) throws Exception {
        if (estaEtiquetado()) {
            return adyacentes(obtenerCodigoE(o));
        } else {
            throw new EtiquetaException();
        }
    }

    public void etiquetarVertice(Integer vertice, E dato) {
        etiqueta[vertice] = dato;
        dicVertices.put(dato, vertice);
    }

    public Boolean estaEtiquetado() {
        Boolean band = true;

        for (int i = 1; i < etiqueta.length; i++) {
            E dato = etiqueta[i];

            if (dato == null) {
                band = false;
                break;
            }
        }

        return band;
    }

    public Integer obtenerCodigoE(E etiqueta) {
        return dicVertices.get(etiqueta);
    }

    public E obtenerEtiqueta(Integer i) {
        return etiqueta[i];
    }

    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder("GRAFOS ETIQUETADOS \n");

        try {
            for (int i = 1; i <= nro_vertices(); i++) {

                grafo.append("Vertice: ").append(obtenerEtiqueta(i)).append("\n");

                if (!adyacentes(i).isEmpty()) {
                    Adyacencia[] lista = adyacentes(i).toArray();
                    for (int j = 0; j < lista.length; j++) {
                        Adyacencia a = lista[j];
                        grafo.append("Adyacente: ").append(obtenerEtiqueta(a.getD())).append("-Peso-").append(a.getPeso()).append("\n");
                    }
                }
            }
        } catch (Exception e) {
        }
        return grafo.toString();
    }

      

    public String anchuraDir(Integer primero) throws Exception {
        StringBuilder anchuraCadena = new StringBuilder();

        long tiempoInicio = System.nanoTime();

        // Creamos una instancia de tu cola personalizada
        Queque<Integer> cola = new Queque<>(nro_vertices());

        // Marcamos todos los vértices como no visitados
        boolean[] visitado = new boolean[nro_vertices() + 1];

        // Marcamos el vértice de inicio como visitado y lo agregamos a la cola
        visitado[primero] = true;
        cola.queque(primero);

        while (cola.getSize() != 0) {
            // Sacamos un vértice de la cola y lo agregamos a la cadena
            primero = cola.dequeque();
            anchuraCadena.append(obtenerEtiqueta(primero)).append(" -----> ");

            // Obtenemos todos los vértices adyacentes del vértice sacado de la cola
            LinkedList<Adyacencia> adyacentes = adyacentes(primero);

            // Iteramos sobre los vértices adyacentes
            for (Adyacencia adyacente : adyacentes.toArray()) {
                Integer etiqueta = adyacente.getD();
                if (!visitado[etiqueta]) {
                    visitado[etiqueta] = true;
                    cola.queque(etiqueta);
                }
            }
        }

        long tiempoFin = System.nanoTime();
        long tiempoTotal = tiempoFin - tiempoInicio;
        anchuraCadena.append("\nTiempo de la Ejecucion del metodo de anchura ").append(tiempoTotal).append("ns");

        // Devuelve la cadena con el recorrido en anchura
        return anchuraCadena.toString();
    }

   

    public void profundidad(E primero) throws LlenoException, EmptyException, Exception {

        long tiempoInicio = System.nanoTime();
        // Creamos una instancia de tu pila personalizada
        Stack<E> pila = new Stack<>(nro_vertices());

        // Marcamos todos los vértices como no visitados
        boolean[] visitado = new boolean[nro_vertices() + 1];

        // Marcamos el vértice de inicio como visitado y lo agregamos a la pila
        int codigoInicio = obtenerCodigoE(primero);
        visitado[codigoInicio] = true;
        pila.push(primero);

        while (pila.getSize() != 0) {
            // Sacamos un vértice de la pila e imprimimos
            primero = pila.pop();
            System.out.print(primero + " -----> ");

            // Obtenemos todos los vértices adyacentes del vértice sacado de la pila
            LinkedList<Adyacencia> adyacentes = adyacentesE(primero);

            // Iteramos sobre los vértices adyacentes
            for (Adyacencia adyacente : adyacentes.toArray()) {
                E etiqueta = obtenerEtiqueta(adyacente.getD());
                if (!visitado[adyacente.getD()]) {
                    visitado[adyacente.getD()] = true;
                    pila.push(etiqueta);
                }
            }
        }
        long tiempoFin = System.nanoTime();
        long tiempoTotal = tiempoFin - tiempoInicio;
        System.out.println("\nTiempo de la Ejecucion del metodo de profundidad " + tiempoTotal + "ns");
    }

    
    //FLOYD
    public void algoritmoFloyd() throws Exception {
        LinkedList<E> nombresVertices = new LinkedList<>();
        for (int i = 1; i <= nro_vertices(); i++) {
            nombresVertices.add(obtenerEtiqueta(i));
        }

        nodosIntermedios = new Integer[nro_vertices() + 1][nro_vertices() + 1];
        distancias = new Double[nro_vertices() + 1][nro_vertices() + 1];

        for (int i = 1; i <= nro_vertices(); i++) {
            for (int j = 1; j <= nro_vertices(); j++) {
                if (i == j) {
                    distancias[i][j] = 0.0;
                } else if (existe_arista(i, j)) {
                    distancias[i][j] = peso_arista(i, j);
                    nodosIntermedios[i][j] = i; // El nodo intermedio en una arista directa es el origen.
                    distancias[j][i] = distancias[i][j]; // Considera la arista en la dirección opuesta
                    nodosIntermedios[j][i] = j;
                } else {
                    distancias[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        for (int k = 1; k <= nro_vertices(); k++) {
            for (int i = 1; i <= nro_vertices(); i++) {
                for (int j = 1; j <= nro_vertices(); j++) {
                    if (distancias[i][k] != Double.POSITIVE_INFINITY && distancias[k][j] != Double.POSITIVE_INFINITY
                            && distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        nodosIntermedios[i][j] = nodosIntermedios[k][j]; // El nodo intermedio es el mismo que para k->j.
                        distancias[j][i] = distancias[i][j]; // Actualiza la distancia en la dirección opuesta
                        nodosIntermedios[j][i] = nodosIntermedios[i][j];
                    }
                }
            }
        }
    }

    public String rutaCortaFloyd(Integer origen, Integer destino) throws Exception {
        algoritmoFloyd();

        if (distancias[origen][destino] == Double.POSITIVE_INFINITY) {
            return "No existe ruta de [" + obtenerEtiqueta(origen) + " a " + obtenerEtiqueta(destino) + "]";
        }

        StringBuilder rutaMasCorta = new StringBuilder();
        rutaMasCorta.append("La ruta más corta desde ").append(obtenerEtiqueta(origen)).append(" hasta ").append(obtenerEtiqueta(destino)).append(" es de:\n");

        java.util.Stack<Integer> ruta = new java.util.Stack<>();
        ruta.push(destino);
        int intermedio = nodosIntermedios[origen][destino];

        while (intermedio != origen) {
            ruta.push(intermedio);
            intermedio = nodosIntermedios[origen][intermedio];
        }

        ruta.push(origen);

        rutaMasCorta.append(obtenerEtiqueta(ruta.pop()));
        while (!ruta.isEmpty()) {
            rutaMasCorta.append(" -> ").append(obtenerEtiqueta(ruta.pop()));
        }

        rutaMasCorta.append("\nPeso total: ").append(distancias[origen][destino]);
        return rutaMasCorta.toString();
    }

    //Algortimo de dijkstra
    public HashMap<Integer, Double> dijkstraNew(int origen) {
        HashMap<Integer, Double> distancias = new HashMap<>();
        HashSet<Integer> visitados = new HashSet<>();
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

                //Realizo adyacencias 
                System.out.println("Inserto Arista ->>>>>>>>");
                System.out.println(escuelaDAO.getTelefonos().get(0));
                System.out.println(escuelaDAO.getTelefonos().get(1));
                grafoEscuelas.InsertarAristaE(escuelaDAO.getTelefonos().get(0), escuelaDAO.getTelefonos().get(1), 50.00);

                System.out.println("Adyancencias ->>>");
                System.out.println(grafoEscuelas.adyacentes(1).print());
                System.out.println("Lista");
                System.out.println(grafoEscuelas.getListaAdyacente().toString());

                // Aplicar el algoritmo de Floyd-Warshall para calcular las distancias mínimas
                System.out.println("SADSA");
                System.out.println(grafoEscuelas.obtenerEtiqueta(0));
                System.out.println(grafoEscuelas.obtenerEtiqueta(1));

                System.out.println("ID1" + escuelaDAO.getTelefonos().get(0).getId());
                System.out.println("ID1" + escuelaDAO.getTelefonos().get(1).getId());

                System.out.println("Anchura Dirigido " + grafoEscuelas.anchuraDir(1));

                //System.out.println(grafoEscuelas.rutaCortaFloyd(escuelaDAO.getTelefonos().get(0).getId(), escuelaDAO.getTelefonos().get(1).getId()));
            } else {
                System.out.println("No se pudo cargar el grafo de escuelas.");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }

    }

}
