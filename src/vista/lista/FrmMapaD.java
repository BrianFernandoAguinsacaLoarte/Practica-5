/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.lista;

import controlador.Excepcion.EmptyException;
import controlador.TDA.grafos.DibujarGrafo;
import controlador.TDA.listas.LinkedList;
import controlador.grafo.telefono.TelefoniaDAODirigido;
import controlador.util.Utilidades;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JOptionPane;
import modelo.Telefonia;
import vista.grafo.escuela.UtilesEscuelaVista;
import vista.listas.tablas.ModeloTablaAdyacenciasD;

/**
 *
 * @author Usuario iTC
 */
public class FrmMapaD extends javax.swing.JDialog {

    private TelefoniaDAODirigido ed = new TelefoniaDAODirigido();
    private ModeloTablaAdyacenciasD mtady = new ModeloTablaAdyacenciasD();
    
    public FrmMapaD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        limpiar();
        this.setLocationRelativeTo(null);
    }
    
     private void guardarGrafo(){
        int i = JOptionPane.showConfirmDialog(null, "Esta de acuerdo con guardar el grafo?", "Pregunta", JOptionPane.OK_CANCEL_OPTION);
        
        if(i == JOptionPane.OK_OPTION){
            try {
                ed.guardarGrafo();
                JOptionPane.showMessageDialog(null, "Grafo Guardado", "OK", JOptionPane.OK_OPTION);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarGrafo(){
         int i = JOptionPane.showConfirmDialog(null, "Esta de acuerdo con guardar el grafo?", "Pregunta", JOptionPane.OK_CANCEL_OPTION);
        
        if(i == JOptionPane.OK_OPTION){
            try {
                 ed.cargarGrafo();
           limpiar();
                JOptionPane.showMessageDialog(null, "Grafo Cargado", "OK", JOptionPane.OK_OPTION);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    private void cargarTabla(){
        try {
            mtady.setGrafo(ed.getGrafoTelefonia());
            mtady.fireTableDataChanged();
            JTableady.setModel(mtady);
            JTableady.updateUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiar(){
        
        try {
            UtilesEscuelaVista.cargaComboTelefonia(cbxOrigen);
            UtilesEscuelaVista.cargaComboTelefonia(cbxDestino);
            UtilesEscuelaVista.cargaComboAlgortimo(cbxAlgoritmo);
            UtilesEscuelaVista.cargaComboRecorrido(cbxRecorrido);
            cargarTabla();

        } catch (Exception e) {
        }
    }
    
    private void mostrarGrafo(){
        try {
            DibujarGrafo dg = new DibujarGrafo();
            dg.crearArchivo(ed.getGrafoTelefonia());
            
            String dir = Utilidades.getDirProject();
            if(Utilidades.getOS().equalsIgnoreCase("Windows 10")){
                
                Utilidades.abrirNavegadorPredeterminadoWindows(dir + File.separatorChar+"d3"+File.separatorChar+"grafo.html");
                
                //Utilidades.abrirNavegadorPredeterminadoWindows("d3/grafo.html");
            
            }else{
                //Utilidades.abrirNavegadorPredeterminadoWindows("d3/grafo.html");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarMapa(){
        try {
            UtilesEscuelaVista.crearMapaTelefonia(ed.getGrafoTelefonia());
            String dir = Utilidades.getDirProject();
            
            if(Utilidades.getOS().equalsIgnoreCase("Windows 10")){
                
                Utilidades.abrirNavegadorPredeterminadoWindows(dir + File.separatorChar+"mapas"+File.separatorChar+"index.html");
                //Utilidades.abrirNavegadorPredeterminadoWindows("mapas/index.html");
            
            }else{
                //Utilidades.abrirNavegadorPredeterminadoWindows("mapas/index.html");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void camino() throws Exception{
        if(ed.getGrafoTelefonia()!= null){
            Integer posO = cbxOrigen.getSelectedIndex() + 1; //Vertices comienzan desde 1 y no desde 0
            System.out.println(posO);
            Integer posD = cbxDestino.getSelectedIndex() + 1;
            System.out.println(posD);
            
            HashMap<String, LinkedList> mapa = ed.getGrafoTelefonia().camino(posO, posD);
                if(mapa.isEmpty()){
                    System.out.println("Mapa esta vacio");
                    JOptionPane.showMessageDialog(null, "No existe camino", "ERROR", JOptionPane.ERROR_MESSAGE); 
                }else{
                    LinkedList<Integer> caminos = mapa.get("camino");
                    for(int i = 0; i < caminos.getSize(); i++){
                        Integer v = caminos.get(i);
                        System.out.println(ed.getGrafoTelefonia().obtenerEtiqueta(v));
                    }
                }
        }else{
              JOptionPane.showMessageDialog(null, "Grafo Nulo", "ERROR", JOptionPane.ERROR_MESSAGE);  
        }
    }
    

    

    private void adyacenciaRandom() {
        try {
            Integer posO = cbxOrigen.getSelectedIndex();
            if (posO != -1) { // Verificar si se seleccionó un índice válido en cbxOrigen
                Random rand = new Random();
                int posD1 = rand.nextInt(cbxDestino.getItemCount()); // Obtener un índice aleatorio para el primer destino
                int posD2 = rand.nextInt(cbxDestino.getItemCount()); // Obtener un índice aleatorio para el segundo destino

                // Evitar que posD1 y posD2 sean iguales a posO
                while (posD1 == posO || posD2 == posO || posD1 == posD2) {
                    posD1 = rand.nextInt(cbxDestino.getItemCount());
                    posD2 = rand.nextInt(cbxDestino.getItemCount());
                }
                
                LinkedList<Telefonia> telefonos = ed.listAll();
                Telefonia telefoniaD1 = telefonos.get(posD1);
                Telefonia telefoniaD2 = telefonos.get(posD2);
                
                Double peso1 = UtilesEscuelaVista.distanciaTelefonias(UtilesEscuelaVista.getComboTelefonia(cbxOrigen), telefoniaD1);
                Double peso2 = UtilesEscuelaVista.distanciaTelefonias(UtilesEscuelaVista.getComboTelefonia(cbxOrigen), telefoniaD2);
                
                ed.getGrafoTelefonia().InsertarAristaE(ed.getTelefonos().get(posO), ed.getTelefonos().get(posD1), peso1);
                ed.getGrafoTelefonia().InsertarAristaE(ed.getTelefonos().get(posO), ed.getTelefonos().get(posD2), peso2);
                
                JOptionPane.showMessageDialog(null, "Adyacencia agregada", "OK", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor selecciona un origen válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    

    private void mostrarAlgortimos() throws EmptyException, Exception {
        if ("Dijkstra".equals(cbxAlgoritmo.getSelectedItem().toString())) {
            System.out.println("Se ha ejecutado el algoritmo Dijkstra");
            HashMap<Integer, Double> resultadoDijkstra = ed.getGrafoTelefonia().dijkstraNew(cbxOrigen.getSelectedIndex() + 1);
            HashMap<Integer, String> etiquetas = new HashMap<>();

            // Obtener las etiquetas de los vértices
            for (Integer i = 0; i < cbxOrigen.getItemCount(); i++) {
                etiquetas.put(i + 1, cbxOrigen.getItemAt(i).toString());
            }

            // Construir la cadena formateada para mostrar el resultado
            StringBuilder resultadoFormateado = new StringBuilder("{\n");
            for (Integer key : resultadoDijkstra.keySet()) {
                String etiqueta = etiquetas.get(key);
                resultadoFormateado.append("La ruta desde ").append(cbxOrigen.getSelectedItem().toString()).append(" hasta ").append(etiqueta).append(" tiene un peso total de = ").append(resultadoDijkstra.get(key)).append(",\n");
            }
            resultadoFormateado.append("}");

            // Mostrar el resultado formateado en un cuadro de diálogo
            JOptionPane.showMessageDialog(null, resultadoFormateado.toString());
        } else if ("Floyd".equals(cbxAlgoritmo.getSelectedItem().toString())) {
            System.out.println("Se ha ejecutado el algoritmo Floyd");
            JOptionPane.showMessageDialog(null, ed.getGrafoTelefonia().rutaCortaFloyd(cbxOrigen.getSelectedIndex() + 1, cbxDestino.getSelectedIndex() + 1));
        }
    }
    
    
//    private void mostrarRecorridos(){
//        if("Anchura".equals(cbxRecorrido.getSelectedItem().toString())){
//            System.out.println("Se ha ejecutado el recorrido de anchura");
//            JOptionPane.showMessageDialog(null, ed.getGrafoTelefonia().anchura(cbxOrigen.getSelectedItem()));
//            
//            
//            
//        }else if("Profundidad".equals(cbxRecorrido.getSelectedItem().toString())){
//            System.out.println("Se ha ejecutado el recorrido de Profunidad");
//        }
//        
//        
//        
//    }
//    
    private void mostrarRecorridos() throws Exception {
        if ("Anchura".equals(cbxRecorrido.getSelectedItem().toString())) {
            try {
                System.out.println("Se ha ejecutado el recorrido de anchura");
                System.out.println("Usted ha seleccionado el veritice: " + cbxOrigen.getSelectedItem().toString());
                ed.getGrafoTelefonia().anchuraDir(cbxOrigen.getSelectedIndex()+1);
                //JOptionPane.showMessageDialog(null, ed.getGrafoTelefonia().anchuraDir(cbxOrigen.getSelectedIndex()));
            } catch (Exception e) {
            }

        } else if ("Profundidad".equals(cbxRecorrido.getSelectedItem().toString())) {
            System.out.println("Se ha ejecutado el recorrido de Profundidad");
            //JOptionPane.showMessageDialog(null, ed.getGrafoTelefonia().profundidadNew(1));

        }
    }
    
    private void mostrarRecorridos2() {
        int inicio = cbxOrigen.getSelectedIndex() + 1;
        int fina_l = cbxDestino.getSelectedIndex() + 1;
        
        try {
            if ("anchura".equals(cbxRecorrido.getSelectedItem().toString().toLowerCase())) {
                //ed.getGrafoTelefonia().bfs(inicio);
                //System.out.println("adasd"+ed.getGrafoTelefonia().bfs(inicio));
                
            } else if ("profundidad".equals(cbxRecorrido.getSelectedItem().toString().toLowerCase())) {
               // ed.getTelefonia().dfs(inicio);
            } else {
                throw new UnsupportedOperationException("Método de ordenamiento no reconocido");
            }
        } catch (Exception ex) {
            //Logger.getLogger(FrmMapaDirigido.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    
    private void adycencia()  {
        try {
            Integer posO = cbxOrigen.getSelectedIndex();
            Integer posD = cbxDestino.getSelectedIndex();
            if (posO != posD) {
                System.out.println(ed.getTelefonos().get(posO).hashCode());
                Double peso = UtilesEscuelaVista.distanciaTelefonias(UtilesEscuelaVista.getComboTelefonia(cbxOrigen), UtilesEscuelaVista.getComboTelefonia(cbxDestino));
                System.out.println("PESO " + peso);
                ed.getGrafoTelefonia().InsertarAristaE(ed.getTelefonos().get(posO), ed.getTelefonos().get(posD), peso);
                JOptionPane.showMessageDialog(null, "Adycencia agregada ", "ok", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se puede agregar la misma adycencia ", "Error", JOptionPane.ERROR_MESSAGE);

            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            JOptionPane.showMessageDialog(null, "No se puede agregar la misma adycencia ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new org.edisoncor.gui.panel.Panel();
        panelRect1 = new org.edisoncor.gui.panel.PanelRect();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTableady = new javax.swing.JTable();
        panel2 = new org.edisoncor.gui.panel.Panel();
        labelRect1 = new org.edisoncor.gui.label.LabelRect();
        labelRect2 = new org.edisoncor.gui.label.LabelRect();
        cbxDestino = new org.edisoncor.gui.comboBox.ComboBoxRound();
        cbxOrigen = new org.edisoncor.gui.comboBox.ComboBoxRound();
        buttonRect1 = new org.edisoncor.gui.button.ButtonRect();
        labelRound1 = new org.edisoncor.gui.label.LabelRound();
        buttonRect2 = new org.edisoncor.gui.button.ButtonRect();
        buttonRect3 = new org.edisoncor.gui.button.ButtonRect();
        buttonRect4 = new org.edisoncor.gui.button.ButtonRect();
        buttonRect5 = new org.edisoncor.gui.button.ButtonRect();
        buttonRect6 = new org.edisoncor.gui.button.ButtonRect();
        labelRect3 = new org.edisoncor.gui.label.LabelRect();
        labelRect4 = new org.edisoncor.gui.label.LabelRect();
        cbxAlgoritmo = new org.edisoncor.gui.comboBox.ComboBoxRect();
        btnMostrarRuta = new org.edisoncor.gui.button.ButtonRect();
        cbxRecorrido = new org.edisoncor.gui.comboBox.ComboBoxRect();
        btnMostrarRuta1 = new org.edisoncor.gui.button.ButtonRect();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel1.setColorPrimario(new java.awt.Color(255, 255, 255));
        panel1.setColorSecundario(new java.awt.Color(255, 204, 102));
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRect1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JTableady.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(JTableady);

        panelRect1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 890, 260));

        panel1.add(panelRect1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 910, 200));

        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Construir Grafo"));
        panel2.setColorPrimario(new java.awt.Color(255, 255, 255));
        panel2.setColorSecundario(new java.awt.Color(255, 204, 153));
        panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelRect1.setText("Destino:");
        panel2.add(labelRect1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 80, -1));

        labelRect2.setText("ORIGEN:");
        panel2.add(labelRect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 80, -1));
        panel2.add(cbxDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 450, -1));

        cbxOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxOrigenActionPerformed(evt);
            }
        });
        panel2.add(cbxOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 450, -1));

        buttonRect1.setText("Adyacencia");
        buttonRect1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRect1ActionPerformed(evt);
            }
        });
        panel2.add(buttonRect1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 80, -1, -1));

        panel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 890, 140));

        labelRound1.setText("UBICACIONES DE TELEFONIAS");
        panel1.add(labelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 10, 890, 30));

        buttonRect2.setText("VER MAPA");
        buttonRect2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRect2ActionPerformed(evt);
            }
        });
        panel1.add(buttonRect2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, -1));

        buttonRect3.setText("VER GRAFO");
        buttonRect3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRect3ActionPerformed(evt);
            }
        });
        panel1.add(buttonRect3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 60, -1, -1));

        buttonRect4.setText("Guardar");
        buttonRect4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRect4ActionPerformed(evt);
            }
        });
        panel1.add(buttonRect4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 600, 120, 40));

        buttonRect5.setText("Camino");
        buttonRect5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRect5ActionPerformed(evt);
            }
        });
        panel1.add(buttonRect5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 600, 120, 40));

        buttonRect6.setText("Cargar");
        buttonRect6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRect6ActionPerformed(evt);
            }
        });
        panel1.add(buttonRect6, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 600, 120, 40));

        labelRect3.setText("Mostrar Ruta Minima:");
        panel1.add(labelRect3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 160, 30));

        labelRect4.setText("Trabajar:");
        panel1.add(labelRect4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 160, 30));
        panel1.add(cbxAlgoritmo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 200, 30));

        btnMostrarRuta.setText("Buscar");
        btnMostrarRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarRutaActionPerformed(evt);
            }
        });
        panel1.add(btnMostrarRuta, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 320, 200, 30));
        panel1.add(cbxRecorrido, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 270, 200, 30));

        btnMostrarRuta1.setText("Ruta");
        btnMostrarRuta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarRuta1ActionPerformed(evt);
            }
        });
        panel1.add(btnMostrarRuta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 200, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRect6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRect6ActionPerformed
        cargarGrafo();
    }//GEN-LAST:event_buttonRect6ActionPerformed

    private void buttonRect1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRect1ActionPerformed
        adycencia();
    }//GEN-LAST:event_buttonRect1ActionPerformed

    private void buttonRect5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRect5ActionPerformed
       try {
            camino();
        } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);  

        }
    }//GEN-LAST:event_buttonRect5ActionPerformed

    private void buttonRect2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRect2ActionPerformed
        mostrarMapa();
    }//GEN-LAST:event_buttonRect2ActionPerformed

    private void buttonRect3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRect3ActionPerformed
        mostrarGrafo();
    }//GEN-LAST:event_buttonRect3ActionPerformed

    private void buttonRect4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRect4ActionPerformed
        guardarGrafo();
    }//GEN-LAST:event_buttonRect4ActionPerformed

    private void cbxOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxOrigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxOrigenActionPerformed

    private void btnMostrarRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarRutaActionPerformed
        try {
             mostrarRecorridos();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnMostrarRutaActionPerformed

    private void btnMostrarRuta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarRuta1ActionPerformed
        try {
             mostrarAlgortimos();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnMostrarRuta1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMapaD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMapaD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMapaD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMapaD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmMapaD dialog = new FrmMapaD(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTableady;
    private org.edisoncor.gui.button.ButtonRect btnMostrarRuta;
    private org.edisoncor.gui.button.ButtonRect btnMostrarRuta1;
    private org.edisoncor.gui.button.ButtonRect buttonRect1;
    private org.edisoncor.gui.button.ButtonRect buttonRect2;
    private org.edisoncor.gui.button.ButtonRect buttonRect3;
    private org.edisoncor.gui.button.ButtonRect buttonRect4;
    private org.edisoncor.gui.button.ButtonRect buttonRect5;
    private org.edisoncor.gui.button.ButtonRect buttonRect6;
    private org.edisoncor.gui.comboBox.ComboBoxRect cbxAlgoritmo;
    private org.edisoncor.gui.comboBox.ComboBoxRound cbxDestino;
    private org.edisoncor.gui.comboBox.ComboBoxRound cbxOrigen;
    private org.edisoncor.gui.comboBox.ComboBoxRect cbxRecorrido;
    private javax.swing.JScrollPane jScrollPane1;
    private org.edisoncor.gui.label.LabelRect labelRect1;
    private org.edisoncor.gui.label.LabelRect labelRect2;
    private org.edisoncor.gui.label.LabelRect labelRect3;
    private org.edisoncor.gui.label.LabelRect labelRect4;
    private org.edisoncor.gui.label.LabelRound labelRound1;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.Panel panel2;
    private org.edisoncor.gui.panel.PanelRect panelRect1;
    // End of variables declaration//GEN-END:variables
}
