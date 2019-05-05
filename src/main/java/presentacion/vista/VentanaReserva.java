package presentacion.vista;

import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;


import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.ListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaReserva extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtMonto;
	private JButton btnReservar;
	private JButton btnCancelar;
	private JComboBox<?> comboBoxTransporte;
	private JComboBox<?> comboBoxRangoHorario;
	private JButton btnCargaPasajeros;
	private JLabel lblViajeSeleccionado;
	private static VentanaReserva INSTANCE;
	
	private JList<String> listViajesDisponibles;
	private DefaultListModel<String> listModelViajesDisponibles;
	private JButton btnIrViajes;
	private JButton btnRealizarPago;
	
	
	public static VentanaReserva getInstance(){
		if(INSTANCE == null)
			return new VentanaReserva();
		else
			return INSTANCE;
	}
	
	private VentanaReserva() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 617, 436);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelReserva = new JPanel();
		panelReserva.setBackground(new Color(230, 126, 34));
		panelReserva.setBounds(0, 0, 610, 53);
		contentPane.add(panelReserva);
		panelReserva.setLayout(null);
		
		JLabel lblAgregarReserva = new JLabel("Agregar reserva");
		lblAgregarReserva.setForeground(Color.WHITE);
		lblAgregarReserva.setBounds(26, 0, 210, 53);
		lblAgregarReserva.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelReserva.add(lblAgregarReserva);
		
		JLabel lblTransporte = new JLabel("Medio de transporte:");
		lblTransporte.setBounds(24, 145, 125, 14);
		contentPane.add(lblTransporte);
		
		JLabel lblRangoHorario = new JLabel("Rango de horario:");
		lblRangoHorario.setBounds(323, 145, 113, 14);
		contentPane.add(lblRangoHorario);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setBounds(24, 207, 46, 14);
		contentPane.add(lblMonto);
		
		comboBoxTransporte = new JComboBox();
		comboBoxTransporte.setBounds(159, 142, 143, 20);
		contentPane.add(comboBoxTransporte);
		
		comboBoxRangoHorario = new JComboBox();
		comboBoxRangoHorario.setBounds(446, 142, 143, 20);
		contentPane.add(comboBoxRangoHorario);
		
		txtMonto = new JTextField();
		txtMonto.setBounds(159, 204, 143, 20);
		contentPane.add(txtMonto);
		txtMonto.setColumns(10);
		
		JSeparator separadorReserva = new JSeparator();
		separadorReserva.setBounds(40, 281, 549, 20);
		contentPane.add(separadorReserva);
		
		btnReservar = new JButton("Reservar");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReservar.setForeground(Color.WHITE);
		btnReservar.setBackground(new Color(52, 152, 219));
		btnReservar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnReservar.setBounds(226, 312, 131, 42);
		contentPane.add(btnReservar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBounds(458, 312, 131, 42);
		contentPane.add(btnCancelar);
		
		btnCargaPasajeros = new JButton("Carga de pasajeros");
		btnCargaPasajeros.setBounds(336, 203, 253, 23);
		contentPane.add(btnCargaPasajeros);
		
		btnIrViajes = new JButton("Seleccionar Viaje");
		btnIrViajes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnIrViajes.setBounds(24, 74, 182, 23);
		contentPane.add(btnIrViajes);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(336, 82, 21, -10);
		contentPane.add(lblNewLabel);
		
		lblViajeSeleccionado = new JLabel("");
		lblViajeSeleccionado.setBounds(226, 74, 363, 14);
		contentPane.add(lblViajeSeleccionado);
		
		btnRealizarPago = new JButton("Pagar");
		btnRealizarPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRealizarPago.setBounds(27, 309, 131, 45);
		contentPane.add(btnRealizarPago);
		
		listModelViajesDisponibles = new DefaultListModel<String>();
		listViajesDisponibles = new JList<String>((listModelViajesDisponibles));
		listViajesDisponibles.setModel(listModelViajesDisponibles);
		
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JTextField getTxtMonto() {
		return txtMonto;
	}

	public void setTxtMonto(JTextField txtMonto) {
		this.txtMonto = txtMonto;
	}

	public JButton getBtnReservar() {
		return btnReservar;
	}

	public void setBtnReservar(JButton btnReservar) {
		this.btnReservar = btnReservar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JComboBox<?> getComboBoxTransporte() {
		return comboBoxTransporte;
	}

	public void setComboBoxTransporte(JComboBox<?> comboBoxTransporte) {
		this.comboBoxTransporte = comboBoxTransporte;
	}

	public JComboBox<?> getComboBoxRangoHorario() {
		return comboBoxRangoHorario;
	}

	public void setComboBoxRangoHorario(JComboBox<?> comboBoxRangoHorario) {
		this.comboBoxRangoHorario = comboBoxRangoHorario;
	}

	public JButton getBtnCargaPasajeros() {
		return btnCargaPasajeros;
	}

	public void setBtnCargaPasajeros(JButton btnCargaPasajeros) {
		this.btnCargaPasajeros = btnCargaPasajeros;
	}

	public JList<String> getListViajesDisponibles() {
		return listViajesDisponibles;
	}

	public void setListViajesDisponibles(JList<String> listViajesDisponibles) {
		this.listViajesDisponibles = listViajesDisponibles;
	}

	public DefaultListModel<String> getListModelViajesDisponibles() {
		return listModelViajesDisponibles;
	}

	public void setListModelViajesDisponibles(DefaultListModel<String> listModelViajesDisponibles) {
		this.listModelViajesDisponibles = listModelViajesDisponibles;
	}
	

	public JButton getBtnIrViajes() {
		return btnIrViajes;
	}

	public void setBtnIrViajes(JButton btnIrViajes) {
		this.btnIrViajes = btnIrViajes;
	}

	
	public JLabel getLblViajeSeleccionado() {
		return lblViajeSeleccionado;
	}

	public void setLblViajeSeleccionado(JLabel lblViajeSeleccionado) {
		this.lblViajeSeleccionado = lblViajeSeleccionado;
	}
	

	public JButton getBtnRealizarPago() {
		return btnRealizarPago;
	}

	public void setBtnRealizarPago(JButton btnRealizarPago) {
		this.btnRealizarPago = btnRealizarPago;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaReserva frame = new VentanaReserva();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

