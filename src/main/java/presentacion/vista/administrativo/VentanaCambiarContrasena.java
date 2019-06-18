package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class VentanaCambiarContrasena extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static VentanaCambiarContrasena instance;
	private JPasswordField passNueva;
	private JPasswordField passActual;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JPasswordField confirmacionContrasena;
	

	public static VentanaCambiarContrasena getInstance() {
		if (instance == null)
			return new VentanaCambiarContrasena();
		else
			return instance;
	}
	
	public JPasswordField getConfirmacionContrasena() {
		return confirmacionContrasena;
	}

	public void setConfirmacionContrasena(JPasswordField confirmacionContrasena) {
		this.confirmacionContrasena = confirmacionContrasena;
	}

	public VentanaCambiarContrasena() {
		setTitle("Cambiar contraseña");
		setBounds(0, 0, 467, 315);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(32, 178, 170));
		panel.setBounds(0, 0, 521, 53);
		getContentPane().add(panel);
		
		JLabel lblCambiarContrasenia = new JLabel("Cambiar contraseña");
		lblCambiarContrasenia.setForeground(Color.WHITE);
		lblCambiarContrasenia.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCambiarContrasenia.setBounds(111, 0, 253, 53);
		panel.add(lblCambiarContrasenia);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAceptar.setBackground(new Color(30, 144, 255));
		btnAceptar.setBounds(67, 222, 131, 42);
		getContentPane().add(btnAceptar);
		
		JLabel lblContraseniaActual = new JLabel("Ingrese la contraseña actual:");
		lblContraseniaActual.setBounds(26, 84, 191, 14);
		getContentPane().add(lblContraseniaActual);
		
		JLabel lblContraseniaNueva = new JLabel("Ingrese la nueva contraseña:");
		lblContraseniaNueva.setBounds(26, 139, 191, 14);
		getContentPane().add(lblContraseniaNueva);
		
		passNueva = new JPasswordField();
		passNueva.setBounds(227, 133, 186, 20);
		getContentPane().add(passNueva);
		
		passActual = new JPasswordField();
		passActual.setBounds(227, 81, 186, 20);
		getContentPane().add(passActual);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setBounds(239, 222, 131, 42);
		getContentPane().add(btnCancelar);
		
		JLabel lblIngreseNuevamenteLa = new JLabel("Reingrese la contraseña:");
		lblIngreseNuevamenteLa.setBounds(26, 181, 191, 14);
		getContentPane().add(lblIngreseNuevamenteLa);
		
		confirmacionContrasena = new JPasswordField();
		confirmacionContrasena.setBounds(227, 175, 186, 20);
		getContentPane().add(confirmacionContrasena);
	}

	public JPasswordField getPassNueva() {
		return passNueva;
	}

	public void setPassNueva(JPasswordField passNueva) {
		this.passNueva = passNueva;
	}

	public JPasswordField getPassActual() {
		return passActual;
	}

	public void setPassActual(JPasswordField passActual) {
		this.passActual = passActual;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}


	public void mostrarVentana(boolean visibilidad) {
		this.setVisible(visibilidad);
	}

	public void limpiarCampos() {
		this.passActual.setText("");
		this.passNueva.setText("");
		this.confirmacionContrasena.setText("");
	}
}