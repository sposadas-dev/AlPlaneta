package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class VistaAdministrador extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VistaAdministrador INSTANCE;
	private JLabel lblError;
	
	public static VistaAdministrador getInstance(){
		if(INSTANCE == null)
			return new VistaAdministrador();
		else
			return INSTANCE;
	}

	private VistaAdministrador() {
		super();
		setResizable(false);
		setTitle("Modificar persona");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 266);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 334, 215);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblError = new JLabel("VENTANA ADMINISTRADOR");
		lblError.setBounds(20, 49, 238, 14);
		panel.add(lblError);
		
		this.setVisible(false);
		
	}

	public static VistaAdministrador getINSTANCE() {
		return INSTANCE;
	}

	public static void setINSTANCE(VistaAdministrador iNSTANCE) {
		INSTANCE = iNSTANCE;
	}

	public JLabel getLblError() {
		return lblError;
	}

	public void setLblError(JLabel lblError) {
		this.lblError = lblError;
	}
	
	

}