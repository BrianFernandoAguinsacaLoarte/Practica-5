
package controlador.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class Utilidades {
    
    public static Field getField(Class clazz, String attribute){
        Field[] fields = clazz.getFields();
        Field resp = null;
        //System.out.println(fields.length);
        for(Field f: fields ){
            if(attribute.equalsIgnoreCase(f.getName())){
                resp = f;
            }
        }
        
        Field[] fields1 = clazz.getDeclaredFields();
        //System.out.println(fields1.length);
        for(Field f: fields1 ){
            if(attribute.equalsIgnoreCase(f.getName())){
                resp = f;
            }
        }
        return resp;
    }
    
    public static Object getData(Object clas, String attribute) throws  InvocationTargetException, IllegalAccessException{
        Class clazz = clas.getClass();
        Field f = getField(clazz, attribute);
        Object obj = null;
        String auxAttribute = "get"+capitalize(attribute);
        Method method = null;
        if(f!=null){
            for(Method m: clazz.getMethods()){
            //System.out.println(m.getName());
                if(m.getName().equalsIgnoreCase(auxAttribute)){
                     method = m;
                     break;
                }
            }

            for(Method m: clazz.getDeclaredMethods()){
                //System.out.println(m.getName());
                if(m.getName().equalsIgnoreCase(auxAttribute)){
                     method = m;
                     break;
                }
            }
            
            if(method != null){
                obj = method.invoke(clas);
            }
        }
        
        return obj;
    }
    
    public static String capitalize(String cnd){
        char[] caracteres = cnd.toCharArray();
        String aux = String.valueOf(caracteres[0]).toUpperCase();
        caracteres[0] = aux.charAt(0);
        return new String(caracteres);
    }
    
    //Brian Aguinsaca 3B
    //Ver DIreccion
    public static String getDirProject(){
        return System.getProperty("user.dir");
    }
    
    //Ver sistema Operativo
    public static String getOS(){
        return System.getProperty("os.name");
    }
    
    public static void abrirNavegadorPredeterminadoWindows(String url) throws Exception {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+ url);
    }
    
     public static void copiarArchivo(File origen, File destino) throws Exception {
        Files.copy(origen.toPath(),
        (destino).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
    }
     
     public static String extension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
        extension = fileName.substring(i + 1);
        }
        return extension;
   }
     
     
    public static double distanciaDosPuntos(double lat1, double lon1, double lat2, double lon2) {        
        double lat1rad = Math.toRadians(lat1);
        double lon1rad = Math.toRadians(lon1);
        double lat2rad = Math.toRadians(lat2);
        double lon2rad = Math.toRadians(lon2);

        double difLatitud = lat1rad - lat2rad;
        double difLongitud = lon1rad - lon2rad;

        double a = Math.pow(Math.sin(difLatitud/2), 2) +
                Math.cos(lat1rad) *
                Math.cos(lat2rad) *
                Math.pow(Math.sin(difLongitud/2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double radioTierraKm = 6378.0;
        double distancia = radioTierraKm * c;

        return distancia;
    }
    
    public static Double redondear(Double x){
        return Math.round(x * 100.0) / 100.0;
    }
}
