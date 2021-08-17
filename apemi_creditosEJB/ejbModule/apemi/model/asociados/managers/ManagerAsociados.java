package apemi.model.asociados.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import apemi.model.auditoria.managers.ManagerAuditoria;
import apemi.model.core.entities.AsoCiudad;
import apemi.model.core.entities.AsoPersona;
import apemi.model.core.entities.SegUsuario;
import apemi.model.core.managers.ManagerDAO;
import apemi.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerAsociados
 */
@Stateless
@LocalBean
public class ManagerAsociados {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;
    
    public ManagerAsociados() {
       
    }
    
    public void insertarCiudad(AsoCiudad asoCiudad) throws Exception {
    	mDAO.insertar(asoCiudad);
    }
    
    public void actualizarCiudad(LoginDTO loginDTO ,AsoCiudad edicionCiudad) throws Exception {
    	AsoCiudad ciudad = (AsoCiudad) mDAO.findById(AsoCiudad.class, edicionCiudad.getIdCiudad());
    	ciudad.setNombre(edicionCiudad.getNombre());
    	mDAO.actualizar(ciudad);
    	mAuditoria.mostrarLog(loginDTO, getClass(), "Actualizar ciudad", "Se actualizó la ciudad"+ciudad.getNombre());
    }
    
    
    public void eliminarCiudad(int idAsoCiudad)throws Exception  {
    	AsoCiudad ciudad = (AsoCiudad) mDAO.findById(AsoCiudad.class, idAsoCiudad);
    	mDAO.eliminar(AsoCiudad.class, ciudad.getIdCiudad());
    }
    
    
    public List<AsoCiudad> findAllCiudades(){
    	return mDAO.findAll(AsoCiudad.class, "nombre");
    }
    
    // Listado de Asociados
    
    public List<SegUsuario> findAllAsociados(){
     String consulta = "o.idSegUsuario > 1";
     return mDAO.findWhere(SegUsuario.class,consulta, null);
    }
    
    // Insertar Asociado
    public void insertarAsociado(SegUsuario nuevoUsuario, AsoPersona nuevaPersona, int idCiudad) throws Exception {
    	AsoCiudad ciudad = (AsoCiudad) mDAO.findById(AsoCiudad.class, idCiudad);
    	nuevaPersona.setAsoCiudad(ciudad);
    	mDAO.insertar(nuevaPersona);
    	AsoPersona personaEncontrada = (AsoPersona)mDAO.findById(AsoPersona.class, nuevaPersona.getIdPersona());
    	nuevoUsuario.setAsoPersona(personaEncontrada);
    	
    	mDAO.insertar(nuevoUsuario);
    }
    
    // Actualizar Asociado
    public void actualizarAsociado(LoginDTO loginDTO,AsoPersona edicionPersona, int idCiudadEdicion, SegUsuario edicionUsuario) throws Exception {
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
    
    //Desactivar Asociado
    public void activarDesactivarAsociado(int idSegUsuario) throws Exception {
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	if(idSegUsuario==1)
    		throw new Exception("No se puede desactivar al usuario asociado.");
    	usuario.setActivo(!usuario.getActivo());
    	mDAO.actualizar(usuario);
    }

}
