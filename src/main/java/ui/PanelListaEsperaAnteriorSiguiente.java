package ui;

import estructuras.ListaDobleCircular;
import modelos.Curso;
import modelos.Estudiante;
import persistencias.PersistenciaFachada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel para recorrer la lista de espera de un curso
 * mostrando todos los datos del estudiante en una tabla.
 */
public class PanelListaEsperaAnteriorSiguiente extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;
    private JTextField txtClaveCurso;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnSiguiente, btnAnterior, btnRegresar;

    private ListaDobleCircular<Estudiante> listaEspera;
    private ListaDobleCircular<Estudiante>.Nodo actual;

    public PanelListaEsperaAnteriorSiguiente(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TÍTULO
        JLabel titulo = new JLabel("LISTA DE ESPERA - ANTERIOR / SIGUIENTE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // PANEL DE INGRESO DE CURSO
        JPanel panelCurso = new JPanel(new FlowLayout());
        panelCurso.setBackground(Color.WHITE);
        panelCurso.add(new JLabel("Clave del curso:"));
        txtClaveCurso = new JTextField(10);
        panelCurso.add(txtClaveCurso);
        JButton btnCargar = new JButton("Cargar lista");
        panelCurso.add(btnCargar);
        add(panelCurso, BorderLayout.NORTH);

        // TABLA PARA MOSTRAR DATOS DEL ESTUDIANTE
        String columnas[] = {"Matrícula", "Nombre", "Teléfono", "Correo", "Dirección", "Promedio"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabla solo lectura
            }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // PANEL DE BOTONES
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);

        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");
        btnRegresar = new JButton("Regresar");

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnRegresar);

        add(panelBotones, BorderLayout.SOUTH);

        // EVENTOS
        btnCargar.addActionListener(e -> cargarListaEspera());
        btnSiguiente.addActionListener(e -> mostrarSiguiente());
        btnAnterior.addActionListener(e -> mostrarAnterior());
        btnRegresar.addActionListener(e ->
                frame.cambiarPanel(new MenuPrincipal(frame, sistema))
        );

        // Deshabilitar botones hasta cargar lista
        btnSiguiente.setEnabled(false);
        btnAnterior.setEnabled(false);
    }

    private void cargarListaEspera() {
        String clave = txtClaveCurso.getText().trim();
        Curso curso = sistema.buscarCursoPorId(clave);

        if (curso == null) {
            JOptionPane.showMessageDialog(this, "Curso no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        listaEspera = curso.getListaEspera();
        if (listaEspera.getCabeza() == null) {
            JOptionPane.showMessageDialog(this, "No hay estudiantes en la lista de espera", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            modelo.setRowCount(0);
            btnSiguiente.setEnabled(false);
            btnAnterior.setEnabled(false);
            return;
        }

        // Empezamos con el primer estudiante
        actual = listaEspera.getCabeza();
        mostrarEstudianteActual();

        btnSiguiente.setEnabled(true);
        btnAnterior.setEnabled(true);
    }

    private void mostrarSiguiente() {
        if (actual != null) {
            actual = actual.getSiguiente();
            mostrarEstudianteActual();
        }
    }

    private void mostrarAnterior() {
        if (actual != null) {
            actual = actual.getAnterior();
            mostrarEstudianteActual();
        }
    }

    private void mostrarEstudianteActual() {
        if (actual != null && actual.getDato() instanceof Estudiante) {
            Estudiante e = (Estudiante) actual.getDato();
            modelo.setRowCount(0); // limpiar tabla
            modelo.addRow(new Object[]{
                    e.getMatricula(),
                    e.getNombre(),
                    e.getTelefono(),
                    e.getCorreo(),
                    e.getDireccion(),
                    String.format("%.2f", e.promedio())
            });
        }
    }
}