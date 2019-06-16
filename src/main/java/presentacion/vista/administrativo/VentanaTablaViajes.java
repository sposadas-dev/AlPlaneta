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
import javax.swing.JTextField;
import java.awt.Color;

public class VentanaTablaViajes extends JFrame {

	private static VentanaTablaViajes INSTANCE;
	private JPanel contentPane;
	private JTable tablaViajes;
	private DefaultTableModel modelViajes;
	private JButton btnConfirmar;
	private JButton btnAtras;
	private  String[] nombreColumnas = {"Origen","Destino","Fecha de salida","Fecha de llegada","Hora de salida","Horas estimadas","Capacidad","Transporte","Precio"};
	private JTextField txtFiltro;
	private JLabel lblFiltro;
	private JLabel lblPresioneLaTecla;

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
		spPasajeros.setBounds(10, 149, 928, 227);
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
		
		txtFiltro = new JTextField();
		txtFiltro.setBounds(444, 106, 200, 19);
		contentPane.add(txtFiltro);
		txtFiltro.setColumns(10);
		
		lblFiltro = new JLabel("Filtro");
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFiltro.setBounds(276, 109, 38, 13);
		contentPane.add(lblFiltro);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(60, 179, 113));
		panel.setBounds(0, 0, 969, 68);
		contentPane.add(panel);
		
		JLabel lblViaje = new JLabel("Viajes");
		lblViaje.setForeground(Color.WHITE);
		lblViaje.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblViaje.setBounds(425, 0, 271, 64);
		panel.add(lblViaje);
		
		lblPresioneLaTecla = new JLabel("Consejo: Mantenga la tecla ctrl presionada para seleccionar más de un viaje.");
		lblPresioneLaTecla.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPresioneLaTecla.setBounds(12, 378, 704, 13);
		contentPane.add(lblPresioneLaTecla);
		lblPresioneLaTecla.setVisible(false);
		
		this.setVisible(false);
	}

	public JTextField getTxtFiltro() {
		return txtFiltro;
	}
	
	public JLabel getTxtMensajeCtrl() {
		return lblPresioneLaTecla;
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