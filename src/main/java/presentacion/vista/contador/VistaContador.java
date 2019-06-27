package presentacion.vista.contador;

import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;






import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import persistencia.conexion.Conexion;
import presentacion.vista.administrador.VentanaPanelGeneral;



import presentacion.vista.administrativo.PanelCliente;
import presentacion.vista.administrativo.PanelPasaje;
import presentacion.vista.administrativo.VistaAdministrativo;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Component;

import javax.swing.Box;

public class VistaContador extends JFrame {
		
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PanelSueldos panelSueldos;
	private PanelServicios panelServicios;
	private JMenu menuUsuarioLogueado;
	private JMenuItem itemCambiarContrasenia;
	
	private JMenuItem itemVisualizarSueldos;
	private JMenuItem itemAgregarSueldo;
	private JMenuItem itemEditarSueldo;
	
	private JMenuItem itemVisualizarServicios;
	private JMenuItem itemAgregarServicio;
	private JMenuItem itemEditarServicio;
	private JMenuItem itemEliminarServicio;
	private JMenuItem itemEgresosReportes;
	
	private JMenuItem itemIngresosReportes;
	private static VistaContador vistaContador;
	
	public static VistaContador getInstance(){
		if(vistaContador== null){	
			vistaContador = new VistaContador();
			return vistaContador;
		}else{
			return vistaContador;
		}
	}
	
	public VistaContador() {
		super();
		setTitle("Contador");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setBounds(0, 0, 1366, 730);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JMenuBar menuBarContador = new JMenuBar();
		setJMenuBar(menuBarContador);
		
		panelSueldos = new PanelSueldos();
		panelSueldos.getTablaSueldos().setSize(1114, 900);
		panelSueldos.setSize(1352, 700);
		panelSueldos.setLocation(0, 0);
		getContentPane().add(panelSueldos);
		panelSueldos.setVisible(false);
		
		panelServicios = new PanelServicios();
		panelServicios.getTablaServicios().setSize(1114, 900);
		panelServicios.setSize(1352, 700);
		panelServicios.setLocation(0, 0);
		getContentPane().add(panelServicios);
		panelServicios.setVisible(false);
		
		JMenu menuSueldos = new JMenu("Sueldos");
		menuSueldos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBarContador.add(menuSueldos);
		
		itemVisualizarSueldos = new JMenuItem("Visualizar sueldos");
		itemVisualizarSueldos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuSueldos.add(itemVisualizarSueldos);
		
		itemAgregarSueldo = new JMenuItem("Agregar sueldo");
		itemAgregarSueldo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuSueldos.add(itemAgregarSueldo);
		
		itemEditarSueldo = new JMenuItem("Editar sueldo");
		itemEditarSueldo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuSueldos.add(itemEditarSueldo);
		
		JMenu menuServicios = new JMenu("Servicios");
		menuServicios.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBarContador.add(menuServicios);
		
		itemVisualizarServicios = new JMenuItem("Visualizar servicios");
		itemVisualizarServicios.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuServicios.add(itemVisualizarServicios);
		
		itemAgregarServicio = new JMenuItem("Agregar servicio");
		itemAgregarServicio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuServicios.add(itemAgregarServicio);
		
		itemEditarServicio = new JMenuItem("Editar servicio");
		itemEditarServicio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuServicios.add(itemEditarServicio);
		
		itemEliminarServicio = new JMenuItem("Eliminar servicio");
		itemEliminarServicio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuServicios.add(itemEliminarServicio);
		
		JMenu menuReportes = new JMenu("Reportes");
		menuReportes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBarContador.add(menuReportes);
		
		itemIngresosReportes = new JMenuItem("Ingresos");
		itemIngresosReportes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuReportes.add(itemIngresosReportes);
		
		itemEgresosReportes = new JMenuItem("Egresos");
		itemEgresosReportes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuReportes.add(itemEgresosReportes);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBarContador.add(horizontalGlue);
		
		menuUsuarioLogueado = new JMenu(" ");
		menuUsuarioLogueado.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBarContador.add(menuUsuarioLogueado);
		
		itemCambiarContrasenia = new JMenuItem("Cambiar contraseña");
		itemCambiarContrasenia.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuUsuarioLogueado.add(itemCambiarContrasenia);
		

		JLabel labelMarcaDeAgua = new JLabel("");
		labelMarcaDeAgua.setIcon(new ImageIcon(VistaContador.class.getResource("/recursos/marcaAgua.png")));
		labelMarcaDeAgua.setBounds(47, 0, 1313, 674);
		getContentPane().add(labelMarcaDeAgua);
		
		
	}

	public void mostrarVentana(){
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			@Override
		    public void windowClosing(WindowEvent e) {
		        int confirm = JOptionPane.showOptionDialog(
		             null, "¿Estás seguro que quieres salir del programa?", 
		             "Salir", JOptionPane.YES_NO_OPTION,
		             JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) {
		        	Conexion.getConexion().cerrarConexion();
		           System.exit(0);
		        }
		    }
		});
		setVisible(true);
	}

	public JMenu getMenuUsuarioLogueado() {
		return menuUsuarioLogueado;
	}
	

	public JMenuItem getItemVisualizarServicios() {
		return itemVisualizarServicios;
	}

	public JMenuItem getItemCambiarContrasenia() {
		return itemCambiarContrasenia;
	}
	
	public JMenuItem getItemVisualizarSueldos() {
		return itemVisualizarSueldos;
	}

	public JMenuItem getItemSueldos() {
		return itemAgregarSueldo;
	}

	public JMenuItem getItemAgregarSueldo() {
		return itemAgregarSueldo;
	}

	public JMenuItem getItemAgregarServicio() {
		return itemAgregarServicio;
	}

	public JMenuItem getItemEditarServicio() {
		return itemEditarServicio;
	}

	public JMenuItem getItemIngresosReportes() {
		return itemIngresosReportes;
	}

	public JMenuItem getItemEliminarServicio() {
		return itemEliminarServicio;
	}

	public PanelSueldos getPanelSueldos() {
		return panelSueldos;
	}

	public PanelServicios getPanelServicios() {
		return panelServicios;
	}

	public JMenuItem getItemEgresosReportes() {
		return itemEgresosReportes;
	}
}