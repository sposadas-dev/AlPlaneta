package presentacion.vista.administrador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaEditarFormaPago extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTipoFormaPago;
	private JButton btnEditar;
	private static VentanaEditarFormaPago INSTANCE;

	public void setTxtTipoFormaPago(JTextField txtTipoFormaPago) {
		this.txtTipoFormaPago = txtTipoFormaPago;
	}

	public static VentanaEditarFormaPago getInstance(){
		if(INSTANCE == null)
			return new VentanaEditarFormaPago();
		else
			return INSTANCE;
	}
	
	public VentanaEditarFormaPago() {
		setTitle("Editar Forma Pago");
		setResizable(false);;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 423, 303);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(96, 163, 188));
		panel.setBounds(0, 0, 417, 53);
		contentPane.add(panel);
		
		JLabel lblEditarFormaPago = new JLabel("Editar forma de pago");
		lblEditarFormaPago.setForeground(Color.WHITE);
		lblEditarFormaPago.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblEditarFormaPago.setBounds(27, 0, 323, 53);
		panel.add(lblEditarFormaPago);
		
		JLabel lblNewLabel = new JLabel("Nombre de la forma de pago:");
		lblNewLabel.setBounds(32, 111, 153, 14);
		contentPane.add(lblNewLabel);
		
		txtTipoFormaPago = new JTextField();
		txtTipoFormaPago.setBounds(210, 108, 170, 20);
		contentPane.add(txtTipoFormaPago);
		txtTipoFormaPago.setColumns(10);
		
		JButton button = new JButton("Cancelar");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 14));
		button.setBackground(new Color(192, 57, 43));
		button.setBounds(206, 177, 131, 42);
		contentPane.add(button);
		
		btnEditar = new JButton("Editar");
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEditar.setBackground(new Color(5, 196, 107));
		btnEditar.setBounds(47, 177, 131, 42);
		contentPane.add(btnEditar);
	}
	
	public void mostrarVentana(){
		this.setVisible(true);
	}

	public void cerrarVentana(){
		this.setVisible(false);
	}
	
	public JButton getBtnEditar() {
		return btnEditar;
	}

	public JTextField getTxtTipoFormaPago() {
		return txtTipoFormaPago;
	}
	
	public void limpiarCampos(){
		this.txtTipoFormaPago.setText(null);
	}
}