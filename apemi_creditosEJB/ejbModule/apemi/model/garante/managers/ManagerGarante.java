package apemi.model.garante.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import apemi.model.auditoria.managers.ManagerAuditoria;
import apemi.model.core.entities.AsoCiudad;
import apemi.model.core.entities.AsoPersona;
import apemi.model.core.entities.CredGarante;
import apemi.model.core.entities.SegUsuario;
import apemi.model.core.managers.ManagerDAO;
import apemi.model.seguridades.dtos.LoginDTO;


@Stateless
@LocalBean
public class ManagerGarante {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;

    public ManagerGarante() {
        
    }
    
    public void actualizarCiudad(LoginDTO loginDTO ,AsoCiudad edicionCiudad) throws Exception {
    	AsoCiudad ciudad = (AsoCiudad) mDAO.findById(AsoCiudad.class, edicionCiudad.getIdCiudad());
    	ciudad.setNombre(edicionCiudad.getNombre());
    	mDAO.actualizar(ciudad);
    	mAuditoria.mostrarLog(loginDTO, getClass(), "Actualizar ciudad", "Se actualizó la ciudad"+ciudad.getNombre());
    }
    
    public List<AsoCiudad> findAllCiudades(){
    	return mDAO.findAll(AsoCiudad.class, "nombre");
    }
    
    public List<CredGarante> findAllGarantes(){ 
    	return mDAO.findAll(CredGarante.class, "idGarante");
       }

 // Insertar Garante
    public void insertarGarante(CredGarante nuevoGarante, AsoPersona nuevaPersona, int idCiudad) throws Exception {
    	AsoCiudad ciudad = (AsoCiudad) mDAO.findById(AsoCiudad.class, idCiudad);
    	nuevaPersona.setAsoCiudad(ciudad);
    	mDAO.insertar(nuevaPersona);
    	AsoPersona personaEncontrada = (AsoPersona)mDAO.findById(AsoPersona.class, nuevaPersona.getIdPersona());
    	nuevoGarante.setAsoPersona(personaEncontrada);
    	mDAO.insertar(nuevoGarante);
    }
    
 // Actualizar Garante
    public void actualizarGarante(LoginDTO loginDTO,AsoPersona edicionPersona, int idCiudadEdicion, CredGarante edicionGarante) throws Exception {
    	AsoPersona persona=(AsoPersona) mDAO.findById(AsoPersona.class, edicionPersona.getIdPersona());
    	CredGarante garante=(CredGarante) mDAO.findById(CredGarante.class, edicionGarante.getIdGarante());
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
    	
    	
    	mDAO.actualizar(persona);
    	mDAO.actualizar(garante);
    	
    	mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarUsuario", "se actualizó al usuario "+persona.getApellidos());
    } 
    
  //Desactivar Garante
    public void activarDesactivarGarante(int idGarante) throws Exception {
    	CredGarante garante=(CredGarante) mDAO.findById(CredGarante.class, idGarante);
    	garante.setActivo(!garante.getActivo());
    	mDAO.actualizar(garante);    	
    }
    
}
