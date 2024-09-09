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
 * Clase de frame principal, donde se miran las tareas y las distintas opciones.
 */
public class ListaTareasFrame extends javax.swing.JFrame {
    private AdminTareas adminTareas;
    private JRadioButton selectedRadioButton = null;
    private ButtonGroup buttonGroup = new ButtonGroup();
    String filtro;

    /**
     * Constructor por defecto
     */
    public ListaTareasFrame() {
        adminTareas = new AdminTareas();
        initComponents();
        setElementosVisibles();
        filtro = "Todas";
    }

    /**
     * Constructor con AdminTareas
     * @param adminTareas Manejador de tareas
     */
    public ListaTareasFrame(AdminTareas adminTareas) {
        this.adminTareas = adminTareas;
        initComponents();
        setElementosVisibles();
    }

    /**
     * Configura la visibilidad de los elementos iniciales en la interfaz
     */
    private void setElementosVisibles() {
        lblTareasCompletadas.setVisible(false);
        lblTareasPendientes.setVisible(false);
        eliminarBtn.setVisible(false);
        editarBtn.setVisible(false);
        filtroCbox.setVisible(false);
    }

    /**
     * Actualiza la lista de tareas según el filtro proporcionado
     * @param adminTareas Manejador de tareas
     * @param filtro Filtro para ver ciertas tareas
     */
    public void actualizar(AdminTareas adminTareas, String filtro) {
        this.filtro = filtro;
        jPanel1.removeAll(); // Elimina todos los componentes del panel
        
        filtroCbox.setVisible(true); // Asegura que el combobox de filtro sea visible
        
        buttonGroup = new ButtonGroup(); // Reinicia el grupo de botones

        jPanel1.setLayout(null); // Usa un layout absoluto para el panel
        jPanel1.add(filtroCbox); // Agrega el combobox al panel

        // Posiciones iniciales para las tareas pendientes y completadas
        int yPositionPendientes = lblTareasPendientes.getY() + lblTareasPendientes.getHeight() + 10;
        int yPositionCompletadas = lblTareasCompletadas.getY() + lblTareasCompletadas.getHeight() + 10;

        List<Tarea> tareas = adminTareas.getTareas(); // Obtiene la lista de tareas

        // Añade las etiquetas correspondientes y las muestra según el estado de las tareas
        for (Tarea tarea : tareas) {
            if (!tarea.isEstaCompletada()) {
                jPanel1.add(lblTareasPendientes);
                lblTareasPendientes.setVisible(true);
            } else {
                jPanel1.add(lblTareasCompletadas);
                lblTareasCompletadas.setVisible(true);
            }
        }

        // Oculta las etiquetas según el filtro seleccionado
        if (filtro.equals("Pendientes")) {
            lblTareasCompletadas.setVisible(false);
        } else if (filtro.equals("Completadas")) {
            lblTareasPendientes.setVisible(false);
        }

        // Crea y posiciona los botones de radio para cada tarea
        for (Tarea tarea : tareas) {
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

            // Detecta la tecla ENTER para marcar la tarea como completada o pendiente
            radioButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (radioButton.isSelected() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (tarea.isEstaCompletada()) {
                            tarea.setCompletada(false); // Cambia el estado a pendiente
                        } else {
                            tarea.setCompletada(true); // Cambia el estado a completada
                        }
                        actualizar(adminTareas, filtro); // Actualiza la lista según el filtro
                    }
                }
            });

            // Añade la tarea al panel según su estado
            if (tarea.isEstaCompletada()) {
                radioButton.setBounds(lblTareasCompletadas.getX(), yPositionCompletadas, 300, 30);
                yPositionCompletadas += 40; // Incrementa la posición vertical para la siguiente tarea completada
                if (filtro.equals("Pendientes")) {
                    radioButton.setVisible(false); // Oculta si el filtro es "Pendientes"
                }
            } else {
                radioButton.setBounds(lblTareasPendientes.getX(), yPositionPendientes, 300, 30);
                yPositionPendientes += 40; // Incrementa la posición vertical para la siguiente tarea pendiente
                if (filtro.equals("Completadas")) {
                    radioButton.setVisible(false); // Oculta si el filtro es "Completadas"
                }
            }
            buttonGroup.add(radioButton); // Añade el botón al grupo
            jPanel1.add(radioButton); // Añade el botón al panel
        }

        // Configura el listener para el botón de eliminar
        eliminarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRadioButton != null) {
                    String descripcionSeleccionada = selectedRadioButton.getText();
                    Tarea tareaAEliminar = null;

                    // Busca la tarea a eliminar por su descripción
                    for (Tarea tarea : tareas) {
                        if (tarea.getDescripcion().equals(descripcionSeleccionada)) {
                            tareaAEliminar = tarea;
                            break;
                        }
                    }

                    if (tareaAEliminar != null) {
                        adminTareas.eliminarTarea(tareaAEliminar.getDescripcion());
                        actualizar(adminTareas, filtro); // Actualiza la lista después de eliminar la tarea
                    }
                }
            }
        });

        // Configura el listener para el botón de editar
        editarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedRadioButton != null) {
                    String descripcionSeleccionada = selectedRadioButton.getText();
                    
                    Tarea tareaAEditar = null;

                    // Busca la tarea a eliminar por su descripción
                    for (Tarea tarea : tareas) {
                        if (tarea.getDescripcion().equals(descripcionSeleccionada)) {
                            tareaAEditar = tarea;
                            break;
                        }
                    }

                    // Evita abrir múltiples instancias de la ventana de edición
                    if (!isEditingWindowOpen() && tareaAEditar != null) {
                        EscribirTareaFrame editarTarea = new EscribirTareaFrame(adminTareas, ListaTareasFrame.this, descripcionSeleccionada, "Pendientes");
                        editarTarea.setVisible(true);
                        
                    }
                }
            }
        });

        // Configura la posición de los botones y los agrega al panel
        botonAgregar.setBounds(botonAgregar.getX(), botonAgregar.getY(), botonAgregar.getWidth(), botonAgregar.getHeight());
        jPanel1.add(botonAgregar);

        eliminarBtn.setBounds(eliminarBtn.getX(), eliminarBtn.getY(), eliminarBtn.getWidth(), eliminarBtn.getHeight());
        jPanel1.add(eliminarBtn);

        editarBtn.setBounds(editarBtn.getX(), editarBtn.getY(), editarBtn.getWidth(), editarBtn.getHeight());
        jPanel1.add(editarBtn);

        // Actualiza la interfaz de usuario
        revalidate();
        repaint();
    }

    /**
     * Verifica si hay una ventana de edición abierta
     * @return 
     */
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
        filtroCbox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botonAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton-agregar.png"))); // NOI18N
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

        editarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editarDibujo.png"))); // NOI18N
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

        filtroCbox.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        filtroCbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas las tareas", "Tareas pendientes", "Tareas completadas" }));
        filtroCbox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        filtroCbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtroCboxActionPerformed(evt);
            }
        });
        jPanel1.add(filtroCbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 210, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseClicked
        EscribirTareaFrame escribirTarea = new EscribirTareaFrame(adminTareas, this, filtro);
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

    private void filtroCboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtroCboxActionPerformed
        if (filtroCbox.getSelectedIndex() == 0) {
            actualizar(adminTareas, "Todas");
        } else if (filtroCbox.getSelectedIndex() == 1) {
            actualizar(adminTareas, "Pendientes");
        } else if (filtroCbox.getSelectedIndex() == 2) {
            actualizar(adminTareas, "Completadas");
        }
    }//GEN-LAST:event_filtroCboxActionPerformed

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
    private javax.swing.JComboBox<String> filtroCbox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblInstruccion;
    private javax.swing.JLabel lblTareasCompletadas;
    private javax.swing.JLabel lblTareasPendientes;
    // End of variables declaration//GEN-END:variables
}
