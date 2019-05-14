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

public class VentanaTablaViajes extends JFrame {

	private static VentanaTablaViajes INSTANCE;
	private JPanel contentPane;
	private JTable tablaViajes;
	private DefaultTableModel modelViajes;
	private JButton btnConfirmar;
	private JButton btnAtras;
	private  String[] nombreColumnas = {"Origen","Destino","Fecha de salida","Fecha de llegada","Hora de salida","Horas estimadas","Capacidad","Transporte","Precio"};

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
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 42, 928, 334);
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
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnConfirmar.setBounds(416, 415, 109, 37);
		contentPane.add(btnConfirmar);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(198, 403, 89, 38);
		contentPane.add(btnAtras);
		
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