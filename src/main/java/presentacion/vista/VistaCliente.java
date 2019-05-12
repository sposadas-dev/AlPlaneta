package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class VistaCliente extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VistaCliente INSTANCE;
	private JLabel lblError;
	
	public static VistaCliente getInstance(){
		if(INSTANCE == null)
			return new VistaCliente();
		else
			return INSTANCE;
	}

	private VistaCliente() {
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
		
		lblError = new JLabel("VENTANA CLIENTE");
		lblError.setBounds(37, 28, 238, 14);
		panel.add(lblError);
		
		this.setVisible(false);
		
	}

	public static VistaCliente getINSTANCE() {
		return INSTANCE;
	}

	public static void setINSTANCE(VistaCliente iNSTANCE) {
		INSTANCE = iNSTANCE;
	}

	public JLabel getLblError() {
		return lblError;
	}

	public void setLblError(JLabel lblError) {
		this.lblError = lblError;
	}
	
	

}