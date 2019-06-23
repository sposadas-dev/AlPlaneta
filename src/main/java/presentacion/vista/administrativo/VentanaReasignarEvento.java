package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaReasignarEvento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnOk;
	private JButton btnMotivos;
	private JEditorPane txtDescripcion;
	private JTextField txtDni;
	private JTextField txtApellido;
	private JTextField txtNombre;
	private JLabel fechaEvento;
	private JLabel estado;
	private JLabel horaEvento;
	private JButton btnReasignar;

	private static VentanaReasignarEvento ventanaCliente;
	private JTextField nombreAdministrativo;
	
	public static VentanaReasignarEvento getInstance(){
		if(ventanaCliente == null){	
			ventanaCliente = new VentanaReasignarEvento();
			return ventanaCliente;
		}else{
			return ventanaCliente;
		}
	}
	
	public VentanaReasignarEvento() {
		setResizable(false);
		setTitle("Reasignar evento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 597, 559);
		setLocationRelativeTo(null); // centrado en pantalla

		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelCliente = new JPanel();
		panelCliente.setBackground(new Color(66, 99, 145));
		panelCliente.setBounds(0, 0, 591, 53);
		contentPane.add(panelCliente);
		panelCliente.setLayout(null);
		
		JLabel lblReasignarEvento = new JLabel("Reasignar evento");
		lblReasignarEvento.setHorizontalAlignment(SwingConstants.CENTER);
		lblReasignarEvento.setForeground(Color.WHITE);
		lblReasignarEvento.setBounds(0, 0, 591, 53);
		panelCliente.add(lblReasignarEvento);
		lblReasignarEvento.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JLabel lblFechaEvento = new JLabel("Fecha evento:\t");
		lblFechaEvento.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFechaEvento.setBounds(25, 75, 95, 14);
		contentPane.add(lblFechaEvento);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDescripcion.setBounds(25, 114, 95, 31);
		contentPane.add(lblDescripcion);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(119, 114, 435, 94);
		contentPane.add(scrollPane_1);
		
		txtDescripcion = new JEditorPane();
		scrollPane_1.setViewportView(txtDescripcion);
		txtDescripcion.setEditable(false);
		//txtDescripcion.setColumns(10);
		
		JSeparator separadorCliente = new JSeparator();
		separadorCliente.setBounds(34, 269, 519, 8);
		contentPane.add(separadorCliente);
		
		btnOk = new JButton("Ok");
		btnOk.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnOk.setBackground(new Color(52, 152, 219));
		btnOk.setForeground(Color.WHITE);
		btnOk.setBounds(230, 459, 131, 42);
		contentPane.add(btnOk);
		btnOk.setEnabled(false);
		
		
		JLabel lblHoraEvento = new JLabel("Hora evento:");
		lblHoraEvento.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHoraEvento.setBounds(220, 75, 95, 14);
		contentPane.add(lblHoraEvento);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEstado.setBounds(395, 75, 52, 14);
		contentPane.add(lblEstado);
		
		txtDni = new JTextField();
		txtDni.setEditable(false);
		txtDni.setColumns(10);
		txtDni.setBounds(70, 332, 116, 20);
		contentPane.add(txtDni);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(35, 327, 72, 31);
		contentPane.add(lblDni);
		
		JLabel lblMotivoDeReprogramacion = new JLabel("Motivo(s) de reprogramación");
		lblMotivoDeReprogramacion.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMotivoDeReprogramacion.setBounds(33, 221, 198, 31);
		contentPane.add(lblMotivoDeReprogramacion);
		
		JLabel lblDatosCliente = new JLabel("Datos cliente");
		lblDatosCliente.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDatosCliente.setBounds(35, 290, 98, 31);
		contentPane.add(lblDatosCliente);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(35, 377, 519, 8);
		contentPane.add(separator);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(198, 327, 72, 31);
		contentPane.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setEditable(false);
		txtApellido.setColumns(10);
		txtApellido.setBounds(250, 332, 116, 20);
		contentPane.add(txtApellido);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(378, 327, 72, 31);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setColumns(10);
		txtNombre.setBounds(438, 331, 116, 20);
		contentPane.add(txtNombre);
		
		horaEvento = new JLabel("*hora*");
		horaEvento.setBounds(311, 75, 83, 14);
		contentPane.add(horaEvento);
		
		btnMotivos = new JButton("Ver motivos");
		btnMotivos.setForeground(Color.WHITE);
		btnMotivos.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnMotivos.setBackground(Color.GRAY);
		btnMotivos.setBounds(243, 221, 151, 30);
		contentPane.add(btnMotivos);
		
		JLabel lblAdministrativoAsignado = new JLabel("Administrativo asignado");
		lblAdministrativoAsignado.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdministrativoAsignado.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAdministrativoAsignado.setBounds(12, 401, 194, 31);
		contentPane.add(lblAdministrativoAsignado);
		
		fechaEvento = new JLabel("*fecha*");
		fechaEvento.setBounds(122, 75, 123, 14);
		contentPane.add(fechaEvento);
		
		estado = new JLabel("*estado*");
		estado.setBounds(447, 75, 83, 14);
		contentPane.add(estado);
		
		btnReasignar = new JButton("Reasignar");
		btnReasignar.setForeground(Color.WHITE);
		btnReasignar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnReasignar.setBackground(Color.GRAY);
		btnReasignar.setBounds(423, 400, 131, 30);
		contentPane.add(btnReasignar);
		
		nombreAdministrativo = new JTextField();
		nombreAdministrativo.setEditable(false);
		nombreAdministrativo.setBounds(198, 405, 213, 22);
		contentPane.add(nombreAdministrativo);
		nombreAdministrativo.setColumns(10);
	}
	
	public void mostrarVentana(boolean b){
		this.setVisible(b);
	}
	public JEditorPane getTxtDescripcion() {
		return txtDescripcion;
	}
	
	public JTextField getTxtDni() {
		return txtDni;
	}

	
	public JButton getBtnOk() {
		return btnOk;
	}
	
	public JButton getBtnReasignar() {
		return btnReasignar;
	}
	
	
	public JButton getBtnMotivos() {
		return btnMotivos;
	}


	public static VentanaReasignarEvento getVentanaCliente() {
		return ventanaCliente;
	}

	public JTextField getTxtApellido() {
		return txtApellido;
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void cerrarVentana(){
		this.dispose();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTextField getAdministrativo() {
		return nombreAdministrativo;
	}

	public JLabel getFechaEvento() {
		return fechaEvento;
	}

	public JLabel getEstado() {
		return estado;
	}

	public JLabel getHoraEvento() {
		return horaEvento;
	}
}