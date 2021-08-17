package apemi.model.seguridades.managers;



import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import apemi.model.auditoria.managers.ManagerAuditoria;
import apemi.model.core.entities.AsoCiudad;
import apemi.model.core.entities.AsoPersona;
import apemi.model.core.entities.SegAsignacion;
import apemi.model.core.entities.SegModulo;
import apemi.model.core.entities.SegUsuario;
import apemi.model.core.managers.ManagerDAO;
import apemi.model.core.utils.ModelUtil;
import apemi.model.seguridades.dtos.LoginDTO;


/**
 * Implementa la logica de manejo de usuarios y autenticacion.
 * Funcionalidades principales:
 * <ul>
 * 	<li>Verificacion de credenciales de usuario.</li>
 *  <li>Asignacion de modulos a un usuario.</li>
 * </ul>
 * @author mrea
 */
@Stateless
@LocalBean
public class ManagerSeguridades {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;
    /**
     * Default constructor. 
     */
    public ManagerSeguridades() {
        
    }
    /**
     * Funcion de inicializacion de datos de usuarios.
     */
    public void inicializarDemo() throws Exception {
    	mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Inicializacion de bdd demo.");
    	List<SegUsuario> listaUsuarios=mDAO.findAll(SegUsuario.class);
    	int idSegUsuarioAdmin=0;
    	
    	boolean existeAdministrador=false;
    	for(SegUsuario u:listaUsuarios) {
    		mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Info de usuario "+u.getIdSegUsuario());
    		//Se considera al usuario 1 como administrador: 
    		if(u.getIdSegUsuario()==1) {
    			existeAdministrador=true;
    			idSegUsuarioAdmin=1;
    			System.out.println("Ya existe un usuario administrador (con id usuario 1)");
    		}
    	}
    	
    	
    	//creacion del usuario administrador:
    	//if(existeAdministrador==false) {
			SegUsuario administrador=new SegUsuario();
			AsoPersona persona = new AsoPersona();
			AsoCiudad ciudad = new AsoCiudad();
			
			ciudad.setNombre("Ibarra");
			mDAO.insertar(ciudad);
			AsoCiudad ciudadPersona = (AsoCiudad) mDAO.findById(AsoCiudad.class, ciudad.getIdCiudad());
			persona.setCedula("1002003000");
			persona.setApellidos("admin");
			persona.setAsoCiudad(ciudadPersona);
			persona.setCorreo("admin@gmail.com");
			persona.setDireccion("Alpachaca");
			persona.setGenero("M");
			persona.setFechaNacimiento(new Date());
			persona.setTelefono("No tiene");
			persona.setMovil("0969598740");
			persona.setNombres("admin");
			
			mDAO.insertar(persona);
			AsoPersona usuarioPersona = (AsoPersona) mDAO.findById(AsoPersona.class, persona.getIdPersona());
			administrador.setAsoPersona(usuarioPersona);
			administrador.setActivo(true);
			administrador.setClave("admin");
			mDAO.insertar(administrador);


			
			
			idSegUsuarioAdmin=administrador.getIdSegUsuario();
			mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Usuario administrador creado (id : "+idSegUsuarioAdmin); 
    	//}
		//inicializacion de modulos:
		SegModulo modulo=new SegModulo();
		int idSegModuloSeguridades=0;
		int idSegModuloAuditoria=0;
		modulo.setNombreModulo("Seguridades");
		modulo.setRutaAcceso("seguridades/menu");
		mDAO.insertar(modulo);
		idSegModuloSeguridades=modulo.getIdSegModulo();
		modulo=new SegModulo();
		modulo.setNombreModulo("Auditoría");
		modulo.setRutaAcceso("auditoria/menu");
		mDAO.insertar(modulo);
		idSegModuloAuditoria=modulo.getIdSegModulo();
		mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Módulos creados.");
		//asignacion de accesos:
		asignarModulo(idSegUsuarioAdmin, idSegModuloSeguridades);
		asignarModulo(idSegUsuarioAdmin, idSegModuloAuditoria);
		mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Inicializacion de bdd demo terminada.");
    }
    
    /**
     * Funcion de autenticacion mediante el uso de credenciales.
     * @param idSegUsuario El codigo del usuario que desea autenticarse.
     * @param clave La contrasenia.
     * @param direccionIP La dirección IP V4 del cliente web.
     * @return La ruta de acceso al sistema.
     * @throws Exception
     */
    public LoginDTO login(int idSegUsuario,String clave,String direccionIP) throws Exception{
    	//crear DTO:
		LoginDTO loginDTO=new LoginDTO();
		loginDTO.setIdSegUsuario(idSegUsuario);
		loginDTO.setDireccionIP(direccionIP);
		
    	if(ModelUtil.isEmpty(clave)) {
    		mAuditoria.mostrarLog(getClass(), "login", "Debe indicar una clave "+idSegUsuario);
    		throw new Exception("Debe indicar una clave");
    	}
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	
    	if(usuario==null) {
    		mAuditoria.mostrarLog(getClass(), "login", "No existe usuario "+idSegUsuario);
    		throw new Exception("Error en credenciales.");
    	}
    	loginDTO.setAsoPersona(usuario.getAsoPersona());
    		
    	if(usuario.getClave().equals(clave)) {
    		if(usuario.getActivo()==false) {
        		mAuditoria.mostrarLog(getClass(), "login", "Intento de login usuario desactivado "+idSegUsuario);
        		throw new Exception("El usuario esta desactivado.");
        	}
    		mAuditoria.mostrarLog(loginDTO, getClass(), "login", "Login exitoso "+idSegUsuario);
    		
    		loginDTO.setCorreo(usuario.getAsoPersona().getCorreo());
    		//aqui puede realizarse el envio de correo de notificacion.
    		
    		//obtener la lista de modulos a los que tiene acceso:
    		List<SegAsignacion> listaAsignaciones=findAsignacionByUsuario(usuario.getIdSegUsuario());
    		for(SegAsignacion asig:listaAsignaciones) {
    			SegModulo modulo=asig.getSegModulo();
    			loginDTO.getListaModulos().add(modulo);
    		}
    		return loginDTO;
    	}
    	mAuditoria.mostrarLog(getClass(), "login", "No coincide la clave "+idSegUsuario);
    	throw new Exception("Error en credenciales");
    }
    
    public void cerrarSesion(int idSegUsuario) {
    	mAuditoria.mostrarLog(getClass(), "cerrarSesion", "Cerrar sesión usuario: "+idSegUsuario);
    }
    
    public void accesoNoPermitido(int idSegUsuario,String ruta) {
    	mAuditoria.mostrarLog(getClass(), "accesoNoPermitido", "Usuario "+idSegUsuario+" intento no autorizado a "+ruta);
    }
    
    public List<SegUsuario> findAllUsuarios(){
    	return mDAO.findAll(SegUsuario.class, "idSegUsuario");
    }
    
    public SegUsuario findByIdSegUsuario(int idSegUsuario) throws Exception {
    	return (SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    }
    
    
    
    public void insertarUsuario(SegUsuario nuevoUsuario, AsoPersona nuevaPersona, int idCiudad) throws Exception {
    	AsoCiudad ciudad = (AsoCiudad) mDAO.findById(AsoCiudad.class, idCiudad);
    	nuevaPersona.setAsoCiudad(ciudad);
    	mDAO.insertar(nuevaPersona);
    	AsoPersona personaEncontrada = (AsoPersona)mDAO.findById(AsoPersona.class, nuevaPersona.getIdPersona());
    	nuevoUsuario.setAsoPersona(personaEncontrada);
    	
    	mDAO.insertar(nuevoUsuario);
    }
    
    public void actualizarUsuario(LoginDTO loginDTO,AsoPersona edicionPersona, int idCiudadEdicion, SegUsuario edicionUsuario) throws Exception {
    	AsoPersona persona=(AsoPersona) mDAO.findById(AsoPersona.class, edicionPersona.getIdPersona());
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, edicionUsuario.getIdSegUsuario());
    	AsoCiudad ciudad = (AsoCiudad) mDAO.findById(AsoCiudad.class, idCiudadEdicion);
    	persona.setNombres(edicionPersona.getNombres());
    	persona.setApellidos(edicionPersona.getApellidos());
    	persona.setAsoCiudad(ciudad);
    	persona.setDireccion(edicionPersona.getDireccion());
    	persona.setCorreo(edicionPersona.getCorreo());
    	persona.setTelefono(edicionPersona.getTelefono());
    	persona.setMovil(edicionPersona.getMovil());
    	persona.setFechaNacimiento(edicionPersona.getFechaNacimiento());
    	persona.setGenero(edicionPersona.getGenero());
    	
    	usuario.setClave(edicionUsuario.getClave());
    	
    	mDAO.actualizar(persona);
    	mDAO.actualizar(usuario);
    	
    	mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarUsuario", "se actualizó al usuario "+persona.getApellidos());
    }
    
    public void activarDesactivarUsuario(int idSegUsuario) throws Exception {
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	if(idSegUsuario==1)
    		throw new Exception("No se puede desactivar al usuario administrador.");
    	usuario.setActivo(!usuario.getActivo());
    	System.out.println("activar/desactivar "+usuario.getActivo());
    	mDAO.actualizar(usuario);
    }
    
    public void eliminarUsuario(int idSegUsuario) throws Exception {
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	if(usuario.getIdSegUsuario()==1)
    		throw new Exception("No se puede eliminar el usuario administrador.");
    	if(usuario.getSegAsignacions().size()>0)
    		throw new Exception("No se puede elimininar el usuario porque tiene asignaciones de módulos.");
    	mDAO.eliminar(SegUsuario.class, usuario.getIdSegUsuario());
    }
    
    public List<SegModulo> findAllModulos(){
    	return mDAO.findAll(SegModulo.class, "nombreModulo");
    }
    
    public SegModulo insertarModulo(SegModulo nuevoModulo) throws Exception {
    	SegModulo modulo=new SegModulo();
    	modulo.setNombreModulo(nuevoModulo.getNombreModulo());
    	modulo.setRutaAcceso(nuevoModulo.getRutaAcceso());
    	mDAO.insertar(modulo);
    	return modulo;
    }
    
    public void eliminarModulo(int idSegModulo) throws Exception {
    	mDAO.eliminar(SegModulo.class, idSegModulo);
    }
    
    public void actualizarModulo(SegModulo edicionModulo) throws Exception {
    	SegModulo modulo=(SegModulo) mDAO.findById(SegModulo.class, edicionModulo.getIdSegModulo());
    	modulo.setNombreModulo(edicionModulo.getNombreModulo());
    	modulo.setRutaAcceso(edicionModulo.getRutaAcceso());
    	mDAO.actualizar(modulo);
    }
    
    public List<SegAsignacion> findAsignacionByUsuario(int idSegUsuario){
    	String consulta="o.segUsuario.idSegUsuario="+idSegUsuario;
		List<SegAsignacion> listaAsignaciones=mDAO.findWhere(SegAsignacion.class, consulta, null);
		return listaAsignaciones;
    }
    
    /**
     * Permite asignar el acceso a un modulo del inventario de sistemas.
     * @param idSegUsuario El codigo del usuario.
     * @param idSegModulo El codigo del modulo que se va a asignar.
     * @throws Exception
     */
    public void asignarModulo(int idSegUsuario,int idSegModulo) throws Exception{
    	//Buscar los objetos primarios:
    	SegUsuario usuario=(SegUsuario)mDAO.findById(SegUsuario.class, idSegUsuario);
    	SegModulo modulo=(SegModulo)mDAO.findById(SegModulo.class, idSegModulo);
    	//crear la relacion:
    	SegAsignacion asignacion=new SegAsignacion();
    	asignacion.setSegModulo(modulo);
    	asignacion.setSegUsuario(usuario);
    	//guardar la asignacion:
    	mDAO.insertar(asignacion);
    	mAuditoria.mostrarLog(getClass(), "asignarModulo", "Modulo "+idSegModulo+" asigando a usuario "+idSegUsuario);
    }
    
    public void eliminarAsignacion(int idSegAsignacion) throws Exception {
    	mDAO.eliminar(SegAsignacion.class, idSegAsignacion);
    }

}
