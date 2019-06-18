package presentacion.vista.contador;

import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import persistencia.conexion.Conexion;
import presentacion.vista.administrador.VentanaPanelGeneral;



import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Component;
import javax.swing.Box;

public class VistaContador extends JFrame {
		
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenu menuUsuarioLogueado;
	private JMenuItem itemCambiarContrasenia;
	private static VistaContador vistaContador;
	
	public static VistaContador getInstance(){
		if(vistaContador== null){	
			vistaContador = new VistaContador();
			return vistaContador;
		}else{
			return vistaContador;
		}
	}
	
	public VistaContador() {
		super();
		setTitle("Contador");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	
		setBounds(0, 0, 1366, 730);
		
		JMenuBar menuBarContador = new JMenuBar();
		setJMenuBar(menuBarContador);
		
		JMenu menuRegistrosContables = new JMenu("Registros contables");
		menuRegistrosContables.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBarContador.add(menuRegistrosContables);
		
		JMenuItem itemSueldos = new JMenuItem("Sueldos");
		itemSueldos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuRegistrosContables.add(itemSueldos);
		
		JMenuItem itemEgresos = new JMenuItem("Egresos");
		itemEgresos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuRegistrosContables.add(itemEgresos);
		
		JMenu menuReportes = new JMenu("Reportes");
		menuReportes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBarContador.add(menuReportes);
		
		JMenuItem IngresosReportes = new JMenuItem("Ingresos");
		IngresosReportes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuReportes.add(IngresosReportes);
		
		JMenuItem itemEgresosReportes = new JMenuItem("Egresos");
		itemEgresosReportes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuReportes.add(itemEgresosReportes);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBarContador.add(horizontalGlue);
		
		menuUsuarioLogueado = new JMenu(" ");
		menuUsuarioLogueado.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBarContador.add(menuUsuarioLogueado);
		
		itemCambiarContrasenia = new JMenuItem("Cambiar contraseña");
		itemCambiarContrasenia.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuUsuarioLogueado.add(itemCambiarContrasenia);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	public void mostrarVentana(){
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
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
		setVisible(true);
	}

	public JMenu getMenuUsuarioLogueado() {
		return menuUsuarioLogueado;
	}

	public JMenuItem getItemCambiarContrasenia() {
		return itemCambiarContrasenia;
	}
	
	
}