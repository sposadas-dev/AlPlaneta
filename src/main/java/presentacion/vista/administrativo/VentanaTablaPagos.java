package presentacion.vista.administrativo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JLabel;

import presentacion.vista.cliente.VentanaReservas;

import java.awt.Font;

public class VentanaTablaPagos extends JFrame {

	private JPanel contentPane;
	private JTable tablaPagos;
	private DefaultTableModel modelPagos;
	private String[] nombreColumnas = {"Fecha de pago","Monto pagado","Forma de pago","Atendido por"};
	
	private static VentanaTablaPagos ventanaTablaPagos;
	
	public static VentanaTablaPagos getInstance(){
		if (ventanaTablaPagos == null) {
			ventanaTablaPagos = new VentanaTablaPagos();
			return ventanaTablaPagos;
		} else {
			return ventanaTablaPagos;
		}
	}
	
	public VentanaTablaPagos() {
		setTitle("Pagos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 700, 420);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JScrollPane spPagos = new JScrollPane();
		spPagos.setBounds(28, 85, 638, 229);
		contentPane.add(spPagos);
		
		modelPagos = new DefaultTableModel(null,nombreColumnas){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
			
		tablaPagos = new JTable(modelPagos);
		spPagos.setViewportView(tablaPagos);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(34, 139, 34));
		panel.setBounds(0, 0, 694, 53);
		contentPane.add(panel);
		
		JLabel lblPagos = new JLabel("Pagos");
		lblPagos.setForeground(Color.WHITE);
		lblPagos.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPagos.setBounds(288, 0, 121, 53);
		panel.add(lblPagos);
	
	}
	
	public JTable getTablaPagos() {
		return tablaPagos;
	}

	public DefaultTableModel getModelPagos() {
		return modelPagos;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}
	
	public void mostrarVentana(boolean visibilidad){
		System.out.println("mostrar");
		this.setVisible(visibilidad);
	}
}