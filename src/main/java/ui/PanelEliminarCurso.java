package ui;

import estructuras.ListaSimple;
import estructuras.NodoSimple;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelos.Curso;
import persistencias.PersistenciaFachada;

/**
 * Clase que implementa un panel para eliminar un curso
 * 
 * @author Paulina, Alec, Sarah
 */
public class PanelEliminarCurso extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;

    private JTextField txtIdCurso;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public PanelEliminarCurso(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TÍTULO
        JLabel titulo = new JLabel("ELIMINAR CURSO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // PANEL DE BUSQUEDA
        JPanel panelBusqueda = new JPanel(new GridLayout(1, 3, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
        panelBusqueda.setBackground(Color.WHITE);

        txtIdCurso = new JTextField();

        JButton btnBuscar = new JButton("Buscar");
        JButton btnMostrarTodos = new JButton("Mostrar todos");

        panelBusqueda.add(new JLabel("ID del Curso:"));
        panelBusqueda.add(txtIdCurso);
        panelBusqueda.add(btnBuscar);

        add(panelBusqueda, BorderLayout.NORTH);

        // TABLA
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Créditos", "Profesor"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);

        // BOTONES ABAJO
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);

        JButton btnEliminar = new JButton("Eliminar Curso");
        JButton btnRegresar = new JButton("Regresar");

        panelBotones.add(btnMostrarTodos);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRegresar);

        add(panelBotones, BorderLayout.SOUTH);

        // ACCIONES
        btnBuscar.addActionListener(e -> buscarCurso());
        btnMostrarTodos.addActionListener(e -> mostrarTodos());
        btnEliminar.addActionListener(e -> eliminarCurso());
        btnRegresar.addActionListener(e -> frame.cambiarPanel(new MenuPrincipal(frame, sistema)));
    }

    private void buscarCurso() {
        String id = txtIdCurso.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa un ID de curso.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Curso c = sistema.buscarCursoPorId(id);

        modeloTabla.setRowCount(0); // limpiar tabla

        if (c == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró un curso con ese ID.",
                    "Sin resultados",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            modeloTabla.addRow(new Object[]{
                c.getClave(),
                c.getNombre(),});
        }
    }

    // MOSTRAR TODOS LOS CURSOS
    private void mostrarTodos() {
        modeloTabla.setRowCount(0);

        ListaSimple<Curso> lista = sistema.listarCursos();
        NodoSimple<Curso> nodo = lista.getInicio();

        while (nodo != null) {
            Curso c = nodo.getDato();
            modeloTabla.addRow(new Object[]{
                c.getClave(),
                c.getNombre()
            });
            nodo = nodo.getSiguiente();
        }

    }

    // ELIMINAR CURSO
    private void eliminarCurso() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un curso en la tabla.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = tabla.getValueAt(fila, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar el curso con ID " + id + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean exito = sistema.eliminarCurso(id);

        if (!exito) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo eliminar el curso.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Curso eliminado correctamente.");
            mostrarTodos(); // refrescar tabla
        }
    }
}