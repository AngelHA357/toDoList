/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.todolist.guis;

import com.mycompany.todolist.conexion.IConexion;
import com.mycompany.todolist.dominio.AdminTareas;
import com.mycompany.todolist.dominio.Tarea;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private IConexion conexion;
    private AdminTareas adminTareas;
    private JRadioButton selectedRadioButton = null;
    private ButtonGroup buttonGroup = new ButtonGroup();
    String filtro;
    int yPositionPendientes, yPositionCompletadas;

    /**
     * Constructor por defecto
     * @param conexion Conexion con la base de datos
     */
    public ListaTareasFrame(IConexion conexion) {
        adminTareas = new AdminTareas(conexion);
        initComponents();
        filtro = "Todas";
        filtroCbox.setSelectedIndex(0);
        actualizar(adminTareas);
    }

    /**
     * Constructor con AdminTareas
     * @param adminTareas Manejador de tareas
     */
    public ListaTareasFrame(AdminTareas adminTareas) {
        this.adminTareas = adminTareas;
        initComponents();
    }

    /**
     * Actualiza el frame después de ejecutar alguna acción
     *
     * @param adminTareas Manejador de tareas
     */
    public void actualizar(AdminTareas adminTareas) {
        jPanel1.revalidate();
        jPanel1.repaint();
        jPanel1.removeAll();
        filtroCbox.setVisible(true);

        buttonGroup = new ButtonGroup();
        selectedRadioButton = null;

        jPanel1.setLayout(null);
        jPanel1.add(filtroCbox);
        jPanel1.add(lblTareasCompletadas);
        jPanel1.add(lblTareasPendientes);

        // Obtener las tareas según el filtro
        List<Tarea> tareas;
        if (filtro.equals("Pendientes")) {
            lblTareasCompletadas.setVisible(false);
            lblTareasPendientes.setVisible(true);
            tareas = adminTareas.obtenerTareasPendientes();
        } else if (filtro.equals("Completadas")) {
            lblTareasPendientes.setVisible(false);
            lblTareasCompletadas.setVisible(true);
            tareas = adminTareas.obtenerTareasCompletadas();
        } else {
            lblTareasPendientes.setVisible(true);
            lblTareasCompletadas.setVisible(true);
            tareas = adminTareas.getTareas();
        }

        if (tareas.isEmpty()){
            jPanel1.add(cerrarInstruccionBtn);
            jPanel1.add(lblInstruccion);
//            cerrarInstruccionBtn.setVisible(true);
//            lblInstruccion.setVisible(true);
        }
        
        // Posiciones iniciales para tareas
        yPositionPendientes = lblTareasPendientes.getY() + lblTareasPendientes.getHeight() + 10;
        yPositionCompletadas = lblTareasCompletadas.getY() + lblTareasCompletadas.getHeight() + 10;

        for (Tarea tarea : tareas) {
            JRadioButton radioButton = new JRadioButton(tarea.getDescripcion());
            radioButton.addActionListener(e -> {
                selectedRadioButton = radioButton;
                boolean isCompletada = tarea.getEstaCompletada();
                eliminarBtn.setVisible(!isCompletada);
                editarBtn.setVisible(!isCompletada);
            });

            radioButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (radioButton.isSelected() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        tarea.setEstaCompletada(!tarea.getEstaCompletada());
                        adminTareas.cambiarEstadoTarea(tarea.getDescripcion());
                        actualizar(adminTareas);
                    }
                }
            });

            // Posicionar la tarea
            if (tarea.getEstaCompletada()) {
                if (filtro.equals("Completadas") || filtro.equals("Todas")) {
                    radioButton.setBounds(lblTareasCompletadas.getX(), yPositionCompletadas, 300, 30);
                    yPositionCompletadas += 40;
                } else {
                    radioButton.setVisible(false); // Ocultar si el filtro es "Pendientes"
                }
            } else {
                if (filtro.equals("Pendientes") || filtro.equals("Todas")) {
                    radioButton.setBounds(lblTareasPendientes.getX(), yPositionPendientes, 300, 30);
                    yPositionPendientes += 40;
                } else {
                    radioButton.setVisible(false); // Ocultar si el filtro es "Completadas"
                }
            }

            buttonGroup.add(radioButton);
            jPanel1.add(radioButton);
        }

        // Listener para eliminar tarea
        eliminarBtn.addActionListener(e -> {
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
        });

        // Listener para editar tarea
        editarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedRadioButton != null) {
                    String descripcionSeleccionada = selectedRadioButton.getText();

                    Tarea tareaAEditar = null;

                    // Buscar la tarea a editar por su descripción
                    for (Tarea tarea : tareas) {
                        if (tarea.getDescripcion().equals(descripcionSeleccionada)) {
                            tareaAEditar = tarea;
                            break;
                        }
                    }

                    // Evitar abrir múltiples instancias de la ventana de edición
                    if (!isEditingWindowOpen() && tareaAEditar != null) {
                        EscribirTareaFrame editarTarea = new EscribirTareaFrame(adminTareas, ListaTareasFrame.this, descripcionSeleccionada, filtro);
                        editarTarea.setVisible(true);
                    }
                }
            }
        });

        // Ajustar la posición y añadir los botones
        botonAgregar.setBounds(botonAgregar.getX(), botonAgregar.getY(), botonAgregar.getWidth(), botonAgregar.getHeight());
        jPanel1.add(botonAgregar);

        eliminarBtn.setBounds(eliminarBtn.getX(), eliminarBtn.getY(), eliminarBtn.getWidth(), eliminarBtn.getHeight());
        jPanel1.add(eliminarBtn);

        editarBtn.setBounds(editarBtn.getX(), editarBtn.getY(), editarBtn.getWidth(), editarBtn.getHeight());
        jPanel1.add(editarBtn);

        jPanel1.revalidate();
        jPanel1.repaint();
    }

    /**
     * Verifica si hay una ventana de edición abierta
     *
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
        lblInstruccion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblInstruccion.setText("Enter para marcar tarea como completada");
        jPanel1.add(lblInstruccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, -1, -1));

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
        jPanel1.add(cerrarInstruccionBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 20, 20));

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
        actualizar(adminTareas);
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
            filtro = "Todas";
            actualizar(adminTareas);
        } else if (filtroCbox.getSelectedIndex() == 1) {
            filtro = "Pendientes";
            actualizar(adminTareas);
        } else if (filtroCbox.getSelectedIndex() == 2) {
            filtro = "Completadas";
            actualizar(adminTareas); 
        }
    }//GEN-LAST:event_filtroCboxActionPerformed



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
