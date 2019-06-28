package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VentanaAgregarLocal extends JFrame {

		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JTextField txtNombreLocal;
		private JTextField txtDireccionLocal;
		private JButton btnAgregar;
		private JButton btnCancelar;
		private static VentanaAgregarLocal INSTANCE;
		private JLabel lblDireccionLocal;


		public static VentanaAgregarLocal getInstance(){
			if(INSTANCE == null)
				INSTANCE = new VentanaAgregarLocal();
			return INSTANCE;
		}
		
		public VentanaAgregarLocal() {
			setTitle("Agregar Local");
			setResizable(false);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 423, 303);
			setLocationRelativeTo(null);
			
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBackground(new Color(32, 178, 170));
			panel.setBounds(0, 0, 417, 53);
			contentPane.add(panel);
			
			JLabel lblAgregarTransporte = new JLabel("Agregar local");
			lblAgregarTransporte.setForeground(Color.WHITE);
			lblAgregarTransporte.setFont(new Font("Tahoma", Font.BOLD, 24));
			lblAgregarTransporte.setBounds(128, -1, 253, 53);
			panel.add(lblAgregarTransporte);
			
			JLabel lblNewLabel = new JLabel("Nombre del Local:");
			lblNewLabel.setBounds(67, 82, 147, 14);
			contentPane.add(lblNewLabel);
			
			txtNombreLocal = new JTextField();
			txtNombreLocal.setBounds(204, 80, 170, 20);
			contentPane.add(txtNombreLocal);
			txtNombreLocal.setColumns(10);
			
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnCancelar.setBackground(new Color(192, 57, 43));
			btnCancelar.setBounds(229, 205, 131, 42);
			contentPane.add(btnCancelar);
			
			btnAgregar = new JButton("Agregar ");
			btnAgregar.setForeground(Color.WHITE);
			btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnAgregar.setBackground(new Color(5, 196, 107));
			btnAgregar.setBounds(67, 205, 131, 42);
			contentPane.add(btnAgregar);
			
			lblDireccionLocal = new JLabel("Dirección del local:");
			lblDireccionLocal.setBounds(67, 126, 147, 13);
			contentPane.add(lblDireccionLocal);
			
			txtDireccionLocal = new JTextField();
			txtDireccionLocal.setBounds(204, 123, 170, 19);
			contentPane.add(txtDireccionLocal);
			txtDireccionLocal.setColumns(10);
		}
		
		public void mostrarVentana(){
			this.setVisible(true);
		}

		public void cerrarVentana(){
			this.setVisible(false);
		}
		
		public JButton getBtnAgregar() {
			return btnAgregar;
		}

		public JButton getBtnCancelar() {
			return btnCancelar;
		}

		public JTextField getTxtNombreLocal() {
			return txtNombreLocal;
		}

		public JTextField getTxtDireccionLocal() {
			return txtDireccionLocal;
		}
		
		public void limpiarCampos(){
			this.txtNombreLocal.setText(null);
			this.txtDireccionLocal.setText(null);
		}
		
}
