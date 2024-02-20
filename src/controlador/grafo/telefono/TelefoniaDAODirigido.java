/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.grafo.telefono;

import controlador.Excepcion.EmptyException;
import controlador.TDA.grafos.DibujarGrafo;
import controlador.TDA.grafos.GrafoEtiquetadoDirigido;
import controlador.TDA.grafos.GrafoEtiquetadoNoDirigido;
import controlador.TDA.listas.LinkedList;
import controlador.TDA.listas.Queque;
import controlador.dao.DataAccessObject;
import controlador.util.Utilidades;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import modelo.Telefonia;

/**
 *
 * @author Usuario iTC
 */
public class TelefoniaDAODirigido extends DataAccessObject<Telefonia> {
    
    private Telefonia telefonia = new Telefonia();
    private LinkedList<Telefonia> telefonos = new LinkedList<>();
    private GrafoEtiquetadoDirigido<Telefonia> grafoTelefonia;
    
    //Constructor

    public TelefoniaDAODirigido() {
        super(Telefonia.class);
    }
    
     //Metodos
    public boolean save(){
        telefonia.setId(generated_id());//BDD Esto desaparece
        return save(telefonia);
    }
    
    public boolean update(){
        return update(telefonia,buscarIndex(telefonia.getId()));
    }
    
    public Integer buscarIndex(Integer id) {
        Integer index = -1;
        if (!listAll().isEmpty()) {
            {
                Telefonia[] telefonos = listAll().toArray();

                for (int i = 0; i < telefonos.length; i++) {
                    if (id.intValue() == telefonos[i].getId().intValue()) {
                        index = i;
                        break;
                    }
                }
            }
        }
        return index;
    }
    
    //Getter and Setter

    public Telefonia getTelefonia() {
        
        if(telefonia == null){
            telefonia = new Telefonia();
        }
        return telefonia;
    }

    public void setTelefonia(Telefonia telefonia) {
        this.telefonia = telefonia;
    }

    public LinkedList<Telefonia> getTelefonos() {
         if(telefonos.isEmpty()){
            telefonos = listAll();
        }
        return telefonos;
    }

    public void setTelefonos(LinkedList<Telefonia> telefonos) {
        this.telefonos = telefonos;
    }

    public GrafoEtiquetadoDirigido<Telefonia> getGrafoTelefonia() throws EmptyException {
        
        if(grafoTelefonia == null){
            LinkedList<Telefonia> lista = getTelefonos();
            Integer size = lista.getSize();
            if(size > 0){
                grafoTelefonia = new GrafoEtiquetadoDirigido<>(size,Telefonia.class);
                
                for(int i=0; i < size; i++){
                    grafoTelefonia.etiquetarVertice((i+1), lista.get(i));
                }
            }
        }
        
        return grafoTelefonia;
    }

    public void setGrafoTelefonia(GrafoEtiquetadoDirigido<Telefonia> grafoTelefonia) {
        this.grafoTelefonia = grafoTelefonia;
    }
    
    public void guardarGrafo() throws Exception {

        if (grafoTelefonia != null) {

            getXstream().alias(grafoTelefonia.getClass().getName(), GrafoEtiquetadoDirigido.class);
            getXstream().toXML(grafoTelefonia, new FileWriter("data/grafo.json"));

        } else {
            new NullPointerException("Grafo vacio");
        }

    }

    public void cargarGrafo() throws Exception {
        grafoTelefonia = (GrafoEtiquetadoDirigido<Telefonia>) getXstream().fromXML(new FileReader("data/grafo.json"));
        telefonos.clear();
        for (int i = 0; i < grafoTelefonia.nro_vertices(); i++) {
            telefonos.add(grafoTelefonia.obtenerEtiqueta(i));
        }

    }
    
    //Meotodos
    

    
    
    
    
    //Revisar

    public static void main(String[] args0) {
        try {
            TelefoniaDAODirigido ed = new TelefoniaDAODirigido();
            DibujarGrafo dg = new DibujarGrafo();
            dg.crearArchivo(ed.getGrafoTelefonia());

            String dir = Utilidades.getDirProject();
            if (Utilidades.getOS().equalsIgnoreCase("Windows 10")) {
                System.out.println("Estoy en el if");
                Utilidades.abrirNavegadorPredeterminadoWindows(dir + File.separatorChar + "d3" + File.separatorChar + "grafo.html");

            } else {
                System.out.println("Estoy en el else");
                Utilidades.abrirNavegadorPredeterminadoWindows(dir + File.separatorChar + "d3" + File.separatorChar + "grafo.html");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
