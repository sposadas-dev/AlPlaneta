package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import persistencia.conexion.Conexion;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Vista {

	private JFrame frame;
	private JButton btnClientes;

	public Vista() {
		super();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Al Planeta Project");
		frame.setBounds(300, 200, 602, 435);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnClientes = new JButton("Agregar cliente");
		btnClientes.setBounds(10, 22, 122, 42);
		frame.getContentPane().add(btnClientes);
	}
	
	
	public void show(){
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() 
		{
			@Override
		    public void windowClosing(WindowEvent e) {
		        int confirm = JOptionPane.showOptionDialog(
		             null, "¿Estás seguro que quieres salir del programa?", 
		             "Salir", JOptionPane.YES_NO_OPTION,
		             JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) {
		        	Conexion.getConexion().cerrarConexion();
		           System.exit(0);
		        }
		    }
		});
		this.frame.setVisible(true);
	}
	
	public JButton getBtnClientes() {
		return btnClientes;
	}
}