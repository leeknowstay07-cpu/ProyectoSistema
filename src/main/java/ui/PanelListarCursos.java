package ui;

import estructuras.ListaSimple;
import estructuras.NodoSimple;
import modelos.Curso;
import persistencias.PersistenciaFachada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase que implementa un panel para lisar todos los cursos existentes
 * 
 * @author Paulina, Alec, Sarah
 */
public class PanelListarCursos extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;
    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelListarCursos(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TÍTULO
        JLabel titulo = new JLabel("LISTADO DE CURSOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // TABLA
        String columnas[] = {"Clave del Curso", "Nombre del Curso"};

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

        // Eventos
        btnRegresar.addActionListener(e ->
                frame.cambiarPanel(new MenuPrincipal(frame, sistema))
        );

        // Cargar automáticamente al abrir
        cargarCursos();
    }

    // MÉTODO PARA CARGAR CURSOS EN LA TABLA
    private void cargarCursos() {
        modelo.setRowCount(0); // limpiar tabla

        ListaSimple<Curso> lista = sistema.listarCursos();
        NodoSimple<Curso> nodo = lista.getInicio();

        while (nodo != null) {
            Curso c = nodo.getDato();

            modelo.addRow(new Object[]{
                    c.getClave(),
                    c.getNombre()
            });

            nodo = nodo.getSiguiente();
        }
    }
}