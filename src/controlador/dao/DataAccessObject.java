
package controlador.dao;

import com.thoughtworks.xstream.XStream;
//import controlador.Excepcion.EmptyException;
//import controlador.marcas.MarcaController;
import controlador.TDA.listas.LinkedList;
//import controlador.util.Utilidades;
//import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
//import modelo.Marca;

public class DataAccessObject<T> implements TransferObject<T>{

    private Class<T> clazz;
    private XStream xstream;
    private String URL;
   
    
    
    public DataAccessObject(Class<T> clazz){
        this.clazz = clazz;
        xstream = Connection.getXstream();
        URL = Connection.getURL()+this.clazz.getSimpleName()+".json";
    }
    
    @Override
    public boolean save(T data) {
        LinkedList<T> list = listAll();
        list.add(data);
        try {
            this.xstream.toXML(list,new FileWriter(URL));
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
       
    @Override
    public boolean update(T data, Integer index) {
        LinkedList<T> list = listAll();
        try {
            list.update(data, index);
            this.xstream.toXML(list, new FileWriter(URL));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public LinkedList<T> listAll() {
        LinkedList<T> list = new LinkedList<>();
        
        try {
            list = (LinkedList<T>)xstream.fromXML(new FileReader(URL));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public T find(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Integer generated_id(){
        return listAll().getSize() + 1;
    }

    public XStream getXstream() {
        return xstream;
    }
    
    
    
    
   
}
