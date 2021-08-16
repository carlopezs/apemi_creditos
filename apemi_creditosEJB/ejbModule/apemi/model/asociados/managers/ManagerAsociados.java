package apemi.model.asociados.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import apemi.model.core.entities.AsoCiudad;
import apemi.model.core.entities.SegUsuario;
import apemi.model.core.managers.ManagerDAO;

/**
 * Session Bean implementation class ManagerAsociados
 */
@Stateless
@LocalBean
public class ManagerAsociados {
	@EJB
	private ManagerDAO mDAO;
    
    public ManagerAsociados() {
       
    }
    
    public List<AsoCiudad> findAllCiudades(){
    	return mDAO.findAll(AsoCiudad.class, "nombre");
    }

}
