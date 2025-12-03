package ui;

import estructuras.ListaSimple;
import estructuras.NodoSimple;
import modelos.Curso;
import modelos.Estudiante;
import persistencias.PersistenciaFachada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase que implementa un panel para mostrar inscritos en un curso en específico
 * 
 * @author Paulina, Alec, Sarah
 */
public class PanelMostrarInscritos extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtClaveCurso;

    public PanelMostrarInscritos(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TÍTULO
        JLabel titulo = new JLabel("ESTUDIANTES INSCRITOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // FORMULARIO
        JPanel form = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        form.setBackground(Color.WHITE);
        form.add(new JLabel("Clave del curso:"));
        txtClaveCurso = new JTextField(10);
        form.add(txtClaveCurso);
        JButton btnCargar = new JButton("Cargar");
        form.add(btnCargar);
        add(form, BorderLayout.NORTH);

        // TABLA 
        String columnas[] = {"Matrícula", "Nombre"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // BTN
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);
        JButton btnRegresar = new JButton("Regresar");
        panelBotones.add(btnRegresar);
        add(panelBotones, BorderLayout.SOUTH);

        // EVENTOS
        btnCargar.addActionListener(e -> cargarInscritos());
        btnRegresar.addActionListener(e ->
                frame.cambiarPanel(new MenuPrincipal(frame, sistema))
        );
    }

    private void cargarInscritos() {
        modelo.setRowCount(0); // limpiar tabla
        String clave = txtClaveCurso.getText().trim();
        Curso curso = sistema.buscarCursoPorId(clave);

        if (curso == null) {
            JOptionPane.showMessageDialog(this,
                    "Curso no encontrado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ListaSimple<Estudiante> inscritos = curso.getInscritos();
        NodoSimple<Estudiante> nodo = inscritos.getInicio();
        while (nodo != null) {
            Estudiante e = nodo.getDato();
            modelo.addRow(new Object[]{
                    e.getMatricula(),
                    e.getNombre()
            });
            nodo = nodo.getSiguiente();
        }
    }
}