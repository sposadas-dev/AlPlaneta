package presentacion.vista.coordinador;

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

public class VistaCoordinador extends JFrame {
		
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PanelRegimenPuntos panelRegimenPuntos;
	private VentanaPanelGeneral panelGeneral;
	
	
	private JMenuItem itemVisualizarRegimenPuntos ;
	private JMenuItem itemAgregarRegimenPuntos;
	private JMenuItem itemEditarRegimenPuntos;
	private JMenuItem itemEliminarRegimenPuntos;
	
	
	
	private static VistaCoordinador INSTANCE;
	public static VistaCoordinador getInstance(){
		if(INSTANCE == null)
			return new VistaCoordinador();
		else
			return INSTANCE;
	}

	public VistaCoordinador() {
		super();
		setTitle("Coordinador");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	
		setBounds(0, 0, 1366, 730);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		
			
		JMenu menuRegimenPuntos = new JMenu("Regimen puntos");
		menuRegimenPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(menuRegimenPuntos);
		
		itemVisualizarRegimenPuntos = new JMenuItem("Visualizar RegimenPuntos");
		itemVisualizarRegimenPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuRegimenPuntos.add(itemVisualizarRegimenPuntos);
		
		itemAgregarRegimenPuntos = new JMenuItem("Registrar");
		itemAgregarRegimenPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuRegimenPuntos.add(itemAgregarRegimenPuntos);
		
		itemEditarRegimenPuntos = new JMenuItem("Modificar");
		itemEditarRegimenPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuRegimenPuntos.add(itemEditarRegimenPuntos);
		
		itemEliminarRegimenPuntos = new JMenuItem("Eliminar ");
		itemEliminarRegimenPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuRegimenPuntos.add(itemEliminarRegimenPuntos);
		
		
		panelRegimenPuntos = new PanelRegimenPuntos();
		panelRegimenPuntos.getLblRegimenPuntos().setText("Regimen de puntos");
		panelRegimenPuntos.getLblRegimenPuntos().setBounds(462, 11, 450, 65);
		panelRegimenPuntos.getTablaRegimenPuntos().setSize(1114, 900);
		panelRegimenPuntos.setSize(1350, 601);
		panelRegimenPuntos.setLocation(10, 11);
		getContentPane().add(panelRegimenPuntos);
		panelRegimenPuntos.setVisible(false);
//		panelRegimenPuntos.getBtnRecargarTabla().setVisible(true);

//		this.setVisible(false);
	}
	

//-------------------------RegimenPuntos-----------------------------------//
	public void setPanelRegimenPuntos(PanelRegimenPuntos panelRegimenPuntos) {
		this.panelRegimenPuntos = panelRegimenPuntos;
	}

	public void setItemVisualizarRegimenPuntos(JMenuItem itemVisualizarRegimenPuntos) {
		this.itemVisualizarRegimenPuntos = itemVisualizarRegimenPuntos;
	}

	public void setItemAgregarRegimenPuntos(JMenuItem itemAgregarRegimenPuntos) {
		this.itemAgregarRegimenPuntos = itemAgregarRegimenPuntos;
	}

	public void setItemEditarRegimenPuntos(JMenuItem itemEditarRegimenPuntos) {
		this.itemEditarRegimenPuntos = itemEditarRegimenPuntos;
	}

	public void setItemEliminarRegimenPuntos(JMenuItem itemEliminarRegimenPuntos) {
		this.itemEliminarRegimenPuntos = itemEliminarRegimenPuntos;
	}
	

	public JMenuItem getItemEditarRegimenPuntos() {
		return itemEditarRegimenPuntos;
	}

	public PanelRegimenPuntos getPanelRegimenPuntos() {
		return panelRegimenPuntos;
	}

	public JMenuItem getItemVisualizarRegimenPuntos() {
		return itemVisualizarRegimenPuntos;
	}
	
	public JMenuItem getItemAgregarRegimenPuntos() {
		return itemAgregarRegimenPuntos;
	}
	public JMenuItem getItemEliminarRegimenPuntos() {
		return itemEliminarRegimenPuntos;
	}
	public VentanaPanelGeneral getPanelGeneral() {
		return panelGeneral;
	}

	public void setPanelGeneral(VentanaPanelGeneral panelGeneral) {
		this.panelGeneral = panelGeneral;
	}
	
	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
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
	
	

	
}