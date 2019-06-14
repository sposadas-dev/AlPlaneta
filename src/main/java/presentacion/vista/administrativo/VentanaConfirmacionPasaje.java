package presentacion.vista.administrativo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VentanaConfirmacionPasaje extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modelPasajeros;
	private  String[] nombreColumnas = {"Nombre" , "Apellido", "DNI","Fecha de nacimiento"};
	
	private JTable tablaPasajeros;
	private JTextField txtCliente;
	private JTextField txtDni;
	private JTextField txtOrigen;
	private JTextField txtDestino;
	private JTextField txtFormaPago;
	private JTextField txtPagado;
	private JTextField txtTotal;
	private JButton btnGenerarPasaje;
	private JButton btnAtras;
	private static VentanaConfirmacionPasaje ventanaConfirmacionPasaje;
	
	public static VentanaConfirmacionPasaje getInstance(){
		if (ventanaConfirmacionPasaje == null) {
			ventanaConfirmacionPasaje = new VentanaConfirmacionPasaje();
			return ventanaConfirmacionPasaje;
		} else {
			return ventanaConfirmacionPasaje;
		}
	}
	
	public VentanaConfirmacionPasaje() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Confirmación de pasaje");
		setBounds(100, 100, 657, 652);
		setResizable(false);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOrigen = new JLabel("Origen:");
		lblOrigen.setBounds(35, 137, 72, 26);
		contentPane.add(lblOrigen);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(65, 105, 225));
		panel.setBounds(0, 0, 651, 53);
		contentPane.add(panel);
		
		JLabel lblConfirmacionPasaje = new JLabel("Confirmación de compra de pasaje");
		lblConfirmacionPasaje.setForeground(Color.WHITE);
		lblConfirmacionPasaje.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblConfirmacionPasaje.setBounds(88, 0, 511, 53);
		panel.add(lblConfirmacionPasaje);
		
		JLabel lblDestino = new JLabel("Destino:");
		lblDestino.setBounds(35, 194, 46, 14);
		contentPane.add(lblDestino);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(35, 86, 46, 14);
		contentPane.add(lblCliente);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(374, 86, 46, 14);
		contentPane.add(lblDni);
		
		JLabel lblImporteTotal = new JLabel("Importe total:");
		lblImporteTotal.setBounds(374, 450, 88, 14);
		contentPane.add(lblImporteTotal);
		
		JLabel lblPasajeros = new JLabel("Pasajeros:");
		lblPasajeros.setBounds(35, 233, 133, 14);
		contentPane.add(lblPasajeros);
		
		JLabel lblFormaPago = new JLabel("Forma de pago:");
		lblFormaPago.setBounds(35, 405, 94, 14);
		contentPane.add(lblFormaPago);
		
		JLabel lblImportePagado = new JLabel("Importe pagado:");
		lblImportePagado.setBounds(374, 405, 116, 14);
		contentPane.add(lblImportePagado);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 258, 631, 128);
		contentPane.add(spPasajeros);
		modelPasajeros = new DefaultTableModel(null,nombreColumnas){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tablaPasajeros = new JTable(modelPasajeros);
		spPasajeros.setViewportView(tablaPasajeros);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(137, 524, 152, 47);
		contentPane.add(btnAtras);
		
		btnGenerarPasaje = new JButton("Generar pasaje");
		btnGenerarPasaje.setBounds(332, 525, 158, 45);
		contentPane.add(btnGenerarPasaje);
		
		tablaPasajeros = new JTable();
		tablaPasajeros.setBounds(20, 282, 621, 107);
		contentPane.add(tablaPasajeros);
		
		txtCliente = new JTextField();
		txtCliente.setBackground(Color.WHITE);
		txtCliente.setEditable(false);
		txtCliente.setBounds(97, 83, 244, 20);
		contentPane.add(txtCliente);
		txtCliente.setColumns(10);
		
		txtDni = new JTextField();
		txtDni.setBackground(Color.WHITE);
		txtDni.setEditable(false);
		txtDni.setBounds(404, 83, 197, 20);
		contentPane.add(txtDni);
		txtDni.setColumns(10);
		
		txtOrigen = new JTextField();
		txtOrigen.setBackground(Color.WHITE);
		txtOrigen.setEditable(false);
		txtOrigen.setBounds(96, 140, 505, 20);
		contentPane.add(txtOrigen);
		txtOrigen.setColumns(10);
		
		txtDestino = new JTextField();
		txtDestino.setBackground(Color.WHITE);
		txtDestino.setEditable(false);
		txtDestino.setBounds(96, 191, 505, 20);
		contentPane.add(txtDestino);
		txtDestino.setColumns(10);
		
		txtFormaPago = new JTextField();
		txtFormaPago.setBackground(Color.WHITE);
		txtFormaPago.setEditable(false);
		txtFormaPago.setBounds(133, 402, 186, 20);
		contentPane.add(txtFormaPago);
		txtFormaPago.setColumns(10);
		
		txtPagado = new JTextField();
		txtPagado.setBackground(Color.WHITE);
		txtPagado.setEditable(false);
		txtPagado.setBounds(495, 402, 123, 20);
		contentPane.add(txtPagado);
		txtPagado.setColumns(10);
		
		txtTotal = new JTextField();
		txtTotal.setBackground(Color.WHITE);
		txtTotal.setEditable(false);
		txtTotal.setBounds(495, 447, 123, 20);
		contentPane.add(txtTotal);
		txtTotal.setColumns(10);
	}

	public DefaultTableModel getModelPasajeros() {
		return modelPasajeros;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public JTable getTablaPasajeros() {
		return tablaPasajeros;
	}

	public JTextField getTxtCliente() {
		return txtCliente;
	}

	public JTextField getTxtDni() {
		return txtDni;
	}

	public JTextField getTxtOrigen() {
		return txtOrigen;
	}

	public JTextField getTxtDestino() {
		return txtDestino;
	}

	public JTextField getTxtFormaPago() {
		return txtFormaPago;
	}

	public JTextField getTxtPagado() {
		return txtPagado;
	}

	public JTextField getTxtTotal() {
		return txtTotal;
	}
	
	public JButton getBtnGenerarPasaje() {
		return btnGenerarPasaje;
	}
	
	public JButton getBtnAtras() {
		return btnAtras;
	}

	public void mostrarVentana(boolean visibilidad) {
		this.setVisible(visibilidad);
	}
}