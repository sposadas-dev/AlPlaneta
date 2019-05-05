package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import persistencia.conexion.Conexion;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class Vista {
	
	private JFrame frame;
	private JButton btnClientes;
	private JButton btnPasajes;
	
	private JPanel panelClientes;
	private JTable tablaClientes;
	private JButton btnAgregarCliente;
	private DefaultTableModel modelClientes;
	private  String[] nombreColumnas = {"Nombre" , "Apellido", "DNI", "Fecha de Nacimiento","Teléfono Fijo","Teléfono Celular","Email"};
	
	private JPanel panelReservas;
	private JTable tablaReservas;
	private DefaultTableModel modelReservas;
	private  String[] nombreColumnasReservas = {"Origen" , "Destino"};
	
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnEliminar;


	public Vista() {
		super();
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Al Planeta Project");
		frame.setBounds(300, 200, 827, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnClientes = new JButton("Cliente");
		btnClientes.setBounds(10, 11, 147, 53);
		frame.getContentPane().add(btnClientes);
		
		 btnPasajes = new JButton("Pasajes");
		btnPasajes.setBounds(155, 11, 147, 53);
		frame.getContentPane().add(btnPasajes);
		modelClientes = new DefaultTableModel(null,nombreColumnas){

			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		
		panelClientes = new JPanel();
		panelClientes.setBounds(0, 73, 801, 342);
		frame.getContentPane().add(panelClientes);
		panelClientes.setLayout(null);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 11, 640, 320);
		panelClientes.add(spPasajeros);
		tablaClientes = new JTable(modelClientes);
		spPasajeros.setViewportView(tablaClientes);
		panelClientes.setVisible(false);
			
		btnAgregarCliente = new JButton("Agregar");
		btnAgregarCliente.setBounds(660, 49, 108, 46);
		panelClientes.add(btnAgregarCliente);
		
		panelReservas = new JPanel();
		panelReservas.setBounds(0, 73, 775, 342);
		frame.getContentPane().add(panelReservas);
		panelReservas.setLayout(null);
		
		JScrollPane spReservas = new JScrollPane();
		spReservas.setBounds(10, 11, 567, 320);
		panelReservas.add(spReservas);
		modelReservas = new DefaultTableModel(null,nombreColumnasReservas){
			@Override
			public boolean isCellEditable(int row, int column){
			return false;
			}
		};
		tablaReservas = new JTable(modelReservas);
		spReservas.setViewportView(tablaReservas);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(587, 47, 108, 46);
		panelReservas.add(btnAgregar);
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(587, 105, 108, 46);
		panelReservas.add(btnEditar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(587, 160, 108, 46);
		panelReservas.add(btnEliminar);
		panelReservas.setVisible(false);
	}
		
	public void show(){
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
	
	public JPanel getPanelReservas() {
		return panelReservas;
	}

	public JButton getBtnPasajes() {
		return btnPasajes;
	}

	public JPanel getPanelClientes() {
		return panelClientes;
	}

	public JButton getBtnClientes() {
		return btnClientes;
	}
	
	public JButton getBtnAgregarCliente() {
		return btnAgregarCliente;
	}

	public void setBtnAgregarCliente(JButton btnAgregarCliente) {
		this.btnAgregarCliente = btnAgregarCliente;
	}
}