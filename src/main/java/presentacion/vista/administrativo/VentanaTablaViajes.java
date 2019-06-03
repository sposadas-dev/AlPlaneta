package presentacion.vista.administrativo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;

public class VentanaTablaViajes extends JFrame {

	private static VentanaTablaViajes INSTANCE;
	private JPanel contentPane;
	private JTable tablaViajes;
	private DefaultTableModel modelViajes;
	private JButton btnConfirmar;
	private JButton btnAtras;
	private  String[] nombreColumnas = {"Origen","Destino","Fecha de salida","Fecha de llegada","Hora de salida","Horas estimadas","Capacidad","Transporte","Precio"};
	private JLabel label;
	private JComboBox<String> comboBox;
	private JButton button;

	public static VentanaTablaViajes getInstance(){
		if(INSTANCE == null)
			return new VentanaTablaViajes();
		else
			return INSTANCE;
	}
	
	private VentanaTablaViajes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Carga de viaje");
		setBounds(250, 200, 975, 525);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 95, 928, 281);
		contentPane.add(spPasajeros);
		
		modelViajes = new DefaultTableModel(null,nombreColumnas){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tablaViajes = new JTable(modelViajes);
		spPasajeros.setViewportView(tablaViajes);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(442, 395, 150, 49);
		contentPane.add(btnConfirmar);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(249, 395, 150, 49);
		contentPane.add(btnAtras);
		
		label = new JLabel("Filtrar por:");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(189, 48, 100, 14);
		contentPane.add(label);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(312, 45, 216, 20);
		contentPane.add(comboBox);
		
		button = new JButton("Aplicar búsqueda");
		button.setBounds(601, 44, 216, 23);
		contentPane.add(button);
		
		this.setVisible(false);
	}

	public void mostrarVentana(boolean b) {
		this.setVisible(b);
	}

	public JTable getTablaViajes() {
		return tablaViajes;
	}

	public void setTablaViajes(JTable tablaViajes) {
		this.tablaViajes = tablaViajes;
	}

	public DefaultTableModel getModelViajes() {
		return modelViajes;
	}

	public void setModelViajes(DefaultTableModel modelViajes) {
		this.modelViajes = modelViajes;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}

	public JButton getBtnAtras() {
		return btnAtras;
	}
	
	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}
}