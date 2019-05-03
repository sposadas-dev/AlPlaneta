package modelo;

import java.util.List;
import dto.ClienteDTO;
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
	
	public List<ClienteDTO> obtenerClientes(){
		return this.cliente.readAll();		
	}
}