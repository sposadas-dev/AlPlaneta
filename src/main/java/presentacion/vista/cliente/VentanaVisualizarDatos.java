package presentacion.vista.cliente;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;

public class VentanaVisualizarDatos extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static VentanaVisualizarDatos instance;
	private JPanel contentPane;
	private JLabel lblNombreDelCliente;
	private JLabel lblApellidoDelCliente;
	private JLabel lblDniDelCliente;
	private JLabel lblFechaNacimientoDelCliente;
	private JLabel lblNumeroFijoDelCliente;
	private JLabel lblNumeroCelularDelCliente;
	private JLabel lblEmailDelCliente;
	private JButton btnAceptar;

	public static VentanaVisualizarDatos getInstance() {
		if (instance == null)
			return new VentanaVisualizarDatos();
		else
			return instance;
	}

	public VentanaVisualizarDatos() {
		super();
		initialize();
	}

	public void initialize() {
		setTitle("Datos personales");
		setBounds(0, 0, 615, 408);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombre.setBounds(54, 84, 77, 20);
		contentPane.add(lblNombre);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblApellido.setBounds(317, 84, 117, 20);
		contentPane.add(lblApellido);

		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDni.setBounds(54, 126, 117, 20);
		contentPane.add(lblDni);

		JLabel lblFechaDeNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaDeNacimiento.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFechaDeNacimiento.setBounds(54, 174, 131, 20);
		contentPane.add(lblFechaDeNacimiento);

		JLabel lblNumeroFijo = new JLabel("Numero fijo:");
		lblNumeroFijo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumeroFijo.setBounds(54, 219, 117, 20);
		contentPane.add(lblNumeroFijo);

		JLabel lblNumeroCelular = new JLabel("Número celular:");
		lblNumeroCelular.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumeroCelular.setBounds(317, 219, 117, 20);
		contentPane.add(lblNumeroCelular);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmail.setBounds(54, 263, 117, 20);
		contentPane.add(lblEmail);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 0, 205));
		panel.setBounds(0, 0, 609, 53);
		contentPane.add(panel);
		
		JLabel lblDatosPersonales = new JLabel("Datos personales");
		lblDatosPersonales.setForeground(Color.WHITE);
		lblDatosPersonales.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblDatosPersonales.setBounds(205, 0, 253, 53);
		panel.add(lblDatosPersonales);
		
		lblNombreDelCliente = new JLabel("");
		lblNombreDelCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombreDelCliente.setBounds(130, 90, 148, 14);
		contentPane.add(lblNombreDelCliente);
		
		lblApellidoDelCliente = new JLabel("");
		lblApellidoDelCliente.setBounds(386, 86, 166, 14);
		contentPane.add(lblApellidoDelCliente);
		
		lblFechaNacimientoDelCliente = new JLabel("");
		lblFechaNacimientoDelCliente.setBounds(195, 178, 174, 14);
		contentPane.add(lblFechaNacimientoDelCliente);
		
		lblDniDelCliente = new JLabel("");
		lblDniDelCliente.setBounds(109, 132, 185, 14);
		contentPane.add(lblDniDelCliente);
		
		lblNumeroFijoDelCliente = new JLabel("");
		lblNumeroFijoDelCliente.setBounds(148, 223, 146, 14);
		contentPane.add(lblNumeroFijoDelCliente);
		
		lblNumeroCelularDelCliente = new JLabel("");
		lblNumeroCelularDelCliente.setBounds(427, 222, 144, 14);
		contentPane.add(lblNumeroCelularDelCliente);
		
		lblEmailDelCliente = new JLabel("");
		lblEmailDelCliente.setBounds(109, 265, 166, 14);
		contentPane.add(lblEmailDelCliente);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAceptar.setBackground(new Color(30, 144, 255));
		btnAceptar.setBounds(238, 308, 131, 42);
		contentPane.add(btnAceptar);
	}

	public void mostrarVentana(boolean visibilidad) {
		this.setVisible(visibilidad);
	}

	public JLabel getLblNombreDelCliente() {
		return lblNombreDelCliente;
	}

	public void setLblNombreDelCliente(JLabel lblNombreDelCliente) {
		this.lblNombreDelCliente = lblNombreDelCliente;
	}

	public JLabel getLblApellidoDelCliente() {
		return lblApellidoDelCliente;
	}

	public void setLblApellidoDelCliente(JLabel lblApellidoDelCliente) {
		this.lblApellidoDelCliente = lblApellidoDelCliente;
	}

	public JLabel getLblDniDelCliente() {
		return lblDniDelCliente;
	}

	public void setLblDniDelCliente(JLabel lblDniDelCliente) {
		this.lblDniDelCliente = lblDniDelCliente;
	}

	public JLabel getLblFechaNacimientoDelCliente() {
		return lblFechaNacimientoDelCliente;
	}

	public void setLblFechaNacimientoDelCliente(JLabel lblFechaNacimientoDelCliente) {
		this.lblFechaNacimientoDelCliente = lblFechaNacimientoDelCliente;
	}

	public JLabel getLblNumeroFijoDelCliente() {
		return lblNumeroFijoDelCliente;
	}

	public void setLblNumeroFijoDelCliente(JLabel lblNumeroFijoDelCliente) {
		this.lblNumeroFijoDelCliente = lblNumeroFijoDelCliente;
	}

	public JLabel getLblNumeroCelularDelCliente() {
		return lblNumeroCelularDelCliente;
	}

	public void setLblNumeroCelularDelCliente(JLabel lblNumeroCelularDelCliente) {
		this.lblNumeroCelularDelCliente = lblNumeroCelularDelCliente;
	}

	public JLabel getLblEmailDelCliente() {
		return lblEmailDelCliente;
	}

	public void setLblEmailDelCliente(JLabel lblEmailDelCliente) {
		this.lblEmailDelCliente = lblEmailDelCliente;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}
}