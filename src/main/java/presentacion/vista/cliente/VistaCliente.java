package presentacion.vista.cliente;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import persistencia.conexion.Conexion;

public class VistaCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuItem itemVisualizarReservas;
	private JMenuItem itemViajesHistoricos;
	private JMenuItem itemVisualizarDatos;
	private JMenuItem itemCambiarContrasenia;

	private static VistaCliente instance;
	
	public static VistaCliente getInstance() {
		if (instance == null)
			return new VistaCliente();
		else
			return instance;
	}
	
	public VistaCliente() {
		super();
		initialize();
	}
	
	private void initialize() {
		setTitle("Cliente");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		setBounds(0, 0, 1366, 730);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuPasajes = new JMenu("Pasajes");
		menuPasajes.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(menuPasajes);

		itemVisualizarReservas = new JMenuItem("Visualizar reservas");
		itemVisualizarReservas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuPasajes.add(itemVisualizarReservas);
		
		itemViajesHistoricos = new JMenuItem("Visualizar viajes históricos");
		itemViajesHistoricos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuPasajes.add(itemViajesHistoricos);

		JMenu menuDatos = new JMenu("Datos personales");
		menuDatos.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(menuDatos);

		itemVisualizarDatos = new JMenuItem("Visualizar datos personales");
		itemVisualizarDatos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuDatos.add(itemVisualizarDatos);
		
		itemCambiarContrasenia = new JMenuItem("Cambiar contraseña");
		menuDatos.add(itemCambiarContrasenia);
		itemCambiarContrasenia.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		this.setVisible(false);
	}
	
	public void mostrarVentana() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(null, "¿Estás seguro que quieres salir del programa?",
						"Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
					Conexion.getConexion().cerrarConexion();
					System.exit(0);
				}
			}
		});
		setVisible(true);
	}
	
	public void mostrarVentana(boolean visibilidad) {
		this.setVisible(visibilidad);
	}

	public JMenuItem getItemVisualizarDatos() {
		return itemVisualizarDatos;
	}

	public JMenuItem getItemCambiarContrasenia() {
		return itemCambiarContrasenia;
	}
	
	public JMenuItem getItemVisualizarReservas() {
		return itemVisualizarReservas;
	}

	public JMenuItem getItemVisualizarViajesHistoricos() {
		return itemViajesHistoricos;
	}
}
