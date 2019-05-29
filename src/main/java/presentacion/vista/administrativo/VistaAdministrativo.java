package presentacion.vista.administrativo;

import javax.swing.JFrame;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import persistencia.conexion.Conexion;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class VistaAdministrativo{
	
	private JFrame frame;
	
	private PanelCliente panelCliente;
	private PanelPasaje panelPasaje;
	private PanelEvento panelEvento;

	private JMenuBar menuBar;
	private JMenu menuClientes;
	private JMenuItem itemRegistrarCliente;
	private JMenuItem itemVisualizarClientes;
	
	private JMenuItem itemVisualizarPasajes;
	private JMenuItem itemAgregarPasaje;
	private JMenuItem itemEditarPasaje ;
	private JMenuItem itemCancelarPasaje;
	
	private JMenu menuEventos;
	private JMenuItem itemAgregarEvento;
	private JMenuItem itemEditarEvento;
	private JMenuItem itemVisualizarEvento;
	 

	public VistaAdministrativo() {
		super();
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setTitle("Al Planeta Project");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setBounds(0, 0, 1366, 730);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		panelCliente = new PanelCliente();
		panelCliente.getTablaClientes().setSize(1114, 900);
		panelCliente.setSize(1352, 700);
		panelCliente.setLocation(0, 0);
		frame.getContentPane().add(panelCliente);
		panelCliente.setVisible(false);
		panelCliente.getBtnRecargarTabla().setVisible(true);
		
		panelPasaje = new PanelPasaje();
		panelPasaje.getTablaReservas().setSize(1114, 900);
		panelPasaje.setSize(1352, 700);
		panelPasaje.setLocation(0, 0);
		frame.getContentPane().add(panelPasaje);
		panelPasaje.setVisible(false);
		
		panelEvento = new PanelEvento();
		panelEvento.getTablaEventos().setSize(1114, 900);
		panelEvento.setSize(1352, 700);
		panelEvento.setLocation(0, 0);
		frame.getContentPane().add(panelEvento);
		panelEvento.setVisible(false);
		
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		frame.setJMenuBar(menuBar);
		
		menuClientes = new JMenu("Clientes");
		menuClientes.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(menuClientes);
		
		itemRegistrarCliente = new JMenuItem("Registrar cliente");
		itemRegistrarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuClientes.add(itemRegistrarCliente);
		
		itemVisualizarClientes = new JMenuItem("Visualizar clientes");
		itemVisualizarClientes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuClientes.add(itemVisualizarClientes);
		
		JMenu menuPasajes = new JMenu("Pasajes");
		menuPasajes.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(menuPasajes);
		
		itemVisualizarPasajes = new JMenuItem("Visualizar pasajes");
		itemVisualizarPasajes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuPasajes.add(itemVisualizarPasajes);
		
		itemAgregarPasaje = new JMenuItem("Agregar pasaje");
		itemAgregarPasaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuPasajes.add(itemAgregarPasaje);
		
		itemEditarPasaje = new JMenuItem("Editar pasaje");
		itemEditarPasaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuPasajes.add(itemEditarPasaje);
		
		itemCancelarPasaje = new JMenuItem("Cancelar pasaje");
		itemCancelarPasaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuPasajes.add(itemCancelarPasaje);
		
		menuEventos = new JMenu("Eventos");
		menuEventos.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(menuEventos);
		
		itemAgregarEvento = new JMenuItem("Agregar evento");
		itemAgregarEvento.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuEventos.add(itemAgregarEvento);
		
		itemEditarEvento = new JMenuItem("Editar evento");
		itemEditarEvento.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuEventos.add(itemEditarEvento);
		
		itemVisualizarEvento = new JMenuItem("Visualizar eventos");
		itemVisualizarEvento.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuEventos.add(itemVisualizarEvento);
	}

	public PanelPasaje getPanelPasaje() {
		return panelPasaje;
	}

	public JMenuItem getItemRegistrarCliente() {
		return itemRegistrarCliente;
	}

	public JMenuItem getItemVisualizarClientes() {
		return itemVisualizarClientes;
	}
	
	public JMenuItem getItemAgregarPasaje() {
		return itemAgregarPasaje;
	}

	public JMenuItem getItemVisualizarPasajes() {
		return itemVisualizarPasajes;
	}
	
	public JMenuItem getItemEditarPasaje() {
		return itemEditarPasaje;
	}

	public JMenuItem getItemCancelarPasaje() {
		return itemCancelarPasaje;
	}

	
	public PanelCliente getPanelCliente() {
		return panelCliente;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
	public JMenu getMenuClientes() {
		return menuClientes;
	}
	

	public PanelEvento getPanelEvento() {
		return panelEvento;
	}

	public JMenu getMenuEventos() {
		return menuEventos;
	}

	public JMenuItem getItemAgregarEvento() {
		return itemAgregarEvento;
	}

	public JMenuItem getItemEditarEvento() {
		return itemEditarEvento;
	}

	public void mostrarVentana(){
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter(){
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
		this.frame.setVisible(true);
	}

	public JMenuItem getItemVisualizarEventos() {
		return itemVisualizarEvento;
	}
}