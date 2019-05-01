package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Cliente;
import dto.ClienteDTO;
import dto.MedioContactoDTO;
import presentacion.vista.VentanaCliente;
import presentacion.vista.Vista;

public class Controlador implements ActionListener {

	private Vista vista;
	private VentanaCliente ventanaCliente;
	private Cliente cliente;
	
	public Controlador(Vista vista, Cliente cliente){
		this.vista = vista;
		this.vista.getBtnClientes().addActionListener(this);
		
		this.ventanaCliente = VentanaCliente.getInstance();
		this.ventanaCliente.getBtnRegistrar().addActionListener(this);
		this.cliente = cliente;
	}
	
	public void inicializar(){	
		this.vista.show();
	}
	
	private void insertarCliente(VentanaCliente ventanaCliente) {	
		MedioContactoDTO medioContacto = new MedioContactoDTO(ventanaCliente.getTxtTelefonoFijo().getText(),
			ventanaCliente.getTxtTelefonoCelular().getText(),
			ventanaCliente.getTxtEmail().getText()
		);
			
		ClienteDTO nuevoCliente = new ClienteDTO(0,
			ventanaCliente.getTxtNombre().getText(),
			ventanaCliente.getTxtApellido().getText(),
			ventanaCliente.getTxtDni().getText(),
			ventanaCliente.getDateFechaNacimiento().getDate(),
			medioContacto
		);
		
		this.cliente.agregarCliente(nuevoCliente);
	}
	
	//Validamos que los campos esten completos
		private boolean validarCampos(){
			if (ventanaCliente.getTxtNombre().getText().isEmpty() ||
				ventanaCliente.getTxtApellido().getText().isEmpty() ||
				ventanaCliente.getTxtDni().getText().isEmpty() ||
				ventanaCliente.getDateFechaNacimiento().getDate()== null ||
				ventanaCliente.getTxtTelefonoFijo().getText().isEmpty() ||
				ventanaCliente.getTxtTelefonoCelular().getText().isEmpty() ||
				ventanaCliente.getTxtEmail().getText().isEmpty()
			){
				JOptionPane.showMessageDialog(null, "Debe cargar todos los campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
		
	@Override
	public void actionPerformed(ActionEvent evento){
		if(evento.getSource() == vista.getBtnClientes()){
			ventanaCliente.mostrarVentana();
		}
		else if(evento.getSource() == ventanaCliente.getBtnRegistrar()){
			if (validarCampos()){
				insertarCliente(ventanaCliente);
				ventanaCliente.dispose();
			}
		}
	}
}