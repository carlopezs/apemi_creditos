package apemi.model.asociados.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import apemi.model.auditoria.managers.ManagerAuditoria;
import apemi.model.core.entities.AsoCiudad;
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

}
