package persistencia.dao.mysql;

import persistencia.dao.interfaz.AdministradorDAO;
import persistencia.dao.interfaz.AdministrativoDAO;
import persistencia.dao.interfaz.CiudadDAO;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.CoordinadorDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.MedioContactoDAO;
import persistencia.dao.interfaz.PagoDAO;
import persistencia.dao.interfaz.PasajeDAO;
import persistencia.dao.interfaz.PasajeroDAO;
import persistencia.dao.interfaz.TransporteDAO;
import persistencia.dao.interfaz.ViajeDAO;


public class DAOSQLFactory implements DAOAbstractFactory {
	
	public ClienteDAO createClienteDAO() 
	{
		return new ClienteDAOSQL();
	}
	
	public AdministradorDAO createAdministradorDAO() 
	{
		return new AdministradorDAOSQL();
	}
	
	public AdministrativoDAO createAdministrativoDAO() 
	{
		return new AdministrativoDAOSQL();
	}
	

	public CoordinadorDAO createCoordinadorDAO() {
		return new CoordinadorDAOSQL();
	}

	@Override
	public PasajeDAO createPasajeDAO() {
		return new PasajeDAOSQL();
	}

	@Override
	public CiudadDAO createCiudadDAO() {
		return new CiudadDAOSQL();
	}

	@Override
	public PasajeroDAO createPasajeroDAO() {
		return new PasajeroDAOSQL();
	}

	@Override
	public MedioContactoDAO createMedioContactoDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagoDAO createPagoDAO() {
		return new PagoDAOSQL();
	}

	@Override
	public TransporteDAO createTransporteDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViajeDAO createViajeDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
