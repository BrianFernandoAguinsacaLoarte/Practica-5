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
import java.util.HashSet;
import javax.swing.JOptionPane;
import modelo.Telefonia;

/**
 *
 * @author Usuario iTC
 */
public class GrafoEtiquetadoNoDirigido<E> extends GrafoEtiquetadoDirigido<E> {

    private Double[][] distancias;
    private Integer[][] nodosIntermedios;

    public GrafoEtiquetadoNoDirigido(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices, clazz);
    }

    @Override
    public void Insertar(Integer a, Integer b, Double peso) throws Exception {
        if (a.intValue() <= nro_vertices() && b.intValue() <= nro_vertices()) {

            if (!existe_arista(a, b)) {
                Adyacencia auxO = new Adyacencia();
                auxO.setPeso(peso);
                auxO.setD(b);

                Adyacencia auxD = new Adyacencia();
                auxD.setPeso(peso);
                auxD.setD(a);

                getListaAdyacente()[a].add(auxO);
                getListaAdyacente()[b].add(auxD);

                setNro_aristas(nro_aristas() + 1);

            }
        } else {
            throw new Exception();
        }
    }

    //Método de ANCHURA
    public String anchura(Integer primero) throws LlenoException, EmptyException, Exception {
        StringBuilder anchuraCadena = new StringBuilder();
        JOptionPane.showMessageDialog(null, "Inicializacion la busquedas por ANCHURA");

        long tiempoInicio = System.nanoTime();

        Queque<Integer> cola = new Queque<>(nro_vertices());

        boolean[] visitado = new boolean[nro_vertices() + 1];

        int codigoInicio = primero;
        visitado[codigoInicio] = true;
        cola.queque(primero);

        while (cola.getSize() != 0) {
            //Agrego vertice  a la cadena
            primero = cola.dequeque();
            anchuraCadena.append(obtenerEtiqueta(primero)).append(" -----> ");

            //Adyacencias 
            LinkedList<Adyacencia> adyacentes = adyacentes(primero);

            //Iteraccion de las adyacencias
            for (Adyacencia adyacente : adyacentes.toArray()) {
                Integer etiqueta = adyacente.getD();
                if (!visitado[adyacente.getD()]) {
                    visitado[adyacente.getD()] = true;
                    cola.queque(etiqueta);
                }
            }
        }
        long tiempoFin = System.nanoTime();
        long tiempoTotal = tiempoFin - tiempoInicio;
        anchuraCadena.append("\nTiempo de la ejecucion del metodo de anchura es de: ").append(tiempoTotal).append("ms");

        // Devuelve la cadena con el recorrido en anchura
        return anchuraCadena.toString();
    }
    
    //Metodo de PROFUNDIDAD
    public String profundidad(Integer primero) throws LlenoException, EmptyException, Exception {
        StringBuilder profundidadCadena = new StringBuilder();
        JOptionPane.showMessageDialog(null, "Inicializacion la busquedas por PROFUNDIDAD");

        long tiempoInicio = System.nanoTime();
        // Creamos una instancia de tu pila personalizada
        Stack<Integer> pila = new Stack<>(nro_vertices());

        // Marcamos todos los vértices como no visitados
        boolean[] visitado = new boolean[nro_vertices() + 1];

        // Marcamos el vértice de inicio como visitado y lo agregamos a la pila
        int codigoInicio = primero;
        visitado[codigoInicio] = true;
        pila.push(primero);

        while (pila.getSize() != 0) {
            // Sacamos un vértice de la pila e incluimos su valor en la cadena
            primero = pila.pop();
            profundidadCadena.append(obtenerEtiqueta(primero)).append(" -----> ");

            // Obtenemos todos los vértices adyacentes del vértice sacado de la pila
            LinkedList<Adyacencia> adyacentes = adyacentes(primero);

            // Iteramos sobre los vértices adyacentes
            for (Adyacencia adyacente : adyacentes.toArray()) {
                Integer etiqueta = adyacente.getD();
                if (!visitado[adyacente.getD()]) {
                    visitado[adyacente.getD()] = true;
                    pila.push(etiqueta);
                }
            }
        }
        long tiempoFin = System.nanoTime();
        long tiempoTotal = tiempoFin - tiempoInicio;
        profundidadCadena.append("\nTiempo de la Ejecucion del metodo de profundidad ").append(tiempoTotal).append("ns");

        return profundidadCadena.toString();
    }

    //Algortimo de Floyd
    public void algoritmoFloyd() throws Exception {

        //Lista de los nombres de los vertices
        JOptionPane.showMessageDialog(null, "Iniciando el algortimo de FLOYD");
        LinkedList<E> nombresVertices = new LinkedList<>();
        for (int i = 1; i <= nro_vertices(); i++) {
            nombresVertices.add(obtenerEtiqueta(i));
        }

        nodosIntermedios = new Integer[nro_vertices() + 1][nro_vertices() + 1];
        distancias = new Double[nro_vertices() + 1][nro_vertices() + 1];

        //Inicializo la matris de las distancias con valor a infinito
        for (int i = 1; i <= nro_vertices(); i++) {
            for (int j = 1; j <= nro_vertices(); j++) {
                if (i == j) {
                    distancias[i][j] = 0.0;
                } else if (existe_arista(i, j)) {
                    distancias[i][j] = peso_arista(i, j);

                } else {
                    distancias[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        //Realizo el algortimo de floyd
        for (int k = 1; k <= nro_vertices(); k++) {
            for (int i = 1; i <= nro_vertices(); i++) {
                for (int j = 1; j <= nro_vertices(); j++) {

                    //Se encuentra mejor camino, se actualiza k
                    if (distancias[i][k] != Double.POSITIVE_INFINITY && distancias[k][j] != Double.POSITIVE_INFINITY
                            && distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        nodosIntermedios[i][j] = k;

                    }
                }
            }
        }

        // Mostrar las distancias mínimas con los nombres de los vértices
        System.out.println("Matriz Floyd:");

        // Imprimir encabezados de columnas
        System.out.printf("%-40s", "");
        for (int i = 1; i <= nro_vertices(); i++) {
            System.out.printf("%-30s", obtenerEtiqueta(i));
        }
        System.out.println();

        for (int i = 1; i <= nro_vertices(); i++) {
            //Etiquetas en la Fila
            System.out.printf("%-40s", obtenerEtiqueta(i));

            for (int j = 1; j <= nro_vertices(); j++) {
                //Impresion de valores 
                if (distancias[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.printf("%-30s", "INF");
                } else {
                    System.out.printf("%-30.2f", distancias[i][j]);
                }
            }
            System.out.println();
        }
        this.distancias = distancias;
    }

    public String rutaCortaFloyd (Integer origen, Integer destino) throws Exception {

        //Llamado a floyd
        algoritmoFloyd();

        if (distancias[origen][destino] == Double.POSITIVE_INFINITY) {
            return "No existe ruta de [" + obtenerEtiqueta(origen) + " a " + obtenerEtiqueta(destino) + "]";
        }
        StringBuilder rutaMasCorta = new StringBuilder();
        rutaMasCorta.append("La ruta mas corta desde ").append(obtenerEtiqueta(origen)).append(" hasta ").append(obtenerEtiqueta(destino)).append(" es de:\n");

        // Reconstruir la ruta utilizando los nodos intermedios
        java.util.Stack<Integer> ruta = new java.util.Stack<>();
        ruta.push(destino);
        int intermedio = nodosIntermedios[origen][destino];

        while (intermedio != 0) {
            ruta.push(intermedio);
            if (nodosIntermedios[origen][intermedio] != null) {
                intermedio = nodosIntermedios[origen][intermedio];
            } else {
                break;
            }
        }

        ruta.push(origen);

        //Ruta completa
        rutaMasCorta.append(obtenerEtiqueta(ruta.pop()));
        while (!ruta.isEmpty()) {
            rutaMasCorta.append(" >>>>>> ").append(obtenerEtiqueta(ruta.pop()));
        }

        rutaMasCorta.append("\nPeso total: ").append(distancias[origen][destino]);
        return rutaMasCorta.toString();

    }


    //Algortimo de dijkstra
    public HashMap<Integer, Double> dijkstra(int origen) throws EmptyException, LlenoException {
        
        HashMap<Integer, Double> distancias = new HashMap<>();
        HashSet<Integer> visitados = new HashSet<>();
        Queque<Par<Integer, Double>> colaPrioridad = new Queque<>(nro_vertices());

        //Inicilizo con Infinito y valor de 0
        for (int i = 1; i <= nro_vertices(); i++) {
            distancias.put(i, Double.POSITIVE_INFINITY);
        }
        distancias.put(origen, 0.0);

        // Agrego origen a la cola
        colaPrioridad.queque(new Par<>(origen, 0.0));

        // Iterar hasta que no queden vértices pendientes
        while (colaPrioridad.getSize() != 0) {
            // Obtener el vértice con la distancia más corta de la cola de prioridad
            int actual = colaPrioridad.dequeque().getIzquierda();

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
                    colaPrioridad.queque(new Par<>(vecino, distancias.get(vecino)));
                }
            }
        }

        return distancias;
    }

    public static void main(String args[]) {
        // Crear una instancia de EscuelaDAO
        TelefoniaDAO telefoniaDao = new TelefoniaDAO();

        try {
            telefoniaDao.cargarGrafo();
            telefoniaDao.listAll();
            GrafoEtiquetadoNoDirigido<Telefonia> grafoEscuelas = telefoniaDao.getGrafoTelefonia();
            if (grafoEscuelas != null) {
                System.out.println("Grafo de escuelas cargado correctamente.");

                // Imprimir el grafo para verificar su estructura
                System.out.println(grafoEscuelas.toString());

                // Realizar la búsqueda en anchura (BFS) desde un vértice específico, por ejemplo, "Bravo"
                System.out.println("Inicio del método BFS");
                System.out.println("Etiqueta 1->" + grafoEscuelas.obtenerEtiqueta(1));

                System.out.println("Escuela ->>>>>>>>");
                System.out.println("Escuela 0 ->" + telefoniaDao.getTelefonos().get(1));
                System.out.println("ESCUELA 1 ->" + telefoniaDao.getTelefonos().get(2));

                System.out.println("Adyancencias ->>>");
                System.out.println(grafoEscuelas.adyacentes(1).print());

                System.out.println("Anchura");
                //grafoEscuelas.anchura(grafoEscuelas.obtenerEtiqueta(1));
                System.out.println("\nProfundidad");
                grafoEscuelas.profundidad(grafoEscuelas.obtenerEtiqueta(1));

            } else {
                System.out.println("No se pudo cargar el grafo de escuelas.");
            }

        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

}
