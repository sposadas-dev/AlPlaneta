package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaVisualizarClientes extends JFrame {

	private static VentanaVisualizarClientes INSTANCE;
	private PanelCliente panelCliente;

	public VentanaVisualizarClientes() {
		setTitle("Agregar pasaje - Selección de cliente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(180, 100, 1324, 680);
		setLocationRelativeTo(null);	
		setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel panelPasaje = new JPanel();
		panelPasaje.setBackground(new Color(230, 126, 34));
		panelPasaje.setBounds(0, 0, 1308, 53);
		getContentPane().add(panelPasaje);
		panelPasaje.setLayout(null);
		
		JLabel lblAgregarReserva = new JLabel("Agregar pasaje");
		lblAgregarReserva.setForeground(Color.WHITE);
		lblAgregarReserva.setBounds(26, 0, 210, 53);
		lblAgregarReserva.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelPasaje.add(lblAgregarReserva);
		
		JPanel panelViaje = new JPanel();
		panelViaje.setBackground(new Color(30, 144, 255));
		panelViaje.setBounds(0, 54, 1308, 37);
		getContentPane().add(panelViaje);
		panelViaje.setLayout(null);
		
		JLabel lblSeleccionarPasaje = new JLabel("Seleccione un cliente");
		lblSeleccionarPasaje.setForeground(Color.WHITE);
		lblSeleccionarPasaje.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSeleccionarPasaje.setBounds(532, 0, 296, 31);
		panelViaje.add(lblSeleccionarPasaje);
		
		panelCliente = new PanelCliente();
		panelCliente.getBtnRecargarTabla().setLocation(422, 493);
		panelCliente.getBtnConfirmar().setLocation(620, 493);
		panelCliente.getTablaClientes().setSize(1300, 50);
		getContentPane().add(panelCliente);
		
		panelCliente.setBounds(-20, 83, 1330, 699);
		panelCliente.setLayout(null);
		panelCliente.setVisible(true);
		panelCliente.getBtnRecargarTabla().setVisible(false);
		panelCliente.getBtnConfirmar().setVisible(true);
	}
	
	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public PanelCliente getPanelCliente() {
		return panelCliente;
	}

	public static VentanaVisualizarClientes getInstance(){
		if(INSTANCE == null)
			return new VentanaVisualizarClientes();
		else
			return INSTANCE;
	}

}
