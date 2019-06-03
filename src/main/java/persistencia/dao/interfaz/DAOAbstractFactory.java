package persistencia.dao.interfaz;

public interface DAOAbstractFactory {
	
	public ClienteDAO createClienteDAO();
	
	public AdministradorDAO createAdministradorDAO();
	
	public AdministrativoDAO createAdministrativoDAO();
	
	public PasajeDAO createPasajeDAO();

	public CiudadDAO createCiudadDAO();

	public PasajeroDAO createPasajeroDAO();

	public MedioContactoDAO createMedioContactoDAO();

	public PagoDAO createPagoDAO();

	public TransporteDAO createTransporteDAO();

	public ViajeDAO createViajeDAO();

	public LoginDAO createLoginDAO();

	public RolDAO createRolDAO();
	
	public ProvinciaDAO createProvinciaDAO();

	public PaisDAO createPaisDAO();

	public FormaPagoDAO createFormaPagoDAO();

	public Pasaje_PasajerosDAO createPasaje_PasajerosDAO();

	public EstadoPasajeDAO createEstadoPasajeDAO();
	
	public CoordinadorDAO createCoordinadorDAO();
	
	public PuntoDAO createPuntoDAO();


}
