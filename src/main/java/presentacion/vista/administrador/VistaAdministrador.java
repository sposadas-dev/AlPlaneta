package presentacion.vista.administrador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class VistaAdministrador extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuItem itemAgregarCuenta;
	private JMenuItem itemAgregarTransporte;
	
	
	
	private static VistaAdministrador INSTANCE;
	
	public static VistaAdministrador getInstance(){
		if(INSTANCE == null)
			return new VistaAdministrador();
		else
			return INSTANCE;
	}

	public VistaAdministrador() {
		super();
		setTitle("Administrador");
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 504, 291);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuCuentas = new JMenu("Cuentas");
		menuBar.add(menuCuentas);
		
		itemAgregarCuenta = new JMenuItem("Agregar cuenta");
		menuCuentas.add(itemAgregarCuenta);
		
		JMenu menuViajes = new JMenu("Viajes");
		menuBar.add(menuViajes);
		
		JMenuItem itemAgregarViaje = new JMenuItem("Agregar viaje");
		menuViajes.add(itemAgregarViaje);
		
		JMenu menuTransporte = new JMenu("Transportes");
		menuBar.add(menuTransporte);
		
		itemAgregarTransporte = new JMenuItem("Agregar transporte");
		menuTransporte.add(itemAgregarTransporte);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.setVisible(false);
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
}