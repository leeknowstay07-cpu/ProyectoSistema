package ui;

import javax.swing.*;
import java.awt.*;
import persistencias.PersistenciaFachada;

/**
 * Clase que implementa el menú principal y sus llamadas a cada panel 
 * 
 * @author Paulina, Alec, Sarah
 */
public class MenuPrincipal extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;

    public MenuPrincipal(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("MENÚ PRINCIPAL", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // PANEL DE CONTENIDO SCROLLEABLE
        JPanel panelScrollable = new JPanel();
        panelScrollable.setLayout(new BorderLayout());
        panelScrollable.setBackground(Color.WHITE);

        // PANEL EN COLUMNA
        JPanel panelColumna = new JPanel();
        panelColumna.setLayout(new BoxLayout(panelColumna, BoxLayout.Y_AXIS));
        panelColumna.setBackground(Color.WHITE);
        panelColumna.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Función para agregar espacio vertical
        Runnable espacio = () -> panelColumna.add(Box.createVerticalStrut(10));

        // 1. ESTUDIANTES
        JLabel lbl1 = new JLabel("ESTUDIANTES");
        lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelColumna.add(lbl1);
        espacio.run();

        JButton btnRegEst = boton("Registrar estudiante");
        btnRegEst.addActionListener(e -> frame.cambiarPanel(new PanelAgregarEstudiante(frame, sistema)));
        panelColumna.add(btnRegEst);
        espacio.run();

        JButton btnBuscarEst = boton("Buscar estudiante por matrícula");
        btnBuscarEst.addActionListener(e -> frame.cambiarPanel(new PanelBuscarEstudiante(frame, sistema)));
        panelColumna.add(btnBuscarEst);
        espacio.run();

        // 2. CURSOS
        JLabel lbl2 = new JLabel("CURSOS");
        lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelColumna.add(lbl2);
        espacio.run();

        JButton btnAgregarCurso = boton("Agregar curso");
        btnAgregarCurso.addActionListener(e -> frame.cambiarPanel(new PanelAgregarCurso(frame, sistema)));
        panelColumna.add(btnAgregarCurso);
        espacio.run();

        JButton btnEliminarCurso = boton("Eliminar curso");
        btnEliminarCurso.addActionListener(e -> frame.cambiarPanel(new PanelEliminarCurso(frame, sistema)));
        panelColumna.add(btnEliminarCurso);
        espacio.run();

        JButton btnListarCursos = boton("Listar cursos");
        btnListarCursos.addActionListener(e -> frame.cambiarPanel(new PanelListarCursos(frame, sistema)));
        panelColumna.add(btnListarCursos);
        espacio.run();

        // 3. INSCRIPCIONES
        JLabel lbl3 = new JLabel("INSCRIPCIONES");
        lbl3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelColumna.add(lbl3);
        espacio.run();

        JButton btnInscribir = boton("Inscribir estudiante en curso");
        btnInscribir.addActionListener(e -> frame.cambiarPanel(new PanelInscribir(frame, sistema)));
        panelColumna.add(btnInscribir);
        espacio.run();

        JButton btnMostrarInscritos = boton("Mostrar lista de inscritos");
        btnMostrarInscritos.addActionListener(e -> frame.cambiarPanel(new PanelMostrarInscritos(frame, sistema)));
        panelColumna.add(btnMostrarInscritos);
        espacio.run();

        JButton btnListaEspera = boton("Mostrar lista de espera");
        btnListaEspera.addActionListener(e -> frame.cambiarPanel(new PanelListaEspera(frame, sistema)));
        panelColumna.add(btnListaEspera);
        espacio.run();
        
        JButton btnListaEsperaAS = boton("Mostrar lista de espera anterior-siguiente");
        btnListaEsperaAS.addActionListener(e -> frame.cambiarPanel(new PanelListaEsperaAnteriorSiguiente(frame, sistema)));
        panelColumna.add(btnListaEsperaAS);
        espacio.run();

        // 4. CALIFICACIONES
        JLabel lbl4 = new JLabel("CALIFICACIONES");
        lbl4.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelColumna.add(lbl4);
        espacio.run();

        JButton btnEnviarSolicitud = boton("Enviar solicitud de calificación");
        btnEnviarSolicitud.addActionListener(e -> frame.cambiarPanel(new PanelEnviarSolicitudCalificacion(frame, sistema)));
        panelColumna.add(btnEnviarSolicitud);
        espacio.run();

        JButton btnProcesarSolicitud = boton("Procesar siguiente solicitud");
        btnProcesarSolicitud.addActionListener(e -> frame.cambiarPanel(new PanelProcesarSolicitudCalificacion(frame, sistema)));
        panelColumna.add(btnProcesarSolicitud);
        espacio.run();

        // 5. ACCIONES
        JLabel lbl5 = new JLabel("ACCIONES");
        lbl5.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelColumna.add(lbl5);
        espacio.run();

        JButton btnDeshacer = boton("Deshacer última acción");
        btnDeshacer.addActionListener(e -> frame.cambiarPanel(new PanelDeshacerUltimaAccion(frame, sistema)));
        panelColumna.add(btnDeshacer);
        espacio.run();

        // 6. REPORTES
        JLabel lbl6 = new JLabel("REPORTES");
        lbl6.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelColumna.add(lbl6);
        espacio.run();

        JButton btnEstudiantesProm = boton("Listar estudiantes por promedio");
        btnEstudiantesProm.addActionListener(e -> frame.cambiarPanel(new PanelListarEstudiantesPromedio(frame, sistema)));
        panelColumna.add(btnEstudiantesProm);
        espacio.run();

        JButton btnRotarTutor = boton("Rotar tutor/líder de proyecto");
        btnRotarTutor.addActionListener(e -> frame.cambiarPanel(new PanelRotarTutor(frame, sistema)));
        panelColumna.add(btnRotarTutor);
        espacio.run();

        // 7. SALIR
        JButton btnSalir = boton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        panelColumna.add(btnSalir);

        // METER EL PANEL EN UN SCROLL
        JScrollPane scroll = new JScrollPane(panelColumna);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }

    // Función para crear botones del mismo tamaño
    private JButton boton(String texto) {
        JButton b = new JButton(texto);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);

        b.setMaximumSize(new Dimension(300, 35));
        b.setPreferredSize(new Dimension(300, 35));
        b.setMinimumSize(new Dimension(300, 35));

        return b;
    }
}