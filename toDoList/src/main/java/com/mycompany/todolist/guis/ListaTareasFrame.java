/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.todolist.guis;

import com.mycompany.todolist.dominio.AdminTareas;
import com.mycompany.todolist.dominio.Tarea;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

/**
 *
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public class ListaTareasFrame extends javax.swing.JFrame {
    AdminTareas adminTareas;
    private JRadioButton selectedRadioButton = null;
    private ButtonGroup buttonGroup = new ButtonGroup();

    public ListaTareasFrame() {
        adminTareas = new AdminTareas();
        initComponents();
        setElementosVisibles();
    }

    public ListaTareasFrame(AdminTareas adminTareas) {
        this.adminTareas = adminTareas;
        initComponents();
        setElementosVisibles();
    }

    private void setElementosVisibles() {
        lblTareasCompletadas.setVisible(false);
        lblTareasPendientes.setVisible(false);
        eliminarBtn.setVisible(false);
        editarBtn.setVisible(false);
    }


    public void actualizar(AdminTareas adminTareas) {
        jPanel1.removeAll();
        buttonGroup = new ButtonGroup();

        jPanel1.setLayout(null);
        int yPositionPendientes = lblTareasPendientes.getY() + lblTareasPendientes.getHeight() + 10;
        int yPositionCompletadas = lblTareasCompletadas.getY() + lblTareasCompletadas.getHeight() + 10;

        List<Tarea> tareas = adminTareas.getTareas();
        for(Tarea tarea : tareas){
            if (!tarea.isEstaCompletada()){
                jPanel1.add(lblTareasPendientes);
                lblTareasPendientes.setVisible(true);
            } else {
                jPanel1.add(lblTareasCompletadas);
                lblTareasCompletadas.setVisible(true);
            }
        }
        
        for (Tarea tarea : tareas) {
            // Crear el radio button para la tarea
            JRadioButton radioButton = new JRadioButton(tarea.getDescripcion());

            // Acción al seleccionar el radio button
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedRadioButton = radioButton;
                    if (!tarea.isEstaCompletada()) {
                        eliminarBtn.setVisible(true);
                        editarBtn.setVisible(true);
                    } else {
                        eliminarBtn.setVisible(false);
                        editarBtn.setVisible(false);
                    }
                    
                }
            });

            // Detectar la tecla ENTER para marcar como completada
            radioButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (radioButton.isSelected() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (tarea.isEstaCompletada()){
                            tarea.setCompletada(false); // Cambiar estado a pendiente
                        } else {
                            tarea.setCompletada(true); // Cambiar estado a completada
                        }
                        
                        actualizar(adminTareas); // Volver a actualizar la lista
                    }
                }
            });

            // Añadir la tarea según su estado (pendiente o completada)
            if (tarea.isEstaCompletada()) {
                // Posicionar la tarea completada
                radioButton.setBounds(lblTareasCompletadas.getX(), yPositionCompletadas, 300, 30);
                yPositionCompletadas += 40; // Incrementar posición para la siguiente tarea completada
            } else {
                // Posicionar la tarea pendiente
                radioButton.setBounds(lblTareasPendientes.getX(), yPositionPendientes, 300, 30);
                yPositionPendientes += 40; // Incrementar posición para la siguiente tarea pendiente
            }

            buttonGroup.add(radioButton);
            jPanel1.add(radioButton);
        }

        // Listener para eliminar tarea
        eliminarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRadioButton != null) {
                    String descripcionSeleccionada = selectedRadioButton.getText();
                    Tarea tareaAEliminar = null;

                    // Buscar la tarea a eliminar por su descripción
                    for (Tarea tarea : tareas) {
                        if (tarea.getDescripcion().equals(descripcionSeleccionada)) {
                            tareaAEliminar = tarea;
                            break;
                        }
                    }

                    if (tareaAEliminar != null) {
                        adminTareas.eliminarTarea(tareaAEliminar.getDescripcion());
                        actualizar(adminTareas); // Volver a actualizar la lista
                    }
                }
            }
        });

        editarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedRadioButton != null) {
                    String descripcionSeleccionada = selectedRadioButton.getText();

                    // Evitar abrir múltiples instancias de la ventana de edición
                    if (!isEditingWindowOpen()) {
                        EscribirTareaFrame editarTarea = new EscribirTareaFrame(adminTareas, ListaTareasFrame.this, descripcionSeleccionada);
                        editarTarea.setVisible(true);

                        // Establecer comportamiento para cerrar la ventana al finalizar la edición
                        editarTarea.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                // Cuando se cierra la ventana, actualizar las tareas
                                actualizar(adminTareas);
                            }
                        });
                    }
                }
            }
        });
        
        
        
        botonAgregar.setBounds(botonAgregar.getX(), botonAgregar.getY(), botonAgregar.getWidth(), botonAgregar.getHeight());
        jPanel1.add(botonAgregar);

        eliminarBtn.setBounds(eliminarBtn.getX(), eliminarBtn.getY(), eliminarBtn.getWidth(), eliminarBtn.getHeight());
        jPanel1.add(eliminarBtn);

        editarBtn.setBounds(editarBtn.getX(), editarBtn.getY(), editarBtn.getWidth(), editarBtn.getHeight());
        jPanel1.add(editarBtn);

        revalidate();
        repaint();
        
    }
    
    private boolean isEditingWindowOpen() {
    for (Window window : Window.getWindows()) {
        if (window instanceof EscribirTareaFrame && window.isVisible()) {
            return true;
        }
    }
    return false;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        botonAgregar = new javax.swing.JLabel();
        lblTareasCompletadas = new javax.swing.JLabel();
        lblInstruccion = new javax.swing.JLabel();
        editarBtn = new javax.swing.JLabel();
        cerrarInstruccionBtn = new javax.swing.JButton();
        lblTareasPendientes = new javax.swing.JLabel();
        eliminarBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botonAgregar.setIcon(new javax.swing.ImageIcon("C:\\Users\\JoseH\\OneDrive\\Documentos\\NetBeansProjects\\toDoList\\src\\main\\java\\com\\mycompany\\todolist\\guis\\boton-agregar.png")); // NOI18N
        botonAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonAgregarMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonAgregarMousePressed(evt);
            }
        });
        jPanel1.add(botonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 400, -1, -1));

        lblTareasCompletadas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTareasCompletadas.setText("Tareas completadas");
        jPanel1.add(lblTareasCompletadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, -1, -1));

        lblInstruccion.setBackground(new java.awt.Color(255, 255, 255));
        lblInstruccion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblInstruccion.setText("Enter para marcar tarea como completada");
        jPanel1.add(lblInstruccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, -1, -1));

        editarBtn.setIcon(new javax.swing.ImageIcon("C:/Users/JoseH/OneDrive/Documentos/NetBeansProjects/toDoList/src/main/java/com/mycompany/todolist/guis/Captura de pantalla 2024-09-07 021232.png")); // NOI18N
        editarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editarBtnMouseClicked(evt);
            }
        });
        jPanel1.add(editarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 400, 60, 70));

        cerrarInstruccionBtn.setBackground(new java.awt.Color(255, 0, 0));
        cerrarInstruccionBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cerrarInstruccionBtn.setText("X");
        cerrarInstruccionBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cerrarInstruccionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarInstruccionBtnActionPerformed(evt);
            }
        });
        jPanel1.add(cerrarInstruccionBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 20, 20));

        lblTareasPendientes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTareasPendientes.setText("Tareas pendientes");
        jPanel1.add(lblTareasPendientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        eliminarBtn.setBackground(new java.awt.Color(255, 0, 0));
        eliminarBtn.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        eliminarBtn.setText("X");
        eliminarBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        eliminarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarBtnActionPerformed(evt);
            }
        });
        jPanel1.add(eliminarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 410, 50, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseClicked
        EscribirTareaFrame escribirTarea = new EscribirTareaFrame(adminTareas, this);
        escribirTarea.setVisible(true);
    }//GEN-LAST:event_botonAgregarMouseClicked

    private void botonAgregarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAgregarMousePressed

    private void cerrarInstruccionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarInstruccionBtnActionPerformed
        cerrarInstruccionBtn.setVisible(false);
        lblInstruccion.setVisible(false);
    }//GEN-LAST:event_cerrarInstruccionBtnActionPerformed

    private void editarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editarBtnMouseClicked
        
    }//GEN-LAST:event_editarBtnMouseClicked

    private void eliminarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eliminarBtnActionPerformed

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
            java.util.logging.Logger.getLogger(ListaTareasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListaTareasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListaTareasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaTareasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListaTareasFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botonAgregar;
    private javax.swing.JButton cerrarInstruccionBtn;
    private javax.swing.JLabel editarBtn;
    private javax.swing.JButton eliminarBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblInstruccion;
    private javax.swing.JLabel lblTareasCompletadas;
    private javax.swing.JLabel lblTareasPendientes;
    // End of variables declaration//GEN-END:variables
}
