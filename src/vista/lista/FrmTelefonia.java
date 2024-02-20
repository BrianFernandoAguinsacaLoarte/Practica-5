/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.lista;

import controlador.TDA.listas.LinkedList;
import controlador.grafo.telefono.TelefoniaDAO;
import controlador.util.Utilidades;
import java.io.File;
import java.util.UUID;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import vista.listas.tablas.ModeloTablaTelefonia;

/**
 *
 * @author Usuario iTC
 */
public class FrmTelefonia extends javax.swing.JDialog {

    
    private File logo;
    private File foto;
    private TelefoniaDAO ed = new TelefoniaDAO();
    private ModeloTablaTelefonia mte = new ModeloTablaTelefonia();
    
    
    public FrmTelefonia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        limpiar();
        
    }
    
    public FrmTelefonia() {
        initComponents();
        this.setLocationRelativeTo(null);
        limpiar();
    }
    
    
    private void filechosserLogo(){
        JFileChooser filechooser = new JFileChooser();
        
        filechooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("multimedia", "jpg", "png", "jpeg");
        filechooser.addChoosableFileFilter(filter);
        
        int i = filechooser.showOpenDialog(null);
        if(i == JFileChooser.APPROVE_OPTION){
            logo = filechooser.getSelectedFile();
            txtlogo.setText(logo.getName());
            System.out.println("Si escogio" + logo.getName());
        }
    }
    
    private void filechosserFoto(){
        JFileChooser filechooser = new JFileChooser();
        
        filechooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("multimedia", "jpg", "png", "jpeg");
        filechooser.addChoosableFileFilter(filter);
        
        int i = filechooser.showOpenDialog(null);
        if(i == JFileChooser.APPROVE_OPTION){
            foto = filechooser.getSelectedFile();
            txtfoto.setText(foto.getName());
            System.out.println("Si escogio" + foto.getName());
        }
    }
    
    
    private Boolean validar(){
        return  !txtNombre.getText().trim().isEmpty() &&
                !txtLatitud.getText().trim().isEmpty() &&
                !txtLongitud.getText().trim().isEmpty() &&
                foto != null && logo != null;
    }
    
    private void cargarTabla(){
        mte.setTelefonias(ed.getTelefonos());
        jTableEscuela.setModel(mte);
        jTableEscuela.updateUI();
    }
    
    private void limpiar(){
        foto = null;
        logo = null;
        txtNombre.setText("");
        txtfoto.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
        txtlogo.setText("");
        txtfoto.setText("");
        
        ed.setTelefonia(null);
        ed.setTelefonos(new LinkedList<>());
        cargarTabla();
        
    }
    
    private void guardar2(){
        if(validar()){
            try {
                
                String uuidL = UUID.randomUUID().toString();//Generar un codigo unico para las fotos
                String uuidF = UUID.randomUUID().toString();
                Utilidades.copiarArchivo(logo, new File("multimedia/"+uuidL+Utilidades.extension(logo.getName())));
                Utilidades.copiarArchivo(foto, new File("multimedia/"+uuidF+Utilidades.extension(foto.getName())));
                ed.getTelefonia().setLatitud(Double.parseDouble(txtLatitud.getText()));
                ed.getTelefonia().setLongitud(Double.parseDouble(txtLongitud.getText()));
                ed.getTelefonia().setNombre(txtNombre.getText().trim());
                ed.getTelefonia().setLogo(uuidL+ Utilidades.extension(logo.getName()));
                ed.getTelefonia().setFoto(uuidF+ Utilidades.extension(foto.getName()));
               
               if(ed.save()){
                   JOptionPane.showMessageDialog(null, "Telefonia Guardada", "OK", JOptionPane.ERROR_MESSAGE);
            
               }else{
                   JOptionPane.showMessageDialog(null, "Error algunos campos estan vacios", "ERROR", JOptionPane.ERROR_MESSAGE);
            
               }
               
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error no se puede convertir cadena a numero", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "Error algunos campos estan vacios", "ERROR", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
    private void guardar(){
        if(validar()){
            try {
                
                String uuidL = UUID.randomUUID().toString();//Generar un codigo unico para las fotos
                String uuidF = UUID.randomUUID().toString();
                Utilidades.copiarArchivo(logo, new File("multimedia/"+uuidL+Utilidades.extension(logo.getName())));
                Utilidades.copiarArchivo(foto, new File("multimedia/"+uuidF+Utilidades.extension(foto.getName())));
                ed.getTelefonia().setLatitud(Double.parseDouble(txtLatitud.getText()));
                ed.getTelefonia().setLongitud(Double.parseDouble(txtLongitud.getText()));
                ed.getTelefonia().setNombre(txtNombre.getText().trim());
                ed.getTelefonia().setLogo(uuidL+ Utilidades.extension(logo.getName()));
                ed.getTelefonia().setFoto(uuidF+ Utilidades.extension(foto.getName()));
                
                //Guardar y MOdificar
                if(ed.getTelefonia().getId() == null){
                   if(ed.save()){
                    limpiar();
                        JOptionPane.showMessageDialog(null, "Se ha guardado correctamente", 
                            "OK", JOptionPane.INFORMATION_MESSAGE);
                   
                    }else{
                        JOptionPane.showMessageDialog(null, "No se ha podido guardar", 
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    } 
                }else{
                    if(ed.update(ed.getIndex())){
                    limpiar();
                        JOptionPane.showMessageDialog(null, "Se ha editado correctamente", 
                            "OK", JOptionPane.INFORMATION_MESSAGE);
                   
                    }else{
                        JOptionPane.showMessageDialog(null, "No se ha podido editar", 
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    } 
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage(),"No se pudo guardar"+"Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Llene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void cargarVista(){
        
        ed.setIndex(jTableEscuela.getSelectedRow());
        
        if(ed.getIndex().intValue() < 0){
            JOptionPane.showMessageDialog(null, "Selecciona una fila", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            try {
                ed.setTelefonia(mte.getTelefonias().get(ed.getIndex()));
                txtNombre.setText(ed.getTelefonia().getNombre());
                txtLatitud.setText(ed.getTelefonia().getLatitud().toString());
                txtLongitud.setText(ed.getTelefonia().getLongitud().toString());
                txtlogo.setText(ed.getTelefonia().getLogo());
                txtfoto.setText(ed.getTelefonia().getFoto());
                
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtfoto = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtlogo = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtLatitud = new javax.swing.JTextField();
        txtLongitud = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEscuela = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btnCancelar2 = new javax.swing.JButton();

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Datos Telefonia"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel1.setText("Foto:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 70, -1));

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 190, -1));

        jLabel2.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel2.setText("Latitud:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 70, -1));

        txtfoto.setText("foto");
        txtfoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtfotoMouseClicked(evt);
            }
        });
        jPanel2.add(txtfoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 60, -1));

        jButton1.setText("Cargar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 110, 20));

        jLabel4.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel4.setText("Logo:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 70, -1));

        txtlogo.setText("logo");
        txtlogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtlogoMouseClicked(evt);
            }
        });
        jPanel2.add(txtlogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 60, -1));

        jButton2.setText("Cargar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 110, 20));

        jLabel6.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel6.setText("Nombre:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 70, 20));

        jLabel7.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel7.setText("Longitud:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, 70, -1));
        jPanel2.add(txtLatitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 150, -1));
        jPanel2.add(txtLongitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, 150, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setText("Registro de Telefonias");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 190, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 250));

        jTableEscuela.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableEscuela);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 870, 130));

        btnActualizar.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 540, 140, 40));

        btnCancelar1.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        btnCancelar1.setText("Cancelar");
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 540, 140, 40));

        btnLimpiar.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        btnLimpiar.setText("Seleccionar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 140, 40));

        btnguardar.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        btnguardar.setText("Guardar");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 540, 140, 40));

        btnCancelar2.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        btnCancelar2.setText("Mapa");
        btnCancelar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 540, 140, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        filechosserLogo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtlogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtlogoMouseClicked
        if(evt.getClickCount() >= 2)
            new FrmFoto(null, true, logo).setVisible(true);
    }//GEN-LAST:event_txtlogoMouseClicked

    private void txtfotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtfotoMouseClicked
        if(evt.getClickCount() >= 2)
            new FrmFoto(null, true, foto).setVisible(true);
    }//GEN-LAST:event_txtfotoMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        filechosserFoto();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        guardar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        limpiar();
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        cargarVista();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnCancelar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar2ActionPerformed
        FrmMapa newFrame = new FrmMapa();
        newFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelar2ActionPerformed

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
            java.util.logging.Logger.getLogger(FrmTelefonia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmTelefonia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmTelefonia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmTelefonia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmTelefonia dialog = new FrmTelefonia(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnCancelar2;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEscuela;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField txtLatitud;
    private javax.swing.JTextField txtLongitud;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JLabel txtfoto;
    private javax.swing.JLabel txtlogo;
    // End of variables declaration//GEN-END:variables
}
