package ui;

import estructuras.ListaSimple;
import estructuras.NodoSimple;
import modelos.Estudiante;
import persistencias.PersistenciaFachada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase que implementa un panel para listar estudiantes inorder por promedio
 * 
 * @author Paulina, Alec, Sarah
 */
public class PanelListarEstudiantesPromedio extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;

    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelListarEstudiantesPromedio(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // PANEL SUPERIOR
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("LISTADO DE ESTUDIANTES POR PROMEDIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        panelTop.add(titulo, BorderLayout.NORTH);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelSuperior.setBackground(new Color(240, 240, 240));

        JButton btnRegresar = new JButton("Regresar");

        panelSuperior.add(btnRegresar);

        panelTop.add(panelSuperior, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);

        // TABLA 
        String columnas[] = {"Matrícula", "Nombre", "Promedio", "Teléfono",
                             "Correo", "Dirección", "Calificaciones"};

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // EVENTOS
        btnRegresar.addActionListener(e ->
                frame.cambiarPanel(new MenuPrincipal(frame, sistema))
        );

        // Cargar la tabla al inicio
        cargarTabla();
    }

    private void cargarTabla() {

        modelo.setRowCount(0);

        ListaSimple<Estudiante> lista = sistema.listarEstudiantesPorPromedio();

        NodoSimple<Estudiante> nodo = lista.getInicio();

        while (nodo != null) {
            Estudiante e = nodo.getDato();

            // Construir lista de calificaciones
            StringBuilder sbCalif = new StringBuilder();
            if (e.getCalificaciones() != null && e.getCalificaciones().tamanio() > 0) {
                for (int i = 0; i < e.getCalificaciones().tamanio(); i++) {
                    sbCalif.append(e.getCalificaciones().obtener(i)).append(" ");
                }
            } else {
                sbCalif.append("(sin calificaciones)");
            }

            modelo.addRow(new Object[]{
                e.getMatricula(),
                e.getNombre(),
                String.format("%.2f", e.promedio()),
                e.getTelefono(),
                e.getCorreo(),
                e.getDireccion(),
                sbCalif.toString()
            });

            nodo = nodo.getSiguiente();
        }
    }
}