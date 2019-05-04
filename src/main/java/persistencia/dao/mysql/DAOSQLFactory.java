package persistencia.dao.mysql;

import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class DAOSQLFactory implements DAOAbstractFactory {

	public ClienteDAO createClienteDAO() {
		return new ClienteDAOSQL();
	}
}