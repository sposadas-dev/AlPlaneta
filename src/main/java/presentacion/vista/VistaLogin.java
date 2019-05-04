package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

public class VistaLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VistaLogin INSTANCE;
	private JTextField textUsuario;
	private JTextField textContrasenia;
	private JButton btnEnviarDatos;
	
	public static VistaLogin getInstance(){
		if(INSTANCE == null)
			return new VistaLogin();
		else
			return INSTANCE;
	}

	private VistaLogin() {
		super();
		setResizable(false);
		setTitle("Modificar persona");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 167);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(118, 11, 115, 114);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(10, 11, 86, 20);
		panel.add(textUsuario);
		textUsuario.setColumns(10);
		
		textContrasenia = new JTextField();
		textContrasenia.setBounds(10, 55, 86, 20);
		panel.add(textContrasenia);
		textContrasenia.setColumns(10);
		
		btnEnviarDatos = new JButton("New button");
		btnEnviarDatos.setBounds(10, 91, 89, 23);
		panel.add(btnEnviarDatos);
		
		this.setVisible(false);
		
	}

	public static VistaLogin getINSTANCE() {
		return INSTANCE;
	}

	public JTextField getTextUsuario() {
		return textUsuario;
	}

	public void setTextUsuario(JTextField textUsuario) {
		this.textUsuario = textUsuario;
	}

	public JTextField getTextContrasenia() {
		return textContrasenia;
	}

	public void setTextContrasenia(JTextField textContrasenia) {
		this.textContrasenia = textContrasenia;
	}

	public JButton getBtnEnviarDatos() {
		return btnEnviarDatos;
	}

	public void setBtnEnviarDatos(JButton btnEnviarDatos) {
		this.btnEnviarDatos = btnEnviarDatos;
	}

	public static void setINSTANCE(VistaLogin iNSTANCE) {
		INSTANCE = iNSTANCE;
	}
	
	

}