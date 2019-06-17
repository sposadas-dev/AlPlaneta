package modelo;

import java.util.List;

import dto.ContadorDTO;
import persistencia.dao.interfaz.ContadorDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class Contador {

private ContadorDAO contador;
	
	public Contador(DAOAbstractFactory metodo_persistencia){
		this.contador = metodo_persistencia.createContadorDAO();
	}
	
	public void agregarContador(ContadorDTO nuevoContador){
		this.contador.insert(nuevoContador);
	}
	
	public boolean eliminarContador(int idContador) {
		return this.contador.delete(idContador);
	}
	
	public boolean updateContador(ContadorDTO updateContador) {
		return this.contador.update(updateContador);
	}
	
	public List<ContadorDTO> obtenerContadores(){
		return this.contador.readAll();		
	}

	public ContadorDTO getByLoginId(int idLogin) {
		return this.contador.getByLoginId(idLogin);
	}

	public ContadorDTO buscarPorEmail(String mailDeRecuperacion) {
		return this.contador.getByMail(mailDeRecuperacion);
	}

	public boolean actualizar(ContadorDTO contador2) {
		return this.contador.updateMail(contador2);
	}
	
}
