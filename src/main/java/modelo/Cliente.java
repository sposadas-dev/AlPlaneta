package modelo;

import java.util.List;

import dto.ClienteDTO;
import dto.TransporteDTO;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class Cliente {

	private ClienteDAO cliente;
	
	public Cliente(DAOAbstractFactory metodo_persistencia){
		this.cliente = metodo_persistencia.createClienteDAO();
	}
	
	public void agregarCliente(ClienteDTO nuevoCliente){
		this.cliente.insert(nuevoCliente);
	}
	
	public void editarCliente(ClienteDTO cliente_a_editar){
		this.cliente.update(cliente_a_editar);
	}
	
	public List<ClienteDTO> obtenerClientes(){
		return this.cliente.readAll();		
	}
	
	public ClienteDTO getByClienteById(int idCliente){
		return this.cliente.getClienteById(idCliente);
	}
	
	public ClienteDTO getByLoginId(int idCliente){
		return this.cliente.getByLoginId(idCliente);
	}
	
	public ClienteDTO getClienteByDni(String dniCliente){
		return this.cliente.getClienteByDni(dniCliente);
	}
}