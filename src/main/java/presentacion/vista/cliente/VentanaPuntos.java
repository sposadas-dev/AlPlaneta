package presentacion.vista.cliente;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VentanaPuntos extends JFrame {

	private JTable tablaPuntos;
	private DefaultTableModel modelPuntos;
	private  String[] columnasPuntos = {"Puntos","Fecha de Vencimiento",};
	private static VentanaPuntos ventanaPuntos;
	private JTextField txtFiltro;
	private JLabel puntosAcumulados;
	
	public static VentanaPuntos getInstance(){
		if (ventanaPuntos == null) {
			ventanaPuntos = new VentanaPuntos();
			return ventanaPuntos;
		} else {
			return ventanaPuntos;
		}
	}
	
	public VentanaPuntos() {
		setTitle("Mis puntos");
		setBounds(100, 100, 1246, 465);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(128, 0, 128));
		panel.setBounds(0, 0, 1240, 53);
		getContentPane().add(panel);
		
		JLabel lblPuntos = new JLabel("Mis puntos");
		lblPuntos.setForeground(Color.WHITE);
		lblPuntos.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPuntos.setBounds(33, 0, 253, 53);
		panel.add(lblPuntos);
		
		JScrollPane spPuntos = new JScrollPane();
		spPuntos.setBounds(10, 109, 1220, 317);
		getContentPane().add(spPuntos);
		
		modelPuntos = new DefaultTableModel(null,columnasPuntos){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tablaPuntos = new JTable(modelPuntos);
		spPuntos.setViewportView(tablaPuntos);
		
		txtFiltro = new JTextField();
		txtFiltro.setBounds(457, 79, 154, 19);
		getContentPane().add(txtFiltro);
		txtFiltro.setColumns(10);
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setBounds(401, 85, 46, 13);
		getContentPane().add(lblFiltro);
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblPuntosAcumulados = new JLabel("Puntos Acumulados");
		lblPuntosAcumulados.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPuntosAcumulados.setBounds(10, 82, 123, 14);
		getContentPane().add(lblPuntosAcumulados);
		
		puntosAcumulados = new JLabel("");
		puntosAcumulados.setFont(new Font("Tahoma", Font.PLAIN, 12));
		puntosAcumulados.setBounds(126, 84, 98, 14);
		getContentPane().add(puntosAcumulados);
		
	}
	
	public JTextField getTxtFiltro() {
		return txtFiltro;
	}
	
	public JTable getTablaPuntos() {
		return tablaPuntos;
	}

	public DefaultTableModel getModelPuntos() {
		return modelPuntos;
	}

	public String[] getColumnasPuntos() {
		return columnasPuntos;
	}
	
	public JLabel getPuntosAcumulados() {
		return puntosAcumulados;
	}

	public void setPuntosAcumulados(JLabel puntosAcumulados) {
		this.puntosAcumulados = puntosAcumulados;
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
}