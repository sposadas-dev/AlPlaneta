package modelo;

import java.util.List;

import dto.AdministrativoDTO;
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
	
	public ClienteDTO getByClienteById(int idCliente){
		return this.cliente.getClienteById(idCliente);
	}
	
	public ClienteDTO getByLoginId(int idCliente){
		return this.cliente.getByLoginId(idCliente);
	}
	
	public ClienteDTO getByIdContacto(int idContacto){
		return this.cliente.getByIdContacto(idContacto);
	}
	
	public ClienteDTO buscarPorEmail(String email) {
		return this.cliente.getByMail(email);
	}
	
	public void actualizar(ClienteDTO cliente){
		this.cliente.update(cliente);
	}
}