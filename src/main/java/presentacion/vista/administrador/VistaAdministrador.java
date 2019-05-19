package presentacion.vista.administrador;

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

public class VistaAdministrador extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PanelTransporte panelTransporte;
	private JMenuItem itemAgregarCuenta;
	private JMenuItem itemVisualizarTransportes ;
	private JMenuItem itemAgregarTransporte;
	private JMenuItem itemEditarTransporte;
	private JMenuItem itemEliminarTransporte;
	
	private JMenuItem itemPais;
	private JMenuItem itemProvincia;
	private JMenuItem itemCiudad;

	private static VistaAdministrador INSTANCE;
	private JMenuItem itemEliminarPais;
	private JMenuItem itemEliminarCiudad;
	private JMenuItem itemEliminarProvincia;
	

	public static VistaAdministrador getInstance(){
		if(INSTANCE == null)
			return new VistaAdministrador();
		else
			return INSTANCE;
	}

	public VistaAdministrador() {
		super();
		setTitle("Administrador");
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
		
		JMenu menuCuentas = new JMenu("Cuentas");
		menuCuentas.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(menuCuentas);
		
		itemAgregarCuenta = new JMenuItem("Agregar cuenta");
		itemAgregarCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuCuentas.add(itemAgregarCuenta);
		
		JMenu menuViajes = new JMenu("Viajes");
		menuViajes.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(menuViajes);
		
		JMenuItem itemAgregarViaje = new JMenuItem("Agregar viaje");
		itemAgregarViaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuViajes.add(itemAgregarViaje);
		
		JMenu mnDestinos = new JMenu("Destinos");
		mnDestinos.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnDestinos);
		
		itemPais = new JMenuItem("Agregar Pais");
		itemPais.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnDestinos.add(itemPais);
		
		itemProvincia = new JMenuItem("Agregar Provincia");
		itemProvincia.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnDestinos.add(itemProvincia);
		
		itemCiudad = new JMenuItem("Agregar Ciudad");
		itemCiudad.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnDestinos.add(itemCiudad);
		
		itemEliminarPais = new JMenuItem("Eliminar Pais");
		itemEliminarPais.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnDestinos.add(itemEliminarPais);
		
		itemEliminarProvincia = new JMenuItem("Eliminar Provincia");
		itemEliminarProvincia.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnDestinos.add(itemEliminarProvincia);
		
		itemEliminarCiudad = new JMenuItem("Eliminar Ciudad");
		itemEliminarCiudad.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnDestinos.add(itemEliminarCiudad);
		
		JMenu menuTransporte = new JMenu("Transportes");
		menuTransporte.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(menuTransporte);
		
		itemVisualizarTransportes = new JMenuItem("Visualizar transportes");
		itemVisualizarTransportes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuTransporte.add(itemVisualizarTransportes);
		
		itemAgregarTransporte = new JMenuItem("Agregar transporte");
		itemAgregarTransporte.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuTransporte.add(itemAgregarTransporte);
		
		itemEditarTransporte = new JMenuItem("Editar transporte");
		itemEditarTransporte.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuTransporte.add(itemEditarTransporte);
		
		itemEliminarTransporte = new JMenuItem("Eliminar transporte");
		itemEliminarTransporte.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuTransporte.add(itemEliminarTransporte);
		
		JMenu menuFormaDePago = new JMenu("Formas de pago");
		menuFormaDePago.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(menuFormaDePago);
		
		JMenuItem itemAgregarFormaDePago = new JMenuItem("Agregar forma de pago");
		itemAgregarFormaDePago.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuFormaDePago.add(itemAgregarFormaDePago);
		
		panelTransporte = new PanelTransporte();
		panelTransporte.getTablaTransportes().setSize(1114, 900);
		panelTransporte.setSize(1352, 700);
		panelTransporte.setLocation(0, 0);
		getContentPane().add(panelTransporte);
		panelTransporte.setVisible(false);
		panelTransporte.getBtnRecargarTabla().setVisible(true);
		
		this.setVisible(false);
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
	
	public JMenuItem getItemEditarTransporte() {
		return itemEditarTransporte;
	}

	public PanelTransporte getPanelTransporte() {
		return panelTransporte;
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JMenuItem getItemAgregarCuenta() {
		return itemAgregarCuenta;
	}

	public JMenuItem getItemVisualizarTransportes() {
		return itemVisualizarTransportes;
	}
	
	public JMenuItem getItemAgregarTransporte() {
		return itemAgregarTransporte;
	}
	
	public JMenuItem getItemEliminarTransporte() {
		return itemEliminarTransporte;
	}

	public JMenuItem getItemPais() {
		return itemPais;
	}

	public void setItemPais(JMenuItem itemPais) {
		this.itemPais = itemPais;
	}

	public JMenuItem getItemProvincia() {
		return itemProvincia;
	}

	public void setItemProvincia(JMenuItem itemProvincia) {
		this.itemProvincia = itemProvincia;
	}

	public JMenuItem getItemCiudad() {
		return itemCiudad;
	}

	public void setItemCiudad(JMenuItem itemCiudad) {
		this.itemCiudad = itemCiudad;
	}

	public JMenuItem getItemEliminarPais() {
		return itemEliminarPais;
	}

	public void setItemEliminarPais(JMenuItem itemEliminarPais) {
		this.itemEliminarPais = itemEliminarPais;
	}

	public JMenuItem getItemEliminarCiudad() {
		return itemEliminarCiudad;
	}

	public void setItemEliminarCiudad(JMenuItem itemEliminarCiudad) {
		this.itemEliminarCiudad = itemEliminarCiudad;
	}

	public JMenuItem getItemEliminarProvincia() {
		return itemEliminarProvincia;
	}

	public void setItemEliminarProvincia(JMenuItem itemEliminarProvincia) {
		this.itemEliminarProvincia = itemEliminarProvincia;
	}
	
	
}