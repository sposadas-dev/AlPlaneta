package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class VistaAdministrador extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VistaAdministrador INSTANCE;
	
	public static VistaAdministrador getInstance(){
		if(INSTANCE == null)
			return new VistaAdministrador();
		else
			return INSTANCE;
	}

	private VistaAdministrador() {
		super();
		setResizable(false);
		setTitle("Administrador");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 266);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuViajes = new JMenu("Viajes");
		menuBar.add(menuViajes);
		
		JMenuItem ItemAgregarViaje = new JMenuItem("Agregar viaje");
		menuViajes.add(ItemAgregarViaje);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.setVisible(false);
		
	}

	public static VistaAdministrador getINSTANCE() {
		return INSTANCE;
	}

	public static void setINSTANCE(VistaAdministrador iNSTANCE) {
		INSTANCE = iNSTANCE;
	}
}