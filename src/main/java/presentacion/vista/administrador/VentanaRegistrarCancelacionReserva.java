package presentacion.vista.administrador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VentanaRegistrarCancelacionReserva extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modelFormaPago;
	private String[] nombreColumnasformaPago = {"Cancelacion"};
	private JTable tablaFormaPago;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistrarCancelacionReserva frame = new VentanaRegistrarCancelacionReserva();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaRegistrarCancelacionReserva() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 622, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		modelFormaPago = new DefaultTableModel(null,nombreColumnasformaPago){
			@Override
				public boolean isCellEditable(int row, int column){
					return false;
				}
			};
			setLayout(null);

			JScrollPane formaPago = new JScrollPane();
			formaPago.setBounds(32, 96, 1302, 483);
			add(formaPago);
			tablaFormaPago = new JTable(modelFormaPago);
			formaPago.setViewportView(tablaFormaPago);

			
			JPanel panelFomaPago = new JPanel();
			panelFomaPago.setBackground(new Color(96, 163, 188));
			panelFomaPago.setBounds(32, 32, 1302, 64);
			add(panelFomaPago);
			panelFomaPago.setLayout(null);
	}
}
